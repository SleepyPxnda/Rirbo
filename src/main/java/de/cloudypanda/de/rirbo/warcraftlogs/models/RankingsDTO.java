package de.cloudypanda.de.rirbo.warcraftlogs.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor
public class RankingsDTO {

    public List<Rankings> data;

    @ToString
    @Getter
    public class Server{
        public int id;
        public String name;
        public String region;
    }

    @ToString
    @Getter
    public class Character{
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
    }

    @ToString
    @NoArgsConstructor
    @Getter
    public class Tanks{
        public String name;
        public List<Character> characters;
    }

    @ToString
    @NoArgsConstructor
    @Getter
    public class Healers{
        public String name;
        public List<Character> characters;
    }

    @ToString
    @NoArgsConstructor
    @Getter
    public class Dps{
        public String name;
        public List<Character> characters;
    }

    @ToString
    @Getter
    public class Roles{
        public Tanks tanks;
        public Healers healers;
        public Dps dps;
    }

    @ToString
    @Getter
    public class Guild{
        public int id;
        public String name;
        public int faction;
        public Server server;
    }

    @ToString
    @Getter
    public class Speed{
        public String rank;
        public String best;
        public int totalParses;
        public int rankPercent;
    }

    @ToString
    @Getter
    public class Execution{
        public String rank;
        public String best;
        public int totalParses;
        public int rankPercent;
    }

    @ToString
    @Getter
    public class Rankings {
        public int fightID;
        public int partition;
        public int zone;
        public Encounter encounter;
        public int difficulty;
        public int size;
        public int kill;
        public int duration;
        public double bracketData;
        public int deaths;
        public int damageTakenExcludingTanks;
        public Roles roles;
        public int bracket;
        public Guild guild;
        public Speed speed;
        public Execution execution;
    }
}
