package de.cloudypanda.de.rirbo.discord;

import com.iwebpp.crypto.TweetNaclFast;
import de.cloudypanda.de.rirbo.ContextAwareClass;
import de.cloudypanda.de.rirbo.warcraftlogs.RankingHandler;
import de.cloudypanda.de.rirbo.warcraftlogs.ReportHandler;
import de.cloudypanda.de.rirbo.warcraftlogs.models.*;
import de.cloudypanda.de.rirbo.warcraftlogs.models.Character;
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
            List<Fight> fightsForEncounter = report.getReport().GetFightsForEncounter(encounter.getId());

            if(fightsForEncounter.size() == 0) return;

            Fight bestFight = fightsForEncounter.stream().min(Comparator.comparingDouble(Fight::getFightPercentage)).get();
            float bossPercentage = bestFight.getBossPercentage();
            float fightPercentage = bestFight.getFightPercentage();

            bossField.append(encounter.getName()).append("\n");
            triesField.append(fightsForEncounter.size()).append("\n");

            if(fightsForEncounter.size() == 1){
                killField.append(EmojiStorage.ORANGE_DIAMOND);
            }else {
                killField.append(bestFight.getKill() ? EmojiStorage.DARKGREEN_DIAMOND : "BP: " + bossPercentage + "% FP: " + fightPercentage + "%");
            }

            killField.append("\n");
        });

        //Add Fields for kills
        builder.addField("Boss", bossField.toString(), true);
        builder.addField("Tries", triesField.toString(), true);
        builder.addField("Kill", killField.toString(), true);

        //Decide which view for the Actors
        int fightCount = report.getReport().getDpsParses().getData().size();

        if(fightCount == 0){
            //Basic Actors Section when detailed data isn't available
            String basicActors = GetBasicActors(report.getReport().getMasterData().getActors());
            builder.addField("Participants", basicActors, false);
            AddStringSectionToEmbed(builder, report.getReport().getCode());
            return builder;
        }

        RankingHandler handler = new RankingHandler(report.getReport().getDpsParses().getData(), report.getReport().getHpsParses().getData());


        builder.addField("(DPS/HPS) Tanks", handler.GetParseStringForRole(RoleType.TANK), true);
        builder.addField("(HPS) Healers", handler.GetParseStringForRole(RoleType.HEALER), true);
        builder.addField("(DPS) DPS", handler.GetParseStringForRole(RoleType.DPS), true);

        // Link Sections

        AddStringSectionToEmbed(builder, report.getReport().getCode());

        return builder;
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

    private void AddStringSectionToEmbed(EmbedBuilder builder, String reportCode){
        String warcraftlogsLink = "https://warcraftlogs.com/reports/" + reportCode;
        String wipefestLink = "https://www.wipefest.gg/report/" + reportCode;
        String wowanalyzerLink = "https://wowanalyzer.com/report/" + reportCode;

        builder.addField("Links",
                String.format("[WarcraftLogs](%s) \n [Wipefest](%s) \n [WoWAnalyzer](%s)",
                        warcraftlogsLink, wipefestLink, wowanalyzerLink),
                true);
    }
}
