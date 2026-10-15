package com.service;

import com.mapping.PokeStat;
import com.models.Pokemon;
import com.repository.FileService;

public class StatsticsService extends PokemonOperations {


    public static void createStats(Pokemon pokemon, String label) {
        PokeStat pokeStat = new PokeStat();
        if (label.equalsIgnoreCase("win")) {
            pokeStat.setPokemonName(pokemon.getName());
            pokeStat.incrementTotalNumberOfWins();

        } else if (label.equalsIgnoreCase("loss")) {
            pokeStat.setPokemonName(pokemon.getName());
            pokeStat.incrementTotalNumberOfDefeats();
        }
        pokemonStatsList.add(pokeStat);
        FileService.saveToStatsFile();
        pokeStat.setTotalNumberOfWins(0);
        pokeStat.setTotalNumberOfDefeats(0);
    }

    public void showStatstics() {
        System.out.println("---------------");
        System.out.println("Spel Statstik");
        System.out.println("---------------");
        for (PokeStat pokeStat : pokemonStatsList) {
            System.out.println("namn: " + pokeStat.getPokemonName() + "| Antalet vinst: " + pokeStat.getTotalNumberOfWins() + "| Antalet förlust: " + pokeStat.getTotalNumberOfDefeats() + " | Vinst ratio: " + pokeStat.getWinRatio() + " | Förlust ratio: " + pokeStat.getLossRatio());
        }
    }
}
