package com.service;

import com.exceptions.PokemonStatNotFoundInFileException;
import com.mapping.PokeStat;
import com.models.Pokemon;
import com.repository.FileService;

public class StatsticsService extends PokemonOperations {


    public void createAndSavePokemonStats(Pokemon pokemon, String label) throws PokemonStatNotFoundInFileException {
        try {
            FileService.loadFromStatsticsFile();
            PokeStat pokemonDataInStatFile = pokemonStatsList.stream().filter(pokeStat -> pokeStat.getPokemonName().equalsIgnoreCase(pokemon.getName()))
                    .findFirst()
                    .orElseThrow(() -> new PokemonStatNotFoundInFileException("Pokemonstat hittas ej"));

            if (label.equalsIgnoreCase("win")) {
                int currentTotalNumberOfWins = pokemonDataInStatFile.getTotalNumberOfWins();
                pokemonDataInStatFile.setTotalNumberOfWins(currentTotalNumberOfWins + 1);

            } else if (label.equalsIgnoreCase("loss")) {
                int currentTotalNumberOfDefeats = pokemonDataInStatFile.getTotalNumberOfDefeats();
                pokemonDataInStatFile.setTotalNumberOfDefeats(currentTotalNumberOfDefeats + 1);
            }

        } catch (PokemonStatNotFoundInFileException e) {
            PokeStat pokeStat = null;
            if (label.equalsIgnoreCase("win")) {
                pokeStat = new PokeStat();
                pokeStat.setPokemonName(pokemon.getName());
                pokeStat.setTotalNumberOfWins(1);
                pokeStat.setTotalNumberOfDefeats(0);
                pokeStat.getWinRatio();
                pokeStat.getLossRatio();
            } else if (label.equalsIgnoreCase("loss")) {
                pokeStat = new PokeStat();
                pokeStat.setPokemonName(pokemon.getName());
                pokeStat.setTotalNumberOfDefeats(1);
                pokeStat.setTotalNumberOfWins(0);
                pokeStat.getWinRatio();
                pokeStat.getLossRatio();
            }
            pokemonStatsList.add(pokeStat);

        } finally {
            FileService.saveToStatsFile();
        }


    }

    public void displayStatstics() {
        FileService.loadFromStatsticsFile();
        System.out.println("---------------");
        System.out.println("Spel Statstik");
        System.out.println("---------------");
        for (PokeStat pokeStat : pokemonStatsList) {
            System.out.println("namn: " + pokeStat.getPokemonName() + "| Antalet vinst: " + pokeStat.getTotalNumberOfWins() + "| Antalet förlust: " + pokeStat.getTotalNumberOfDefeats() + " | Vinst ratio: " + pokeStat.getWinRatio() + " | Förlust ratio: " + pokeStat.getLossRatio());
        }
    }
}
