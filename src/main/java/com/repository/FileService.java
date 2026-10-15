package com.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.mapping.GameStats;
import com.mapping.PokeStat;
import com.models.Pokemon;
import com.service.PokemonOperations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileService extends PokemonOperations {


    public static void saveToStatsFile() {
        try {
            String writeJsonToStatFile = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pokemonStatsList);
            Files.writeString(Paths.get(pathToStats_File_JSON), writeJsonToStatFile);
            System.out.println("Success: Lyckades att skriva till statsfil!");
        } catch (IOException e) {
            System.out.println("Error: Lyckades inte att spara till fil: " + e.getMessage());
        }
    }

    public void loadFromStatsticsFile() {
        try {
            ArrayList<PokeStat> pokemonStatJSON = objectMapper.readValue(statsFile, new TypeReference<>() {
            });
            pokemonStatsList = pokemonStatJSON;
            System.out.println("Pokemon data från fil har laddats ...");
        } catch (FileNotFoundException e) {
            System.out.println("Error: Lyckades inte att hitta filen");
        } catch (MismatchedInputException e) {
            System.out.println("General stats fil är tom ...");
        } catch (IOException e) {
            System.out.println("Error: Lyckades inte att ladda från statsfil.");
        }
    }

    public void loadFromFile() {
        try {
            ArrayList<Pokemon> pokemonJSON = objectMapper.readValue(pokemonDataFile, new TypeReference<>() {
            });
            pokemonList = pokemonJSON;
            System.out.println("Pokemon data från fil har laddats ...");
        } catch (FileNotFoundException e) {
            System.out.println("Error: Lyckades inte att hitta filen");
        } catch (IOException e) {
            System.out.println("Error: Lyckades inte att spara till fil");
        }
    }

    //spara till fil
    public void saveToFile() {
        try {
            String writeJsonToFile = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pokemonList);
            Files.writeString(Paths.get(pathToPokemon_Data_JSON), writeJsonToFile);
            System.out.println("Success: Lyckades att skriva pokemon data!");
        } catch (IOException e) {
            System.out.println("Error: Lyckades inte att spara till fil: " + e.getMessage());
        }
    }

    public void loadFromMatchStatsticsFile() {
        try {
            ArrayList<GameStats> statJSON = objectMapper.readValue(matchDataFile, new TypeReference<>() {
            });
                gameStatsList = statJSON;
                System.out.println("Spel data från statsfil har laddats ...");
        } catch (FileNotFoundException e) {
            System.out.println("Error: Lyckades inte att hitta filen");
        } catch (MismatchedInputException e) {
            System.out.println("Spel stats fil är tom ...");
        } catch (IOException e) {
            System.out.println("Error: Lyckades inte att ladda från statsfil.");
        }
    }

    //spara till fil
    public void saveToBattleResultFile() {
        try {
            String writeJsonToFile = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(gameStatsList);
            Files.writeString(Paths.get(pathToBattle_Result_JSON), writeJsonToFile);
            System.out.println("Success: Lyckades att skriva match data till spelsresultat fil!");
        } catch (IOException e) {
            System.out.println("Error: Lyckades inte att spara till statsfil: " + e.getMessage());
        }
    }

    public void displayPokemonsFromFile() {
        loadFromFile();
        System.out.println(pokemonList.toString());
    }

    public List<Pokemon> loadWildPokemons() {
        try {
            ArrayList<Pokemon> wildPokemonJSON = objectMapper.readValue(wildPokemonDataFile, new TypeReference<>() {
            });
            wildPokemonList = wildPokemonJSON;
        } catch (FileNotFoundException e) {
            System.out.println("Error: Lyckades inte att hitta filen");
        } catch (IOException e) {
            System.out.println("Error: Lyckades inte att spara till fil");
        }
        return wildPokemonList;
    }
}
