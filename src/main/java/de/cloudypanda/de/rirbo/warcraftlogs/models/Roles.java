package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Roles {
    public Tanks tanks;
    public Healers healers;
    public Dps dps;
}
