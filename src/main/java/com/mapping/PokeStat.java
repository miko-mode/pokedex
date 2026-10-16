package com.mapping;

public class PokeStat {
    private String pokemonName;
    int totalNumberOfWins;
    int totalNumberOfDefeats;

    public void setPokemonName(String name) {
        this.pokemonName = name;
    }

    public void incrementTotalNumberOfWins() {
        totalNumberOfWins = totalNumberOfWins + 1;
    }

    public void incrementTotalNumberOfDefeats() {
        totalNumberOfDefeats = totalNumberOfDefeats + 1;
    }

    public int getTotalNumberOfWins() {
        return totalNumberOfWins;
    }

    public int getTotalNumberOfDefeats() {
        return totalNumberOfDefeats;
    }

    public double getWinRatio() {

        totalNumberOfWins = totalNumberOfDefeats >= 0 ? Math.floorDiv(totalNumberOfWins, totalNumberOfWins + totalNumberOfDefeats) : 0;
        return totalNumberOfWins;
    }

    public double getLossRatio() {
        totalNumberOfDefeats = totalNumberOfWins >= 0 ? Math.floorDiv(totalNumberOfDefeats, totalNumberOfDefeats + totalNumberOfWins) : 0;
        return totalNumberOfDefeats;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setTotalNumberOfWins(int totalNumberOfWins) {
        this.totalNumberOfWins = totalNumberOfWins;
    }

    public void setTotalNumberOfDefeats(int totalNumberOfDefeats) {
        this.totalNumberOfDefeats = totalNumberOfDefeats;
    }
}
