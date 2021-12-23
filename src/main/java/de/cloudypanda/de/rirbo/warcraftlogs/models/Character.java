package de.cloudypanda.de.rirbo.warcraftlogs.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.cloudypanda.de.rirbo.discord.EmojiStorage;
import lombok.Getter;
import lombok.ToString;

import java.util.Locale;
import java.util.Objects;

@ToString
@Getter
public class Character {
    public int id;
    public String name;
    public Server server;

    @JsonProperty("class")
    public String subType;
    public String spec;
    public double amount;
    public int bracketData;
    public int bracket;
    public String rank;
    public String best;
    public int totalParses;
    public int bracketPercent;
    public int rankPercent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return id == character.id && name.equals(character.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public String GetSpecAsIcon() {
        System.out.println(subType + " - " + spec);
        switch(subType.toLowerCase(Locale.ROOT)){
            case "warrior":
                return EmojiStorage.WARRIOR_SWORD;
            case "warlock":
                return EmojiStorage.WARLOCK_SWORD;
            case "rogue":
                return EmojiStorage.ROGUE_SWORD;
            case "demonhunter":
                return EmojiStorage.DH_SWORD;
            case "deathknight":
                return EmojiStorage.DK_SWORD;
            case "hunter":
                return EmojiStorage.HUNTER_SWORD;
            case "priest":
                return EmojiStorage.PRIEST_SWORD;
            case "shaman":
                return EmojiStorage.SHAMAN_SWORD;
            case "mage":
                return EmojiStorage.MAGE_SWORD;
            case "monk":
                return EmojiStorage.MONK_SWORD;
            case "druid":
                return EmojiStorage.DRUID_SWORD;
            case "paladin":
                return EmojiStorage.PALADIN_SWORD;
            default:
                return EmojiStorage.BASE_SWORD;
        }
    }
}
