package de.cloudypanda.de.rirbo.discord;

import de.cloudypanda.de.rirbo.ContextAwareClass;
import de.cloudypanda.de.rirbo.RirboApplication;
import de.cloudypanda.de.rirbo.warcraftlogs.ReportHandler;
import de.cloudypanda.de.rirbo.warcraftlogs.models.Actor;
import de.cloudypanda.de.rirbo.warcraftlogs.models.Encounter;
import de.cloudypanda.de.rirbo.warcraftlogs.models.Fight;
import de.cloudypanda.de.rirbo.warcraftlogs.models.ReportDTO;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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

        System.out.println(report.getReport().getRankings().getData());

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
                killField.append(":small_orange_diamond:").append("\n");
            }else {
                killField.append(bestFight.getKill() ? ":small_blue_diamond:" : "BP: " + bossPercentage + "% FP: " + fightPercentage + "%").append("\n");

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

}
