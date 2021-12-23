package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class Zone {
    private List<Encounter> encounters;
}
