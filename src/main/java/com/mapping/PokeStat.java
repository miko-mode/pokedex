package com.mapping;

public class PokeStat {
    private String pokemonName;
    int totalNumberOfWins = 0;
    int totalNumberOfDefeats = 0;

    public void setPokemonName(String name) {
        this.pokemonName = name;
    }

    public void incrementTotalNumberOfWins() {
        totalNumberOfWins=totalNumberOfWins + 1;
    }

    public void incrementTotalNumberOfDefeats() {
        totalNumberOfDefeats=totalNumberOfDefeats + 1;
    }

    public int getTotalNumberOfWins() {
        return totalNumberOfWins;
    }

    public int getTotalNumberOfDefeats() {
        return totalNumberOfDefeats;
    }

    public double getWinRatio() {
        return (double) totalNumberOfWins / totalNumberOfDefeats;
    }

    public double getLossRatio() {
        return (double) totalNumberOfDefeats / totalNumberOfWins;
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
