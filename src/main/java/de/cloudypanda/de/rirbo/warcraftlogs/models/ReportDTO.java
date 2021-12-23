package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ReportDTO {

    private RequestData data;

    public Report getReport() {
        return data.getReportData().getReport();
    }
}

