package de.cloudypanda.de.rirbo.warcraftlogs.warcraft_models;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ReportDTO {

    private RequestData data;

    public Report getReport() {
        return data.getReportData().getReport();
    }
}

