package de.cloudypanda.de.rirbo.warcraftlogs.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Character {
    public int id;
    public String name;
    public Server server;

    @JsonProperty("class")
    public String subType;
    public String spec;
    public double amount;
    public int bracketData;
    public int bracket;
    public String rank;
    public String best;
    public int totalParses;
    public int bracketPercent;
    public int rankPercent;
}
