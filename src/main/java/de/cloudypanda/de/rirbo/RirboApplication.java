package de.cloudypanda.de.rirbo;

import de.cloudypanda.de.rirbo.discord.InteractionEventHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import javax.security.auth.login.LoginException;

@SpringBootApplication
public class RirboApplication {

    @Value("${discord_token}")
    private String discord_token;

    public static JDA jda;

    public static void main(String[] args) {
        // Spring Application
        SpringApplication.run(RirboApplication.class, args);
        // Discord Client

    }

    //Discord Client has to be started later cause the application context has to exist to get the value mapping right
    @EventListener
    public void onAppContextStarted(ApplicationStartedEvent event) {

        JDABuilder builder = JDABuilder.createDefault(discord_token);
        builder.addEventListeners(new InteractionEventHandler());

        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.competing("RWF"));

        try {
            jda = builder.build();
        }catch (LoginException e){
            e.printStackTrace();
        }
    }
}
