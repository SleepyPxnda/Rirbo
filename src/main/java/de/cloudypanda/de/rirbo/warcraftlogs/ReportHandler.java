package de.cloudypanda.de.rirbo.warcraftlogs;

import de.cloudypanda.de.rirbo.warcraftlogs.models.ReportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReportHandler {

    private final Logger logger = LoggerFactory.getLogger(ReportHandler.class);
    public ReportClient client;

    public ReportHandler(ReportClient client){
        this.client = client;
    }

    public ReportDTO GetReportForId(String id){
        logger.info("Retrieving Log for ID: " + id);
        return client.GetReportForId(id);
    }
}
