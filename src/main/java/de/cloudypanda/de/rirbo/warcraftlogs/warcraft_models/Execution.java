package de.cloudypanda.de.rirbo.warcraftlogs.warcraft_models;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Execution {
    public String rank;
    public String best;
    public int totalParses;
    public int rankPercent;
}
