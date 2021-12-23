package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Actor {
    private Integer id;
    private String name;
    private String server;
    private String subType;
}
