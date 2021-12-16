package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;

@Getter
public class Actor {
    private Integer id;
    private String name;
    private String server;
    private String subType;

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", server='" + server + '\'' +
                ", subType='" + subType + '\'' +
                '}';
    }
}
