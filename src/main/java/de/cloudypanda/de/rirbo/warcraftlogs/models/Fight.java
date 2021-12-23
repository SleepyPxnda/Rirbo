package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class Fight {
    private Integer difficulty;
    private Integer encounterID;
    private Float averageItemLevel;
    private Float bossPercentage;
    private Float fightPercentage;
    private List<Integer> friendlyPlayers;
    private Boolean kill;
}
