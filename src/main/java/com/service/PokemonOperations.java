package com.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapping.GameStats;
import com.mapping.PokeStat;
import com.models.Attack;
import com.models.Pokemon;
import com.models.Type;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PokemonOperations {

    public static List<Pokemon> pokemonList = new ArrayList<>();
    public static List<GameStats> gameStatsList = new ArrayList<>();
    public List<Pokemon> wildPokemonList = new ArrayList<>();
    public static List<PokeStat> pokemonStatsList = new ArrayList<>();
    protected static final String pathToPokemon_Data_JSON = "src/main/resources/pokemon_data.json";
    protected static final String pathToWild_Pokemon_JSON = "src/main/resources/wild_pokemon.json";
    protected static final String pathToBattle_Result_JSON = "src/main/resources/battle_result.json";
    protected static final String pathToStats_File_JSON = "src/main/resources/stats.json";
    public static ObjectMapper objectMapper = new ObjectMapper();
    public static Scanner scan = new Scanner(System.in);
    protected File pokemonDataFile = new File(pathToPokemon_Data_JSON);
    protected File wildPokemonDataFile = new File(pathToWild_Pokemon_JSON);
    protected File matchDataFile = new File(pathToBattle_Result_JSON);
    protected static File statsFile = new File(pathToStats_File_JSON);

    //predifinerad pokemoner
    public void predefinedPokemon() {
        Attack attack_one = new Attack("atk_1", Type.Electric, 85, 30);
        Attack attack_two = new Attack("atk_2", Type.Water, 75, 40);
        List<Attack> list_one = new ArrayList<>(List.of(attack_one, attack_two));


        Attack attack_three = new Attack("atk_3", Type.Grass, 65, 50);
        Attack attack_four = new Attack("atk_4", Type.Water, 55, 60);
        List<Attack> list_two = new ArrayList<>(List.of(attack_three, attack_four));

        Attack attack_five = new Attack("atk_5", Type.Electric, 45, 70);
        Attack attack_six = new Attack("atk_6", Type.Fire, 35, 80);
        List<Attack> list_three = new ArrayList<>(List.of(attack_five, attack_six));

        Pokemon pokemon_one = new Pokemon("poke_1", Type.Electric, 100, 950, list_one);
        Pokemon pokemon_two = new Pokemon("poke_2", Type.Fire, 900, 450, list_two);
        Pokemon pokemon_three = new Pokemon("poke_3", Type.Grass, 600, 200, list_three);
        Pokemon pokemon_four = new Pokemon("poke_4", Type.Normal, 850, 750, list_one);
        Pokemon pokemon_five = new Pokemon("poke_5", Type.Grass, 500, 550, list_two);
        Pokemon pokemon_six = new Pokemon("poke_6", Type.Electric, 790, 750, list_three);

        pokemonList.addAll(Arrays.asList(pokemon_one, pokemon_two, pokemon_three, pokemon_four, pokemon_five, pokemon_six));

    }

    //återställ till seed data
    public void resetToSeededData() {
        pokemonList = new ArrayList<>();
        predefinedPokemon();
        System.out.println("Info!: Pokemon data har återställt till seed data");
    }
}
