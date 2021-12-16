package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;

import java.util.List;

@Getter
public class Report {
    private String title;
    private Long startTime;
    private Long endTime;
    private String code;

    private List<Fight> fights;

    private MasterData masterData;

    private Zone zone;

    @Override
    public String toString() {
        return "Report{" +
                "title='" + title + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", code='" + code + '\'' +
                ", fights=" + fights +
                ", masterData=" + masterData +
                ", zone=" + zone +
                '}';
    }
}
