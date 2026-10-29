package com.mapping;


public class GameStats {
    private String gameId;
    private String userPokemonName;
    private String cpuPokemonName;
    private int numberOfTurns;
    private String winnerPokemon;
    private String defeatedPokemon;

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

    public String getWinnerPokemon() {
        return winnerPokemon;
    }

    public void setWinnerPokemon(String winnerPokemon) {
        this.winnerPokemon = winnerPokemon;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public String getDefeatedPokemon() {
        return defeatedPokemon;
    }

    public void setDefeatedPokemon(String defeatedPokemon) {
        this.defeatedPokemon = defeatedPokemon;
    }
}
