package com.service;

import com.models.Pokemon;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReadPokemon extends PokemonOperations {

    public ReadPokemon(Scanner scan, List<Pokemon> pokemonList) {
        super(scan, pokemonList);
    }

    public void showPredifinedPokemones() {
        if (pokemonList != null && !pokemonList.isEmpty()) {
            System.out.println("Predefinierad Pokemon Data:\n\n" + pokemonList.stream().map(Pokemon::toString).collect(Collectors.joining(" ")));
        } else {
            System.out.println("Det finns ingen pokemon att visa");
        }
    }
}
