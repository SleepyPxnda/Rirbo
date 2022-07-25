package de.cloudypanda.de.rirbo.warcraftlogs;

import de.cloudypanda.de.rirbo.discord.EmojiStorage;
import de.cloudypanda.de.rirbo.warcraftlogs.warcraft_models.*;

import java.util.*;

public class RankingHandler {
    private final List<Ranking> dpsParses;
    private final List<Ranking> hpsParses;

    public RankingHandler(List<Ranking> dpsParses, List<Ranking> hpsParses){
        this.dpsParses = dpsParses;
        this.hpsParses = hpsParses;
    }

    private List<ParseModel> GenerateParseListDPS(){
        List<ParseModel> parses = new ArrayList<>();

        dpsParses.forEach(parse -> {
            parse.getRoles().getDps().getCharacters().forEach(character -> {
                Optional<ParseModel> currentModel = parses.stream().filter(x -> x.getCharacter().equals(character)).findFirst();

                if(currentModel.isPresent()){
                    currentModel.get().addToParseNumber(character.getBracketPercent());
                    currentModel.get().increaseParticipatedFights();
                    return;
                }

                if(parses.size() < 10){
                    parses.add(new ParseModel(character, character.getBracketPercent(), 1));
                }

            });
        });
        return parses;
    }

    private List<ParseModel> GenerateParseListHealer(){
        List<ParseModel> parses = new ArrayList<>();

        hpsParses.forEach(parse -> {
            parse.getRoles().getHealers().getCharacters().forEach(character -> {
                Optional<ParseModel> currentModel = parses.stream().filter(x -> x.getCharacter().equals(character)).findFirst();

                if(currentModel.isPresent()){
                    currentModel.get().addToParseNumber(character.getBracketPercent());
                    currentModel.get().increaseParticipatedFights();
                    return;
                }

                if(parses.size() < 10){
                    parses.add(new ParseModel(character, character.getBracketPercent(), 1));
                }

            });
        });
        return parses;
    }

    private List<TankParseModel> GenerateParseListTank() {
        List<TankParseModel> parses = new ArrayList<>();

        dpsParses.forEach(parse -> {
            parse.getRoles().getTanks().getCharacters().forEach(character -> {
                Optional<TankParseModel> currentModel = parses.stream().filter(x -> x.getCharacter().equals(character)).findFirst();

                if(currentModel.isPresent()){
                    currentModel.get().addToParseNumber(character.getBracketPercent());
                    currentModel.get().increaseParticipatedFights();
                    return;
                }

                if(parses.size() < 10){
                    parses.add(new TankParseModel(character, character.getBracketPercent(), 1, 0));
                }

            });
        });

        hpsParses.forEach(parse -> {
            parse.getRoles().getTanks().getCharacters().forEach(character -> {
                Optional<TankParseModel> currentModel = parses.stream().filter(x -> x.getCharacter().equals(character)).findFirst();
                currentModel.ifPresent(tankParseModel -> tankParseModel.addToHealParseNumber(character.getBracketPercent()));
            });
        });
        return parses;
    }

    public String GetParseStringForRole(RoleType role) {
        if(role == RoleType.TANK){
            List<TankParseModel> tankParseList = GenerateParseListTank();

            StringBuilder builder = new StringBuilder();

            tankParseList.stream().sorted().forEachOrdered(x ->
                    builder.append(GetColorForParse(x.getParseNumber() / x.getParticipatedFights()))
                            .append(GetColorForParse(x.getHealParse() / x.getParticipatedFights()))
                            .append(" ")
                            .append(x.getCharacter().GetSpecAsIcon())
                            .append(" - ")
                            .append(x.getCharacter().getName())
                            .append("\n")
            );

            if(tankParseList.size() > 10){
                builder.append("+ ").append(tankParseList.size() - 10).append(" ...");
            }

            return builder.toString();
        }



        List<ParseModel> parseList = new ArrayList<>();


        switch (role){
            case DPS:
                parseList = GenerateParseListDPS();
                break;
            case HEALER:
                parseList = GenerateParseListHealer();
                break;
        }

        StringBuilder builder = new StringBuilder();

        parseList.stream().sorted().forEachOrdered(x ->
                builder.append(GetColorForParse(x.getParseNumber() / x.getParticipatedFights()))
                        .append(" ")
                        .append(x.getCharacter().GetSpecAsIcon())
                        .append(" - ")
                        .append(x.getCharacter().getName())
                        .append("\n")
        );

        if(parseList.size() > 10){
            builder.append("+ ").append(parseList.size() - 10).append(" ...");
        }

        return builder.toString();

    }

    private String GetColorForParse(int parse){
        if(parse == 100) return EmojiStorage.YELLOW_DIAMOND;
        if(parse >= 25 && parse <= 49) return EmojiStorage.GREEN_DIAMOND;
        if(parse >= 50 && parse <= 74) return EmojiStorage.BLUE_DIAMOND;
        if(parse >= 75 && parse <= 99) return EmojiStorage.PURPLE_DIAMOND;
        return EmojiStorage.GRAY_DIAMOND;
    }

}
