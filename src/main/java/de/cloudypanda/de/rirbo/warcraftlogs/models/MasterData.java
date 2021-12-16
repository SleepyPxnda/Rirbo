package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class MasterData {
    private List<Actor> actors;

    @Override
    public String toString() {
        return "MasterData{" +
                "actors=" + actors +
                '}';
    }
}
