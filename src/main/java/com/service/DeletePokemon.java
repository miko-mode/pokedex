package com.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.models.Pokemon;

import static com.app.Main.consoleMenu;

public class DeletePokemon extends PokemonOperations {

    public void removePokemon() {
        System.out.println("Ange pokemon namn att ta bort");
        String pokemonNamn = scan.nextLine();
        for (Pokemon pokemon : pokemonList) {
            try {
                if (pokemon.getName().equalsIgnoreCase(pokemonNamn)) {
                    String pokemonJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pokemon);
                    System.out.println("Pokemon info:\n" + pokemonJson);
                    pokemonList.remove(pokemon);
                    System.out.println("Success: Pokemon " + pokemonNamn + " har tagits bort från listan!");
                    consoleMenu();
                }
            } catch (JsonProcessingException e) {
                System.out.println("Error: Någonting gick fel med JSON");
            }

        }
        System.out.println("Error: Ange giltig pokemon namn som finns redan. Tom värde är inte tillåten. ");
        consoleMenu();
    }
}
