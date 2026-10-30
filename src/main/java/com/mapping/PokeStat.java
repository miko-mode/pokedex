package com.mapping;

public class PokeStat {
    private String pokemonName;
    int totalNumberOfWins;
    int totalNumberOfDefeats;
    double winRatio;
    double lossRatio;

    public void setPokemonName(String name) {
        this.pokemonName = name;
    }

    public int getTotalNumberOfWins() {
        return totalNumberOfWins;
    }

    public int getTotalNumberOfDefeats() {
        return totalNumberOfDefeats;
    }

    public double getWinRatio() {
        int totalMatches = totalNumberOfWins + totalNumberOfDefeats;
        if (totalMatches == 0) {
            return 0.0;
        }
        return Math.round(((double) totalNumberOfWins / totalMatches) * 100.0) / 100.0;
    }

    public double getLossRatio() {
        int totalMatches = totalNumberOfWins + totalNumberOfDefeats;
        if (totalMatches == 0) {
            return 0.0;
        }
        return Math.round(((double) totalNumberOfDefeats / totalMatches) * 100.0) / 100.0;
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

    public void setWinRatio(double winRatio) {
        this.winRatio = winRatio;
    }

    public void setLossRatio(double lossRatio) {
        this.lossRatio = lossRatio;
    }
}
