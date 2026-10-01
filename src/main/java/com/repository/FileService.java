package com.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.models.Pokemon;
import com.service.PokemonOperations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileService  extends PokemonOperations {
    public void loadFromFile() {
        try {
            ArrayList<Pokemon> pokemonJson = objectMapper.readValue(jsonFile, new TypeReference<>() {
            });
            pokemonList = pokemonJson;
            String outputJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pokemonJson);
            System.out.println(outputJson);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Lyckades inte att hitta filen");
        } catch (IOException e) {
            System.out.println("Error: Lyckades inte att spara till fil");
        }
    }

    //spara till fil
    public void saveToFile() {
        try {
            String writeJsonToFile=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pokemonList);
            Files.writeString(Paths.get(pathToFileJson),writeJsonToFile);
            System.out.println("Success: Lyckades att skriva till filen!");
        } catch (IOException e) {
            System.out.println("Error: Lyckades inte att spara till fil: " + e.getMessage());
        }
    }
}
