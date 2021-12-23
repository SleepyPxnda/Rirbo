package de.cloudypanda.de.rirbo.discord;

import de.cloudypanda.de.rirbo.ContextAwareClass;
import de.cloudypanda.de.rirbo.warcraftlogs.ReportHandler;
import de.cloudypanda.de.rirbo.warcraftlogs.models.Actor;
import de.cloudypanda.de.rirbo.warcraftlogs.models.Character;
import de.cloudypanda.de.rirbo.warcraftlogs.models.Fight;
import de.cloudypanda.de.rirbo.warcraftlogs.models.ReportDTO;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
public class InteractionEventHandler extends ListenerAdapter {

    private Logger logger = LoggerFactory.getLogger(InteractionEventHandler.class);
    private ReportHandler reportHandler;

    public InteractionEventHandler() {
        this.reportHandler = ContextAwareClass.getApplicationContext().getBean(ReportHandler.class);
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event){
        if(!event.getCommandId().equals("859206768554278942")) return;

        String logLink = event.getOption("warcraftlog_link").getAsString();

        if(logLink.split("/").length < 5) {
            event.reply("Please use this command with a valid link")
                    .setEphemeral(false)
                    .queue();
            return;
        }

        String logId = logLink.split("/")[4];
        logger.info(event.getUser().getAsTag() + " requested Log for ID: " + logId);

        ReportDTO report = reportHandler.GetReportForId(logId);

        if(report == null){
            logger.error("Report is null, please retry");
            return;
        }

        EmbedBuilder builder = createLogEmbed(report);
        builder.setAuthor(event.getUser().getAsTag());
        MessageEmbed embed = builder.build();

        event.replyEmbeds(embed).queue();
    }

    public EmbedBuilder createLogEmbed(ReportDTO report){

        //Basic Setup
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("<RI> | " + report.getReport().getTitle());
        builder.setTimestamp(Instant.now());
        builder.setFooter("Code: " + report.getReport().getCode());

        //Kills and Fights Section
        StringBuilder bossField = new StringBuilder();
        StringBuilder killField = new StringBuilder();
        StringBuilder triesField = new StringBuilder();

        report.getReport().getZone().getEncounters().forEach(encounter -> {
            List<Fight> fightsForEncounter = report.getReport().getFights().stream()
                    .filter(fight -> fight.getEncounterID().equals(encounter.getId())).collect(Collectors.toList());

            if(fightsForEncounter.size() == 0) return;

            Fight bestFight = fightsForEncounter.stream().min(Comparator.comparingDouble(Fight::getFightPercentage)).get();
            float bossPercentage = bestFight.getBossPercentage();
            float fightPercentage = bestFight.getFightPercentage();

            bossField.append(encounter.getName()).append("\n");
            triesField.append(fightsForEncounter.size()).append("\n");

            if(fightsForEncounter.size() == 1){
                killField.append(EmojiStorage.ORANGE_DIAMOND).append("\n");
            }else {
                killField.append(bestFight.getKill() ? EmojiStorage.DARKGREEN_DIAMOND : "BP: " + bossPercentage + "% FP: " + fightPercentage + "%").append("\n");

            }
        });

        //Add Fields for kills
        builder.addField("Boss", bossField.toString(), true);
        builder.addField("Tries", triesField.toString(), true);
        builder.addField("Kill", killField.toString(), true);

        //Decide which view for the Actors
        if(report.getReport().getRankings().getData().size() == 0){
            //Basic Actors Section when detailed data isn't available
            String basicActors = GetBasicActors(report.getReport().getMasterData().getActors());
            builder.addField("Participants", basicActors, false);
        } else {
            HashMap<Character, Integer> parseMapDPS = new HashMap<>();
            HashMap<Character, Integer> parseMapHealer = new HashMap<>();
            HashMap<Character, Integer> parseMapTank = new HashMap<>();

            report.getReport().getRankings().getData().forEach( ranking -> {
                ranking.getRoles().getDps().getCharacters().forEach(character -> {
                    parseMapDPS.put(character, parseMapDPS.getOrDefault(character, 0) + character.getBracketPercent());
                });

                ranking.getRoles().getHealers().getCharacters().forEach(character -> {
                    parseMapHealer.put(character, parseMapHealer.getOrDefault(character, 0) + character.getBracketPercent());
                });

                ranking.getRoles().getTanks().getCharacters().forEach(character -> {
                    parseMapTank.put(character, parseMapTank.getOrDefault(character, 0) + character.getBracketPercent());
                });
            });

            StringBuilder dpsField = new StringBuilder();
            StringBuilder healerField = new StringBuilder();
            StringBuilder tankField = new StringBuilder();
            int fightCount = report.getReport().getRankings().getData().size();

            SortHashMapAndBuildString(parseMapDPS, dpsField, fightCount);
            SortHashMapAndBuildString(parseMapHealer, healerField, fightCount);
            SortHashMapAndBuildString(parseMapTank, tankField, fightCount);

            builder.addField("Tanks", tankField.toString(), true);
            builder.addField("Healers", healerField.toString(), true);
            builder.addField("DPS", dpsField.toString(), true);

            System.out.println("Tanklenght: " + tankField.toString().length());
            System.out.println("DPSlenght: " + dpsField.toString().length());
            System.out.println("Healer: " + healerField.toString().length());

        }

        // Link Section
        String warcraftlogsLink = "https://warcraftlogs.com/reports/" + report.getReport().getCode();
        String wipefestLink = "https://www.wipefest.gg/report/" + report.getReport().getCode();
        String wowanalyzerLink = "https://wowanalyzer.com/report/" + report.getReport().getCode();

        builder.addField("Links",
                String.format("[WarcraftLogs](%s) \n [Wipefest](%s) \n [WoWAnalyzer](%s)",
                        warcraftlogsLink, wipefestLink, wowanalyzerLink),
                true);

        return builder;
    }

    private void SortHashMapAndBuildString(HashMap<Character, Integer> map, StringBuilder builder, int fightCount) {
        AtomicInteger limiter = new AtomicInteger(0);
        map.entrySet().stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new))
                .forEach((character, integer) -> {
                    limiter.getAndIncrement();
                    if(limiter.get() > 10) return;

                    builder.append(GetColorForParse(integer / fightCount))
                            .append(" ")
                            .append(character.GetSpecAsIcon())
                            .append(" - ")
                            .append(character.getName())
                            .append("\n");

                });

        if(limiter.get() <= 10) return;
        builder.append("+ ").append(map.size() - 10).append(" ...");
    }

    private String GetBasicActors(List<Actor> actors){
        StringBuilder sb = new StringBuilder();
        int actorLimit = 15;

        actors.forEach(actor -> {
                    if(actor.getId() > actorLimit) return;
                    sb.append(actor.getName())
                            .append(" - ")
                            .append(actor.getSubType())
                            .append("\n");
                });
        int actorAmount = actors.size();

        if(actorAmount > 15){
            sb.append("__... and ").append(actorAmount - actorLimit).append(" more ...__");
        }

        return sb.toString();
    }

    private String GetColorForParse(int parse){
        if(parse == 100) return EmojiStorage.YELLOW_DIAMOND;
        if(parse >= 25 && parse <= 49) return EmojiStorage.GREEN_DIAMOND;
        if(parse >= 50 && parse <= 74) return EmojiStorage.BLUE_DIAMOND;
        if(parse >= 75 && parse <= 99) return EmojiStorage.PURPLE_DIAMOND;
        return EmojiStorage.GRAY_DIAMOND;
    }
}
