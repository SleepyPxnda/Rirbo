package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;

import java.util.List;

@Getter
public class Zone {
    private List<Encounter> encounters;

    @Override
    public String toString() {
        return "Zone{" +
                "encounters=" + encounters +
                '}';
    }
}
