package com.service;

import com.exceptions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.Attack;
import com.enums.Type;
import com.models.Pokemon;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PokemanService {
    final String pathToFileJson = "src/main/resources/pokemon_data.json";
    static ObjectMapper objectMapper = new ObjectMapper();
    static Scanner scan = new Scanner(System.in);
    File jsonFile = new File(pathToFileJson);
    public static List<Pokemon> pokemonList = new ArrayList<>();

    public void consoleMenu() {
        System.out.println("\nKonsoll Meny");
        System.out.println("======= ====");
        System.out.println("0. Visa Pokemoner (Båda Predefinierad och Nya)");
        System.out.println("1. Lägg till ny Pokemon");
        System.out.println("2. Ta bort Pokemon");
        System.out.println("3. Radera ett Pokemon");
        System.out.println("4. Spara pokemon listan till fil");
        System.out.println("5. Ladda Pokemoner från fil");
        System.out.println("6. Återställ till predifinierad/seed data");
        System.out.println("9. Avsluta program");
        System.out.println("Ange din väl ...");
        Scanner scan = new Scanner(System.in);
        String choice = scan.nextLine();
        switch (choice) {
            case "0" -> {
                showPredifinedPokemones();
                consoleMenu();
            }
            case "1" -> {
                createPokemon();
                consoleMenu();
            }
            case "2" -> {
                removePokemon();
                consoleMenu();
            }
            case "3" -> {
                editPokemon();
                consoleMenu();
            }
            case "4" -> {
                saveToFile();
                consoleMenu();
            }
            case "5" -> {
                loadFromFile();
                consoleMenu();
            }
            case "6" -> {
                resetToSeededData();
                consoleMenu();
            }
            case "9" -> System.exit(0);
            default -> {
                System.out.println("Error: Vänliga ange giltig meny val");
                consoleMenu();
            }
        }
    }

    //läs från fil
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

    //radera Attack
    public void editAttack(Pokemon pokemon) throws NoSuchElementException, NullPointerException {
        String attackName;
        String choice = null;
        boolean isValidAttackEntry = false;
        boolean isValidBaseDamage = false;
        boolean isValidAttackAccuracy = false;
        boolean isValidChoice = false;
        boolean attackFound = false;
        System.out.println("Välja 'l' om du vill lägga attack eller 'b' för att ta bort ett attack från Pokemon");
        String input = scan.nextLine();
        while (input != null) {
            switch (input.toLowerCase()) {
                case "l" -> {
                    while (!isValidAttackEntry) {
                        try {
                            Attack attack = new Attack();
                            System.out.println("Ange ett ny Attacknamn");
                            attackName = scan.nextLine();
                            String finalAttackName = attackName;
                            if (attackName.isBlank() || attackName.length() < 4 || attackName.length() > 20 || !attackName.matches("atk_\\d+")) {
                                throw new InvalidAttackEntryException("Attack namnet är ogiltig.");
                            }
                            if (pokemon.getAttacks().stream().anyMatch(a -> a.getName().equalsIgnoreCase(finalAttackName))) {
                                throw new DuplicateAttackEntryException("Attack namnet redan finns.");
                            }


                            System.out.println("Ange Attack Type:[Ange 'g' för Grass,'e' för Electric, 'f' för Fire, 'n' för Normal]");
                            String attackType = scan.nextLine();
                            attackTypeValidation(attackType, attack);
                            System.out.println("Ange Base Damage värde för " + attackName);
                            while (!isValidBaseDamage) {
                                try {
                                    String baseDamage = scan.nextLine().trim();
                                    if (Integer.parseInt(baseDamage) >= 1 && Integer.parseInt(baseDamage) <= 100) {
                                        attack.setBaseDamage(Integer.parseInt(baseDamage));
                                        isValidBaseDamage = true;
                                    } else if (Integer.parseInt(baseDamage) < 1 || Integer.parseInt(baseDamage) > 100) {
                                        throw new NumberNotInIntervalException("Ogiltigt värde för Base Damage");
                                    } else if (Integer.parseInt(baseDamage) > Integer.MAX_VALUE || Integer.parseInt(baseDamage) < Integer.MIN_VALUE) {
                                        throw new NumberFormatException();
                                    }

                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Ogiltigt värde för Base Damage, Vänliga ange bara giltig Integer värde");
                                } catch (NumberNotInIntervalException e) {
                                    System.out.println("Error: Ogiltigt värde för Base Damage, Ange mellan 1 och 100");
                                }
                            }

                            System.out.println("Ange Accuracy värde för " + attackName);
                            while (!isValidAttackAccuracy) {
                                try {
                                    String attackAccuracy = scan.nextLine().trim();
                                    if (Integer.parseInt(attackAccuracy) >= 1 && Integer.parseInt(attackAccuracy) <= 100) {
                                        attack.setAccuracy(Integer.parseInt(attackAccuracy));
                                        isValidAttackAccuracy = true;
                                    } else if (Integer.parseInt(attackAccuracy) < 1 || Integer.parseInt(attackAccuracy) > 100) {
                                        throw new NumberNotInIntervalException("Ogiltigt värde för Accuracy");
                                    } else if (Integer.parseInt(attackAccuracy) > Integer.MAX_VALUE || Integer.parseInt(attackAccuracy) < Integer.MIN_VALUE) {
                                        throw new NumberFormatException();
                                    }

                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Ogiltigt värde för Accuracy, Vänliga ange bara giltig Integer värde");
                                } catch (NumberNotInIntervalException e) {
                                    System.out.println("Error: Ogiltigt värde för Accuracy, Ange mellan 1 och 100");
                                }
                            }

                            attack.setName(attackName);
                            pokemon.getAttacks().add(attack);
                            isValidAttackEntry = true;
                            System.out.println("Success! Ny Attacken |" + attackName + "| har lägt till " + pokemon.getName());
                            consoleMenu();

                        } catch (InvalidAttackEntryException | NullPointerException e) {
                            System.out.println("Attack namnet är ogiltig! Försök igen:");
                        } catch (DuplicateAttackEntryException e) {
                            System.out.println("Attack namnet redan finns! Ange nytt namn");
                        }


                    }


                }
                case "b" -> {
                    List<Attack> pokemonAttacks = pokemon.getAttacks();
                    if (pokemonAttacks != null) {
                        System.out.println("Vill du ta bort hela Attack List eller en stycken? välj 'h' för hela listan och 'e' för en stycken");
                        while (!isValidChoice) {
                            choice = scan.nextLine();
                            if (!choice.isBlank() && (choice.equalsIgnoreCase("h") || choice.equalsIgnoreCase("e"))) {
                                isValidChoice = true;
                            }
                        }
                        if (choice.equalsIgnoreCase("h")) {
                            if (pokemon.getAttacks() != null) {
                                pokemon.getAttacks().clear();
                                System.out.println("Success! Alla attacker för " + pokemon.getName() + " har tagits bort!");
                                consoleMenu();
                            } else {
                                System.out.println("Attack listan för " + pokemon.getName() + "är tom");
                                consoleMenu();
                            }
                        }
                        if (choice.equalsIgnoreCase("e")) {
                            System.out.println("Vilken attack vill du ta bort från " + pokemon.getName() + " ?, ange attack namnet");
                            while (!attackFound) {
                                try {
                                    String attackToDelete = scan.nextLine().trim();
                                    pokemonAttacks.remove(pokemonAttacks.stream()
                                            .filter(a -> a.getName().equalsIgnoreCase(attackToDelete))
                                            .findFirst()
                                            .orElseThrow(() -> new AttackNotFoundException("Attack hitta ej.")));
                                    System.out.println("Success: Attack " + attackToDelete + " har tagits bort från Pokemon " + pokemon.getName());
                                    attackFound = true;
                                    consoleMenu();

                                } catch (NullPointerException e) {
                                    System.out.println("Error: Lyckades inte att ta bort attacken! Försök igen");

                                } catch (AttackNotFoundException e) {
                                    System.out.println("Error: Lyckades inte att hitta Attacken! Försök igen");
                                }
                            }
                        }
                    }
                }

                default -> {
                    System.out.println("Error: Du har angett ogiltigt väl. Ange giltig väl: ['l' eller 'b']");
                    input = scan.nextLine();
                    continue;
                }
            }
            break;
        }
    }

    //predifinerad pokemoner
    public void predefinedPokemon() {
        Attack attack_one = new Attack("atk_1", Type.Electric, 85, 30);
        Attack attack_two = new Attack("atk_2", Type.Fire, 75, 40);
        List<Attack> list_one = new ArrayList<>(List.of(attack_one, attack_two));


        Attack attack_three = new Attack("atk_3", Type.Grass, 65, 50);
        Attack attack_four = new Attack("atk_4", Type.Normal, 55, 60);
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

    public void showPredifinedPokemones() {
        try {
            String predefinedPokemons = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pokemonList);
            System.out.println("Predefinierad Pokemon Data:\n\n" + predefinedPokemons);
        } catch (JsonProcessingException e) {
            System.out.println("Error: Någonting gick fel med JSON");
        }
    }

    //skapa pokemon
    public void createPokemon() throws InvalidPokemonNameFormat, DuplicatePokemonException {
        String maxHp;
        String currentHp;
        String numberOfAttacks = null;
        String baseDamage;
        String attackAccuracy;
        String pokemonName;
        String attackName;
        boolean isValidPokemonName = false;
        boolean isValidMaxHp = false;
        boolean isValidCurrentHp = false;
        boolean isValidNumberOfAttacks = false;
        boolean isValidBaseDamage = false;
        boolean isValidAttackAccuracy = false;


        ArrayList<Attack> attackList = new ArrayList<>();
        //validera pokemon namn
        Pokemon pokemon = new Pokemon();

        System.out.println("Ange Pokemon namn");
        while (!isValidPokemonName) {
            try {
                pokemonName = scan.nextLine().trim();
                String finalPokemonName = pokemonName;

                if (!pokemonName.matches("poke_\\d+")) {
                    throw new InvalidPokemonNameFormat("Invalid pokemon namn format");
                }
                if (pokemonList.stream().anyMatch(p -> p.getName().equalsIgnoreCase(finalPokemonName))) {
                    throw new DuplicatePokemonException("Pokemon redan finns");
                }
                if (!pokemonName.isBlank() && (pokemonName.length() >= 6 && pokemonName.length() <= 20)) {
                    pokemon.setName(pokemonName);
                    isValidPokemonName = true;
                }

            } catch (InvalidPokemonNameFormat e) {
                System.out.println("Error: Följ gärna 'poke_nummer' namning format! Ange ett ny namn");

            } catch (DuplicatePokemonException e) {
                System.out.println("Error: Pokemonen redan finns! Ange ett ny namn");
            }
        }

        System.out.println("Ange Type:[Ange 'g' för Grass,'e' för Electric, 'f' för Fire, 'n' för Normal]");
        String pokemanAttackType = scan.nextLine();
        pokemanAttackTypeValidation(pokemanAttackType, pokemon);
        System.out.println("Ange Maximalt HP för Pokemonen");
        while (!isValidMaxHp) {
            try {
                maxHp = scan.nextLine().trim();
                if (Integer.parseInt(maxHp) >= 1 && Integer.parseInt(maxHp) <= 1000) {
                    pokemon.setMaxHp(Integer.parseInt(maxHp));
                    isValidMaxHp = true;
                } else if (Integer.parseInt(maxHp) < 1 || Integer.parseInt(maxHp) > 1000) {
                    throw new NumberNotInIntervalException("Ogiltigt värde för Max HP");
                } else if (Integer.parseInt(maxHp) > Integer.MAX_VALUE || Integer.parseInt(maxHp) < Integer.MIN_VALUE) {
                    throw new NumberFormatException();
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Ogiltigt värde för Max HP, Vänliga ange bara giltig Integer värde");
            } catch (NumberNotInIntervalException e) {
                System.out.println("Error: Ogiltigt värde för Max HP, Ange mellan 1 och 1000");
            }
        }

        System.out.println("Ange Current HP för Pokemonen");
        while (!isValidCurrentHp) {
            try {
                currentHp = scan.nextLine().trim();
                if (Integer.parseInt(currentHp) >= 1 && Integer.parseInt(currentHp) <= 1000) {
                    pokemon.setCurrentHp(Integer.parseInt(currentHp));
                    isValidCurrentHp = true;
                } else if (Integer.parseInt(currentHp) < 1 || Integer.parseInt(currentHp) > 1000) {
                    throw new NumberNotInIntervalException("Ogiltigt värde för Current HP");
                } else if (Integer.parseInt(currentHp) > Integer.MAX_VALUE || Integer.parseInt(currentHp) < Integer.MIN_VALUE) {
                    throw new NumberFormatException();
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Ogiltigt värde för Current HP, Vänliga ange bara giltig Integer värde");
            } catch (NumberNotInIntervalException e) {
                System.out.println("Error: Ogiltigt värde för Current HP, Ange mellan 1 och 1000");
            }
        }
        System.out.println("Hur många attacker vill du skaffa för Pokemon " + pokemon.getName() + " ?");
        while (!isValidNumberOfAttacks) {
            try {
                numberOfAttacks = scan.nextLine().trim();
                if (Integer.parseInt(numberOfAttacks) >= 1 && Integer.parseInt(numberOfAttacks) <= 4) {
                    isValidNumberOfAttacks = true;
                } else if (Integer.parseInt(numberOfAttacks) < 1 || Integer.parseInt(numberOfAttacks) > 4) {
                    throw new NumberNotInIntervalException("Ogiltigt värde för Antalet Attacker");
                } else if (Integer.parseInt(numberOfAttacks) > Integer.MAX_VALUE || Integer.parseInt(numberOfAttacks) < Integer.MIN_VALUE) {
                    throw new NumberFormatException();
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Ogiltigt värde för  Antalet Attacker, Vänliga ange bara giltig Integer värde");
            } catch (NumberNotInIntervalException e) {
                System.out.println("Error: Ogiltigt värde för  Antalet Attacker, Ange mellan 1 och 4");
            }
        }

        for (int i = 1; i <= Integer.parseInt(numberOfAttacks); i++) {
            System.out.println("Skapa attack nummer: " + i);
            //skapa och validera Attack namn
            boolean isValidAttackEntry = false;
            Attack attack = new Attack();
            System.out.println("Ange Attack Namn! Följ gärna formatten 'atk_nummer'");
            while (!isValidAttackEntry) {
                try {
                    attackName = scan.nextLine();
                    String finalAttackName = attackName;
                    if (attackName.isBlank() || attackName.length() < 4 || attackName.length() > 20 || !attackName.matches("atk_\\d+")) {
                        throw new InvalidAttackEntryException("Attack namnet är ogiltig.");
                    }
                    if (pokemon.getAttacks().stream().anyMatch(a -> a.getName().equalsIgnoreCase(finalAttackName))) {
                        throw new DuplicateAttackEntryException("Attack namnet redan finns.");
                    }

                    System.out.println("Ange Attack Type:[Ange 'g' för Grass,'e' för Electric, 'f' för Fire, 'n' för Normal]");
                    String attackType = scan.nextLine();
                    attackTypeValidation(attackType, attack);

                    System.out.println("Ange Base Damage värde för " + attackName);
                    while (!isValidBaseDamage) {
                        try {
                            baseDamage = scan.nextLine().trim();
                            if (Integer.parseInt(baseDamage) >= 1 && Integer.parseInt(baseDamage) <= 100) {
                                attack.setBaseDamage(Integer.parseInt(baseDamage));
                                isValidBaseDamage = true;
                            } else if (Integer.parseInt(baseDamage) < 1 || Integer.parseInt(baseDamage) > 100) {
                                throw new NumberNotInIntervalException("Ogiltigt värde för Base Damage");
                            } else if (Integer.parseInt(baseDamage) > Integer.MAX_VALUE || Integer.parseInt(baseDamage) < Integer.MIN_VALUE) {
                                throw new NumberFormatException();
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("Error: Ogiltigt värde för Base Damage, Vänliga ange bara giltig Integer värde");
                        } catch (NumberNotInIntervalException e) {
                            System.out.println("Error: Ogiltigt värde för Base Damage, Ange mellan 1 och 100");
                        }
                    }

                    System.out.println("Ange Accuracy värde för " + attackName);
                    while (!isValidAttackAccuracy) {
                        try {
                            attackAccuracy = scan.nextLine().trim();
                            if (Integer.parseInt(attackAccuracy) >= 1 && Integer.parseInt(attackAccuracy) <= 100) {
                                attack.setAccuracy(Integer.parseInt(attackAccuracy));
                                isValidAttackAccuracy = true;
                            } else if (Integer.parseInt(attackAccuracy) < 1 || Integer.parseInt(attackAccuracy) > 100) {
                                throw new NumberNotInIntervalException("Ogiltigt värde för Accuracy");
                            } else if (Integer.parseInt(attackAccuracy) > Integer.MAX_VALUE || Integer.parseInt(attackAccuracy) < Integer.MIN_VALUE) {
                                throw new NumberFormatException();
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("Error: Ogiltigt värde för Accuracy, Vänliga ange bara giltig Integer värde");
                        } catch (NumberNotInIntervalException e) {
                            System.out.println("Error: Ogiltigt värde för Accuracy, Ange mellan 1 och 100");
                        }
                    }
                    attack.setName(attackName);
                    isValidAttackEntry = true;

                } catch (InvalidAttackEntryException | NullPointerException e) {
                    System.out.println("Attack namnet är ogiltig! Försök igen:");
                } catch (DuplicateAttackEntryException e) {
                    System.out.println("Attack namnet redan finns! Ange nytt namn");
                }

            }
            attackList.add(attack);
            pokemon.setAttacks(attackList);
            //reset för ny base damage och attack accuracy
            isValidAttackAccuracy = false;
            isValidBaseDamage = false;
        }
        pokemonList.add(pokemon);
        System.out.println("Success! Pokemon " + pokemon.getName() + " skapades och lagt till Predifinierad pokemoner! Om du vill spara den till fil, använd väl #4 från meny.");
        consoleMenu();
    }

    //radera Pokemon
    public void editPokemon() {
        String maxHp;
        String currentHp;
        boolean isValidMaxHp = false;
        boolean isValidCurrentHp = false;
        boolean isValidPokemonName = false;
        System.out.println("Ange pokemon namn att Radera");
        String pokemonNamn = scan.nextLine();
        for (Pokemon pokemon : pokemonList) {
            if (pokemon.getName().equalsIgnoreCase(pokemonNamn)) {
                try {
                    String pokemonJsonInfo = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pokemon);
                    System.out.println("Pokemon info:\n" + pokemonJsonInfo);
                    System.out.println("Vilken fält vill du ändra? Ange 'n' för namn, 't' för Type, 'm' för Max HP, 'c' för Current HP. För att radera Attack, Tryck 'a'.");
                    String val = scan.nextLine();
                    while (val != null) {
                        if (val.equalsIgnoreCase("n")) {
                            System.out.println("Ange ny pokemon namn");

                            while (!isValidPokemonName) {
                                try {
                                    String pokemonName = scan.nextLine().trim();
                                    String finalPokemonName = pokemonName;

                                    if (!pokemonName.matches("poke_\\d+")) {
                                        throw new InvalidPokemonNameFormat("Invalid pokemon namn format");
                                    }
                                    if (pokemonList.stream().anyMatch(p -> p.getName().equalsIgnoreCase(finalPokemonName))) {
                                        throw new DuplicatePokemonException("Pokemon redan finns");
                                    }
                                    if (!pokemonName.isBlank() && (pokemonName.length() >= 6 && pokemonName.length() <= 20)) {
                                        pokemon.setName(pokemonName);
                                        System.out.println("Success! Pokemon namn är ändrat till " + pokemonName);

                                        isValidPokemonName = true;
                                        consoleMenu();
                                    }

                                } catch (InvalidPokemonNameFormat e) {
                                    System.out.println("Error: Följ gärna 'poke_nummer' namning format! Ange ett ny namn");

                                } catch (DuplicatePokemonException e) {
                                    System.out.println("Error: Pokemonen redan finns! Ange ett ny namn");
                                }
                            }

                        } else if (val.equalsIgnoreCase("t")) {
                            System.out.println("Ange Attack Type:[Ange 'g' för Grass,'e' för Electric, 'f' för Fire, 'n' för Normal]");
                            String attackType = scan.nextLine();
                            while (attackType != null) {
                                switch (attackType.toLowerCase()) {
                                    case "g":
                                        pokemon.setType(Type.Grass);
                                        System.out.println("Success! Pokemon Typ är ändrat till Grass");
                                        break;
                                    case "e":
                                        pokemon.setType(Type.Electric);
                                        System.out.println("Success! Pokemon Typ är ändrat till Electric");
                                        break;
                                    case "f":
                                        pokemon.setType(Type.Fire);
                                        System.out.println("Success! Pokemon Typ är ändrat till Fire");
                                        break;
                                    case "n":
                                        pokemon.setType(Type.Normal);
                                        System.out.println("Success! Pokemon Typ är ändrat till Normal");
                                        break;
                                    default:
                                        System.out.println("Error: Ogiltigt Attack Typ, Ange valid val igen! ['g' ,'e' ,'f' ,'n']]");
                                        attackType = scan.nextLine();
                                        continue;
                                }
                                break;
                            }
                            consoleMenu();
                        } else if (val.equalsIgnoreCase("m")) {
                            System.out.println("Ange Maximalt HP för Pokemonen");
                            while (!isValidMaxHp) {
                                try {
                                    maxHp = scan.nextLine().trim();
                                    if (Integer.parseInt(maxHp) < 1 || Integer.parseInt(maxHp) > 1000) {
                                        throw new NumberNotInIntervalException("Ogiltigt värde för Max HP");
                                    }
                                    if (Integer.parseInt(maxHp) > Integer.MAX_VALUE || Integer.parseInt(maxHp) < Integer.MIN_VALUE) {
                                        throw new NumberFormatException();
                                    }
                                    if (Integer.parseInt(maxHp) >= 1 && Integer.parseInt(maxHp) <= 1000) {
                                        pokemon.setMaxHp(Integer.parseInt(maxHp));
                                        System.out.println("Success! " + pokemon.getName() + " maximalt HP ändrats till " + maxHp);
                                        isValidMaxHp = true;
                                        consoleMenu();
                                    }

                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Ogiltigt värde för Max HP, Vänliga ange bara giltig Integer värde");
                                } catch (NumberNotInIntervalException e) {
                                    System.out.println("Error: Ogiltigt värde för Max HP, Ange mellan 1 och 1000");
                                }
                            }


                        } else if (val.equalsIgnoreCase("c")) {
                            System.out.println("Ange Current HP för Pokemonen");
                            while (!isValidCurrentHp) {
                                try {
                                    currentHp = scan.nextLine().trim();
                                    if (Integer.parseInt(currentHp) < 1 || Integer.parseInt(currentHp) > 1000) {
                                        throw new NumberNotInIntervalException("Ogiltigt värde för Current HP");
                                    }
                                    if (Integer.parseInt(currentHp) > Integer.MAX_VALUE || Integer.parseInt(currentHp) < Integer.MIN_VALUE) {
                                        throw new NumberFormatException();
                                    }
                                    if (Integer.parseInt(currentHp) >= 1 && Integer.parseInt(currentHp) <= 1000) {
                                        pokemon.setCurrentHp(Integer.parseInt(currentHp));
                                        isValidCurrentHp = true;
                                        System.out.println("Success! " + pokemon.getName() + " Current HP ändrats till " + currentHp);
                                        consoleMenu();
                                    }

                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Ogiltigt värde för Current HP, Vänliga ange bara giltig Integer värde");
                                } catch (NumberNotInIntervalException e) {
                                    System.out.println("Error: Ogiltigt värde för Current HP, Ange mellan 1 och 1000");
                                }
                            }

                        } else if (val.equalsIgnoreCase("a")) {
                            editAttack(pokemon);
                        } else {
                            System.out.println("Error: Ogilitigt val ändring av Pokemon, ange valid val igen! ['n' för namn , 't' för Type, 'm' för Max HP, 'c' för Current HP,'a' för Attack]");
                            val = scan.nextLine();
                        }
                    }
                } catch (JsonProcessingException e) {
                    System.out.println("Någonting gick fel med JSON");
                }
            }

        }
        System.out.println("Error: Pokemonen finns inte i listan");
        consoleMenu();
    }

    //ta bort pokemon
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

    //återställ till seed data
    public void resetToSeededData() {
        pokemonList=new ArrayList<>();
        predefinedPokemon();
        System.out.println("Info!: Pokemon data har återställt till seed data");
    }

    private void attackTypeValidation(String attackType, Attack attack) {
        while (attackType != null) {
            switch (attackType.toLowerCase()) {
                case "g":
                    attack.setType(Type.Grass);
                    break;
                case "e":
                    attack.setType(Type.Electric);
                    break;
                case "f":
                    attack.setType(Type.Fire);
                    break;
                case "n":
                    attack.setType(Type.Normal);
                    break;
                default:
                    System.out.println("Error: Ogiltigt Attack Typ, Ange igen!");
                    attackType = scan.nextLine();
                    continue;
            }
            break;
        }
    }

    private void pokemanAttackTypeValidation(String pokemanAttackType, Pokemon pokemon) {
        while (pokemanAttackType != null) {
            switch (pokemanAttackType.toLowerCase()) {
                case "g":
                    pokemon.setType(Type.Grass);
                    break;
                case "e":
                    pokemon.setType(Type.Electric);
                    break;
                case "f":
                    pokemon.setType(Type.Fire);
                    break;
                case "n":
                    pokemon.setType(Type.Normal);
                    break;
                default:
                    System.out.println("Error: Ogiltigt Attack Typ, Ange igen!");
                    pokemanAttackType = scan.nextLine();
                    continue;
            }
            break;
        }
    }
}
