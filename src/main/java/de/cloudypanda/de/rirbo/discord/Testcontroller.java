package de.cloudypanda.de.rirbo.discord;

import de.cloudypanda.de.rirbo.warcraftlogs.ReportHandler;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Testcontroller {

    public ReportHandler handler;

    public Testcontroller(ReportHandler handler){
        this.handler = handler;
    }

    @GetMapping("/test/{id}")
    public void testHandler(@PathVariable String id){
        handler.GetReportForId(id);
    }

}
