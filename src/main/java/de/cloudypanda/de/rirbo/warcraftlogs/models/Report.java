package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class Report {
    private String title;
    private Long startTime;
    private Long endTime;
    private String code;

    private List<Fight> fights;

    private MasterData masterData;

    private Zone zone;

    private Rankings rankings;
}
