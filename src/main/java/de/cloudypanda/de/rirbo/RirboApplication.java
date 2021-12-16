package de.cloudypanda.de.rirbo;

import de.cloudypanda.de.rirbo.warcraftlogs.ReportClient;
import de.cloudypanda.de.rirbo.warcraftlogs.ReportHandler;
import de.cloudypanda.de.rirbo.warcraftlogs.models.ReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RirboApplication {

    public static void main(String[] args) {
        SpringApplication.run(RirboApplication.class, args);
    }
}
