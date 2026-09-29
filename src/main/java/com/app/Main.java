package com.app;

import com.models.Pokemon;
import com.service.PokemanService;

public class Main {
    public static void main(String[] args) {
        PokemanService pokemanService=new PokemanService();
        pokemanService.predefinedPokemon();
        pokemanService.consoleMenu();
    }
}