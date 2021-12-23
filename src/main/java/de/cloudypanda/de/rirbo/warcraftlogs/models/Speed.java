package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Speed {
    public String rank;
    public String best;
    public int totalParses;
    public int rankPercent;
}
