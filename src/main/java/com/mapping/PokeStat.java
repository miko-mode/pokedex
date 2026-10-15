package com.mapping;

import com.models.Pokemon;

public class PokeStat {
    Pokemon pokemon;
    int totalNumberOfWins = 0;
    int totalNumberOfDefeats = 0;

    public void setPokemonName(String name) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(name);
    }

    public void incrementTotalNumberOfWins() {
        totalNumberOfWins = totalNumberOfWins + 1;
    }

    public void setDefenderPokemonName(String name) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(name);
    }

    public void decrementTotalNumberOfDefeats() {
        totalNumberOfDefeats = totalNumberOfDefeats - 1;
    }

    public int getTotalNumberOfWins() {
        return totalNumberOfWins;
    }

    public void setTotalNumberOfWins(int totalNumberOfWins) {
        this.totalNumberOfWins = totalNumberOfWins;
    }

    public int getTotalNumberOfDefeats() {
        return totalNumberOfDefeats;
    }

    public void setTotalNumberOfDefeats(int totalNumberOfDefeats) {
        this.totalNumberOfDefeats = totalNumberOfDefeats;
    }

    public double getWinRatio() {
        return (double) totalNumberOfWins /totalNumberOfDefeats;
    }

    public double getLossRatio() {
        return (double) totalNumberOfDefeats /totalNumberOfWins;
    }

}
