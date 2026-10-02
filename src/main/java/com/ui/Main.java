package com.ui;


import com.repository.FileService;
import com.service.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        PokemonOperations pokemonOperations = new PokemonOperations();
        FileService fileService = new FileService();
        //load från fil automatiskt med start
        pokemonOperations.predefinedPokemon();
        fileService.loadFromFile();
        consoleMenu();
    }

    public static void consoleMenu() {
        System.out.println("\nKonsoll Meny");
        System.out.println("======= ====");
        System.out.println("0. Visa Pokemoner (Båda Predefinierad och Nya)");
        System.out.println("1. Lägg till ny Pokemon");
        System.out.println("2. Ta bort Pokemon");
        System.out.println("3. Redigera/ändra ett Pokemon");
        System.out.println("4. Spara pokemon listan till fil");
        System.out.println("5. Ladda och Visa Pokemoner från fil");
        System.out.println("6. Återställ till predifinierad/seed data");
        System.out.println("9. Avsluta program");
        System.out.println("Ange din väl ...");
        Scanner scan = new Scanner(System.in);
        String choice = scan.nextLine();
        switch (choice) {
            case "0" -> {
                new ReadPokemon().showPredifinedPokemones();
                consoleMenu();
            }
            case "1" -> {
                new CreatePokemon().createPokemon();
                consoleMenu();
            }
            case "2" -> {
                new DeletePokemon().removePokemon();
                consoleMenu();
            }
            case "3" -> {
                new EditPokemon().editPokemon();
                consoleMenu();
            }
            case "4" -> {
                new FileService().saveToFile();
                consoleMenu();
            }
            case "5" -> {
                new FileService().displayPokemonsFromFile();
                consoleMenu();
            }
            case "6" -> {
                new PokemonOperations().resetToSeededData();
                consoleMenu();
            }
            case "9" -> {
                //spara till fil automatiskt med exit
                System.out.println("sparar andringar till fil ...");
                new FileService().saveToFile();
                System.exit(0);
            }
            default -> {

                System.out.println("Error: Vänliga ange giltig meny val");
                consoleMenu();
            }
        }
    }
}
