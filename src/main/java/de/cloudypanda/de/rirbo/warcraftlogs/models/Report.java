package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

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

    private Rankings dpsParses;
    private Rankings hpsParses;


    public List<Fight> GetFightsForEncounter(int encounterId){
        return this.getFights().stream()
                .filter(fight -> fight.getEncounterID().equals(encounterId)).collect(Collectors.toList());
    }
}
