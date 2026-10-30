package com.service;

import com.exceptions.PokemonStatNotFoundInFileException;
import com.mapping.GameStats;
import com.mapping.PokeStat;
import com.models.Pokemon;
import com.repository.FileService;

import java.util.*;
import java.util.stream.Collectors;

import static com.service.PokemonOperations.gameStatsList;
import static com.service.PokemonOperations.pokemonStatsList;

public class StatsticsService  {

    public void createAndSavePokemonStats(Pokemon pokemon, String label) throws PokemonStatNotFoundInFileException {
        try {
            FileService.loadFromStatsticsFile();
            PokeStat pokemonDataInStatFile = pokemonStatsList.stream().filter(pokeStat -> pokeStat.getPokemonName().equalsIgnoreCase(pokemon.getName()))
                    .findFirst()
                    .orElseThrow(() -> new PokemonStatNotFoundInFileException("Pokemonstat hittas ej"));

            if (label.equalsIgnoreCase("win")) {
                int currentTotalNumberOfWins = pokemonDataInStatFile.getTotalNumberOfWins();
                pokemonStatsList.stream().filter(pokeStat -> pokeStat.getPokemonName().equalsIgnoreCase(pokemon.getName()))
                        .findFirst().get().setTotalNumberOfWins(currentTotalNumberOfWins + 1);

            } else if (label.equalsIgnoreCase("loss")) {
                int currentTotalNumberOfDefeats = pokemonDataInStatFile.getTotalNumberOfDefeats();
                pokemonStatsList.stream().filter(pokeStat -> pokeStat.getPokemonName().equalsIgnoreCase(pokemon.getName()))
                        .findFirst().get().setTotalNumberOfWins(currentTotalNumberOfDefeats + 1);
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

    public String mostUsedPokemon() {
        List<Map.Entry<String, Long>> gameList = gameStatsList.stream().collect(Collectors.groupingBy(GameStats::getUserPokemonName, Collectors.counting()))
                .entrySet()
                .stream().toList();

        return maxValue(gameList);
    }

    public String mostUsedCpuPokemon() {
        List<Map.Entry<String, Long>> gameList = gameStatsList.stream().collect(Collectors.groupingBy(GameStats::getCpuPokemonName, Collectors.counting()))
                .entrySet()
                .stream().toList();

        return maxValue(gameList);
    }

    private String maxValue(List<Map.Entry<String, Long>> entryList) {
        String maxValueKey = null;
        long maxValue = 0;
        int count = 0;
        ArrayList<Long> maxList = new ArrayList<>();
        for (Map.Entry<String, Long> entry : entryList) {
            long currentValue = entry.getValue();
            if (currentValue > maxValue) {
                maxValue = currentValue;
                maxValueKey = entry.getKey();
            }
            if (currentValue == maxValue) {
                maxList.add(currentValue);
            }
        }
        long maxInt = Collections.max(maxList);
        for (Long num : maxList) {
            if (num == maxInt)
                count = count + 1;
        }
        if (maxList.size() > 1 && count > 1)
            return "Ingen";
        return maxValueKey != null ? maxValueKey : "Ingen";

    }

    public void displayStatstics() {
        FileService.loadFromStatsticsFile();
        System.out.println("---------------");
        System.out.println("Spel Statstik");
        System.out.println("---------------");
        for (PokeStat pokeStat : pokemonStatsList) {
            System.out.println("Pokemon namn: " + pokeStat.getPokemonName() + "| Antalet vinst: " + pokeStat.getTotalNumberOfWins() + "| Antalet förlust: " + pokeStat.getTotalNumberOfDefeats() + " | Vinst ratio: " + pokeStat.getWinRatio() + " | Förlust ratio: " + pokeStat.getLossRatio());
            System.out.println(".................. .................. ............. ........ ......... ........... ........... .........");
        }
        System.out.println("---------- ---------------  ----- ------- ---------- ---------- ---------------- -------- -- ----------");
        System.out.println("Mest använt Pokemon från samlingen i battle: " + mostUsedPokemon().toUpperCase());
        System.out.println("................ .................... ..................... .............................. ............");
        System.out.println("Mest använt Pokemon från CPU i battle: " + mostUsedCpuPokemon().toUpperCase());

    }
}
