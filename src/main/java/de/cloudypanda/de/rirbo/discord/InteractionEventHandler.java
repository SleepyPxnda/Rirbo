package de.cloudypanda.de.rirbo.discord;

import de.cloudypanda.de.rirbo.ContextAwareClass;
import de.cloudypanda.de.rirbo.RirboApplication;
import de.cloudypanda.de.rirbo.warcraftlogs.ReportHandler;
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
        builder.setTitle("RI | " + report.getReport().getTitle());
        builder.setTimestamp(Instant.now());
        builder.setFooter("Code: " + report.getReport().getCode());

        String warcraftlogsLink = "https://warcraftlogs.com/reports/" + report.getReport().getCode();
        String wipefestLink = "https://www.wipefest.gg/report/" + report.getReport().getCode();
        String wowanalyzerLink = "https://wowanalyzer.com/report/" + report.getReport().getCode();

        // Link Section
        builder.addField("Links",
                String.format("(WarcraftLogs)[%s] \n (Wipefest)[%s] \n (WoWAnalyzer)[%s]",
                        warcraftlogsLink, wipefestLink, wowanalyzerLink),
                true);

        return builder;
    }

}
