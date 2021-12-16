package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;

@Getter
public class Encounter {
    private Integer id;
    private String name;

    @Override
    public String toString() {
        return "Encounter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

