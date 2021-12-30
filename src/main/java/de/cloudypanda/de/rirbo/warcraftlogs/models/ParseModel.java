package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ParseModel implements Comparable<ParseModel> {
    private final Character character;
    private Integer parseNumber;
    private Integer participatedFights;

    public ParseModel(Character character, Integer parse, Integer participatedFights) {
        this.character = character;
        this.parseNumber = parse;
        this.participatedFights = participatedFights;
    }

    public void addToParseNumber(Integer add){
        parseNumber += add;
    }
    public void increaseParticipatedFights() {participatedFights += 1; }

    @Override
    public int compareTo(@NotNull ParseModel o) {
        return Integer.compare(o.getParseNumber() / o.getParticipatedFights(), this.getParseNumber() / this.getParticipatedFights());
    }
}
