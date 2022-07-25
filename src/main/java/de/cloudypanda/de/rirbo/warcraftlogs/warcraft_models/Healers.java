package de.cloudypanda.de.rirbo.warcraftlogs.warcraft_models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@NoArgsConstructor
@Getter
public class Healers {
    public String name;
    public List<Character> characters;
}
