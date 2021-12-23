package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Guild {
    public int id;
    public String name;
    public int faction;
    public Server server;
}
