package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;

import java.util.List;

@Getter
public class ReportDTO {

    private RequestData data;

    public Report getReport() {
        return data.getReportData().getReport();
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "reportData=" + data +
                '}';
    }
}

