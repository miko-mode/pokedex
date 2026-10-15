package com.service;

import com.mapping.PokeStat;
import com.models.Pokemon;
import com.repository.FileService;

public class StatsticsService extends PokemonOperations {


    public static void createStats(Pokemon pokemon, String label) {

        PokeStat pokeStats = new PokeStat();
        if (label.equalsIgnoreCase("win")) {
            pokeStats.setPokemonName(pokemon.getName());
            pokeStats.incrementTotalNumberOfWins();

        }
        if (label.equalsIgnoreCase("loss")) {
            pokeStats.setDefenderPokemonName(pokemon.getName());
            pokeStats.decrementTotalNumberOfDefeats();
        }
        pokemonStatsList.add(pokeStats);
        FileService.saveToStatsFile();
    }

    public void showStatstics() {
        System.out.println("---------------");
        System.out.println("Spel Statstik");
        System.out.println("---------------");
        for (PokeStat pokeStat : pokemonStatsList) {

        }
    }
}
