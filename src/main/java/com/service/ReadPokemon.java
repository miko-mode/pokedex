package com.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public class ReadPokemon extends PokemonOperations {

    public void showPredifinedPokemones() {
        try {
            String predefinedPokemons = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pokemonList);
            System.out.println("Predefinierad Pokemon Data:\n\n" + predefinedPokemons);
        } catch (JsonProcessingException e) {
            System.out.println("Error: Någonting gick fel med JSON");
        }
    }
}
