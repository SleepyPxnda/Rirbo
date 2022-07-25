package de.cloudypanda.de.rirbo.warcraftlogs.warcraft_models;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Server {
    public int id;
    public String name;
    public String region;
}
