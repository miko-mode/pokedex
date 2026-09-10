package com.app;

import com.models.Pokemon;
import com.service.PokemanService;

public class Main {
    public static void main(String[] args) {

        PokemanService pokemanService=new PokemanService();
        //load predefined pokemans
        pokemanService.predefinedPokemon();
        //show console menu
        pokemanService.consoleMenu();



    }
}