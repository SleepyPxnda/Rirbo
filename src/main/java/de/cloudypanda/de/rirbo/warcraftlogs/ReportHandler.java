package de.cloudypanda.de.rirbo.warcraftlogs;

import de.cloudypanda.de.rirbo.warcraftlogs.warcraft_models.ReportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service
public class ReportHandler {

    private final Logger logger = LoggerFactory.getLogger(ReportHandler.class);
    public ReportClient client;
    private final StopWatch watch;

    public ReportHandler(ReportClient client){
        this.client = client;
        this.watch = new StopWatch();
    }

    public ReportDTO GetReportForId(String id){
        watch.start();
        ReportDTO dto = client.GetReportForId(id);
        watch.stop();
        logger.info("Retrieved Log for ID: " + id + " Took: " + watch.getTotalTimeMillis() + " ms");
        return dto;
    }
}
