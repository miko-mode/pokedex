package com.mapping;

import java.util.ArrayList;

public class PokemonStats {

    private String userPokemonName;
    private String cpuPokemonName;
    private String userAttackName;
    private String cpuAttackName;
    private ArrayList<AttackTurnStats> turnStats=new ArrayList<>();

    private String winner;

    public String getUserPokemonName() {
        return userPokemonName;
    }

    public void setUserPokemonName(String userPokemonName) {
        this.userPokemonName = userPokemonName;
    }

    public String getUserAttackName() {
        return userAttackName;
    }

    public void setUserAttackName(String userAttackName) {
        this.userAttackName = userAttackName;
    }

    public String getCpuPokemonName() {
        return cpuPokemonName;
    }

    public void setCpuPokemonName(String cpuPokemonName) {
        this.cpuPokemonName = cpuPokemonName;
    }

    public String getCpuAttackName() {
        return cpuAttackName;
    }

    public void setCpuAttackName(String cpuAttackName) {
        this.cpuAttackName = cpuAttackName;
    }
    public String getWinner() {
        return winner;
    }
    public void setWinner(String name) {
        this.winner=winner;
    }

    public ArrayList<AttackTurnStats> getTurnStats() {
        return turnStats;
    }

    public void setTurnStats(ArrayList<AttackTurnStats> turnStats) {
        this.turnStats = turnStats;
    }
}
