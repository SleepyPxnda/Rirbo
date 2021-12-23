package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Ranking {
    public int fightID;
    public int partition;
    public int zone;
    public Encounter encounter;
    public int difficulty;
    public int size;
    public int kill;
    public int duration;
    public double bracketData;
    public int deaths;
    public int damageTakenExcludingTanks;
    public Roles roles;
    public int bracket;
    public Guild guild;
    public Speed speed;
    public Execution execution;
}
