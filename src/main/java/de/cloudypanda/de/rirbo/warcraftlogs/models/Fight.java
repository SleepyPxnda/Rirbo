package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class Fight {
    private Integer difficulty;
    private Integer encounterID;
    private Float averageItemLevel;
    private Float bossPercentage;
    private Float fightPercentage;
    private List<Integer> friendlyPlayers;
    private Boolean kill;

    @Override
    public String toString() {
        return "Fight{" +
                "difficulty=" + difficulty +
                ", encounterID=" + encounterID +
                ", averageItemLevel=" + averageItemLevel +
                ", bossPercentage=" + bossPercentage +
                ", fightPercentage=" + fightPercentage +
                ", friendlyPlayers=" + friendlyPlayers +
                ", kill=" + kill +
                '}';
    }
}
