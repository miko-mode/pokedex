package com.service;

import com.models.Pokemon;

import static com.ui.Main.consoleMenu;

public class DeletePokemon extends PokemonOperations {

    public void removePokemon() {
        System.out.println("Ange pokemon namn att ta bort");
        String pokemonNamn = scan.nextLine();
        for (Pokemon pokemon : pokemonList) {
            if (pokemon.getName().equalsIgnoreCase(pokemonNamn)) {
                System.out.println("Pokemon info:\n" + pokemon.toString());
                pokemonList.remove(pokemon);
                System.out.println("Success: Pokemon " + pokemonNamn + " har tagits bort från listan!");
                consoleMenu();
            }
        }
        System.out.println("Error: Ange giltig pokemon namn som finns redan. Tom värde är inte tillåten. ");
        consoleMenu();
    }
}

