package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;

import java.util.List;

@Getter
public class ReportData {
    private Report report;

    @Override
    public String toString() {
        return "ReportData{" +
                "report=" + report +
                '}';
    }
}

