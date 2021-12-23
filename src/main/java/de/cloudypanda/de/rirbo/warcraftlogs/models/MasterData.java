package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class MasterData {
    private List<Actor> actors;
}
