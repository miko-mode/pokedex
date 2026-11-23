package com.ui;


import com.models.Attack;
import com.models.Pokemon;
import com.models.Type;
import com.mongodb.client.MongoDatabase;
import com.repository.AttackDaoJpa;
import com.repository.FileService;
import com.repository.MongoBattleLogRepository;
import com.repository.PokemonDaoJpa;
import com.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static boolean showMenu=true;
    public static void main(String[] args) {
        //load från fil automatiskt med start
        PokemonOperations.predefinedPokemon();
        //load från fil
        FileService.loadFromFile();
        FileService.loadFromMatchStatsticsFile();
        FileService.loadFromStatsticsFile();
        MongoBattleLogRepository mongoBattleLogRepository = new MongoBattleLogRepository();

        PokemonDaoJpa pokemonDao = new PokemonDaoJpa();
        Pokemon pokemon=new Pokemon("poke_1",Type.Water,33,22);
        pokemonDao.sparaPokemon(pokemon);

        AttackDaoJpa attackDaoJpa=new AttackDaoJpa();

        Attack attack=new Attack("atk_1", Type.Grass, 23,44,pokemon);
        attackDaoJpa.sparaAttack(attack);

        System.out.println(mongoBattleLogRepository.findByGameId("f3e1498e-f09b-4e53-807f-bfb23a0d9e2a"));


        consoleMenu();
    }


    public static void consoleMenu() {
        while(showMenu) {
            System.out.println("\nKonsoll Meny");
            System.out.println("======= ====");
            System.out.println("0. Visa Pokemoner (Båda Predefinierad och Nya)");
            System.out.println("1. Lägg till ny Pokemon");
            System.out.println("2. Ta bort Pokemon");
            System.out.println("3. Redigera/ändra ett Pokemon");
            System.out.println("4. Spara pokemon listan till fil");
            System.out.println("5. Ladda och Visa Pokemoner från fil");
            System.out.println("6. Återställ till predifinierad/seed data");
            System.out.println("7. Starta strid");
            System.out.println("8. Visa statstik");
            System.out.println("9. Avsluta program");
            System.out.println("Ange din väl ...");
            Scanner scan = new Scanner(System.in);
            String choice = scan.nextLine();
            switch (choice) {
                case "0" -> {
                    ReadPokemon.showPredifinedPokemones();
                    showMenu=true;
                }
                case "1" -> {
                    new CreatePokemon().createPokemon();
                    showMenu=true;
                }
                case "2" -> {
                    new DeletePokemon().removePokemon();
                    showMenu=true;
                }
                case "3" -> {
                    new EditPokemon().editPokemon();
                    showMenu=true;
                }
                case "4" -> {
                    FileService.saveToFile();
                    showMenu=true;
                }
                case "5" -> {
                    new FileService().displayPokemonsFromFile();
                    showMenu=true;
                }
                case "6" -> {
                    new PokemonOperations().resetToSeededData();
                    showMenu=true;
                }
                case "7" -> {
                    new BattleEngine().battleEngine();
                    showMenu=true;
                }
                case "8" -> {
                    new StatsticsService().displayStatstics();
                    showMenu=true;
                }
                case "9" -> {
                    //spara till fil automatiskt med exit
                    System.out.println("sparar andringar till fil ...");
                    FileService.saveToFile();
                    FileService.saveToBattleResultFile();
                    System.exit(0);
                }
                default -> {
                    System.out.println("Error: Vänliga ange giltig meny val");
                    consoleMenu();
                }
            }
        }
    }
}
