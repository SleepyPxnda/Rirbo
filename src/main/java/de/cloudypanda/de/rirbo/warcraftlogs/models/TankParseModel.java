package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;

@Getter
public class TankParseModel extends ParseModel{

    private Integer healParse;

    public TankParseModel(Character character, Integer parse, Integer participatedFights, Integer healParse) {
        super(character, parse, participatedFights);
        this.healParse = healParse;
    }

    public void addToHealParseNumber(Integer add){
        healParse += add;
    }
}
