package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor
public class Rankings {
    public List<Ranking> data;
}

