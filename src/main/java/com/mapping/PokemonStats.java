package com.mapping;

import java.util.ArrayList;

public class PokemonStats {

    private String userPokemonName;
    private String cpuPokemonName;
    private ArrayList<AttackTurnStats> turnStats=new ArrayList<>();
    private String winnerPokemon;

    public String getUserPokemonName() {
        return userPokemonName;
    }

    public void setUserPokemonName(String userPokemonName) {
        this.userPokemonName = userPokemonName;
    }

    public String getCpuPokemonName() {
        return cpuPokemonName;
    }
    public void setCpuPokemonName(String cpuPokemonName) {
        this.cpuPokemonName = cpuPokemonName;
    }


    public ArrayList<AttackTurnStats> getTurnStats() {
        return turnStats;
    }

    public void setTurnStats(ArrayList<AttackTurnStats> turnStats) {
        this.turnStats = turnStats;
    }

    public String getWinnerPokemon() {
        return winnerPokemon;
    }

    public void setWinnerPokemon(String winnerPokemon) {
        this.winnerPokemon = winnerPokemon;
    }
}
