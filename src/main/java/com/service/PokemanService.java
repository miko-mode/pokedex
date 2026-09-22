package com.service;

import com.models.Attack;
import com.models.Pokemon;
import com.enums.Type;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PokemanService {

    public static List<Pokemon> pokemanList = new ArrayList<>();
    Scanner scan = new Scanner(System.in);

    public void consoleMenu() {
        System.out.println("\nKonsoll Menu");
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
                showPredifined();
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
                saveToFil();
                consoleMenu();
            }
            case "5" -> {
                loadFromFil();
                consoleMenu();
            }
            case "6" -> {
                resetToSeededData();
                consoleMenu();
            }
            case "9" -> System.exit(0);
        }


    }

    public void loadFromFil() {
        String pathToFile = "src/main/resources/pokemon_data.txt";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            List<Pokemon> pokemonListFromFile = new ArrayList<>();

            while ((line = bufferedReader.readLine()) != null) {
                if (line.isBlank()) continue;
                Pokemon pokemon = new Pokemon();
                List<Attack> attackListFromFile = new ArrayList<>();

                String[] data = line.split(",");
                pokemon.setName(data[0].trim());
                pokemon.setType(Type.valueOf(data[1].trim()));
                pokemon.setMaxHp(Integer.parseInt(data[2].trim()));
                pokemon.setCurrentHp(Integer.parseInt(data[3].trim()));
                //loop through attack and add to attackList

                if (!data[4].isEmpty()) {
                    for (String attackArray : data[4].split("#")) {
                        String[] attacksData = attackArray.split(":");
                        Attack pokeAttack = new Attack();
                        pokeAttack.setName(attacksData[0].trim());
                        pokeAttack.setType(Type.valueOf(attacksData[1].trim()));
                        pokeAttack.setBaseDamage(Integer.parseInt(attacksData[2].trim()));
                        pokeAttack.setAccuracy(Integer.parseInt(attacksData[3]));

                        attackListFromFile.add(pokeAttack);
                    }
                }
                pokemon.setAttacks(attackListFromFile);
                pokemonListFromFile.add(pokemon);

            }
            pokemanList = pokemonListFromFile;
            pokemanList.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Error: Lyckades inte att spara till fil: " + e.getMessage());
        }
    }

    public void saveToFil() {
        String pathToFile = "src/main/resources/pokemon_data.txt";
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pathToFile))) {
            for (Pokemon pokemon : pokemanList) {
                bufferedWriter.write(pokemon.getName() + "," + pokemon.getType() + "," + pokemon.getMaxHp() + "," + pokemon.getCurrentHp() + "," + pokemon.getAttacks().stream().map(Attack::toString).collect(Collectors.joining("#")));
                bufferedWriter.newLine();
            }
            System.out.println("Success! Pokeman data skrivs till fil.");

        } catch (IOException e) {
            System.out.println("Error: Lyckades inte att spara till fil: " + e.getMessage());
        }
    }

    public void editAttack(Pokemon pokemon) {
        System.out.println("Välja 'l' om du vill lägga attack eller 'b' för att ta bort ett attack från Pokemon");
        String val = scan.nextLine();
        //Check for valid väl
        if (!val.equalsIgnoreCase("l")) {
            if (!val.equalsIgnoreCase("b")) {
                System.out.println("Error: Du har angett ogiltigt väl. Ange giltig väl: ['l' eller 'b']");
                val = scan.nextLine();
            }
        }
        if (val.equalsIgnoreCase("l")) {
            Attack attack = new Attack();
            System.out.println("Ange Attack namn");
            String attackName = scan.nextLine();
            attack.setName(attackName);

            System.out.println("Ange Attack Type:[Ange 'g' för Grass,'e' för Electric, 'f' för Fire, 'n' för Normal]");
            String attackType = scan.nextLine();
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
                        System.out.println("Error! Ogiltigt Attack Typ, Ange igen!");
                        attackType = scan.nextLine();
                        continue;
                }
                break;
            }
            System.out.println("Ange Attack base damage");
            int baseDamage = scan.nextInt();
            while (baseDamage < 0 || baseDamage > 10000) {
                System.out.println("Error: Ogiltigt värde för Base Damage, Ange mellan 1 och 10,000");
                baseDamage = scan.nextInt();
            }
            attack.setBaseDamage(baseDamage);

            System.out.println("Ange Attack accuracy");
            int accuracy = scan.nextInt();
            while (accuracy < 0 || accuracy > 10000) {
                System.out.println("Error: Ogiltigt värde för Accuracy, Ange mellan 1 och 10,000");
                accuracy = scan.nextInt();
            }
            attack.setAccuracy(accuracy);


            pokemon.getAttacks().add(attack);
            scan.nextLine();
            System.out.println("Success! Ny Attacken |" + attack + "| har lägt till " + pokemon.getName());
            consoleMenu();

        }
        if (val.equalsIgnoreCase("b")) {
            List<Attack> pokemanAttacks = pokemon.getAttacks();
            if (pokemanAttacks != null) {
                System.out.println("Vill du ta bort hela Attack List eller en stycken? välj 'h' för hela listan och 'e' för en stycken");
                String choice = scan.nextLine();
                if (choice.equalsIgnoreCase("h")) {
                    pokemon.getAttacks().clear();
                    System.out.println("Success! Alla attacker för " + pokemon.getName() + " har tagits bort!");
                    consoleMenu();
                }
                if (choice.equalsIgnoreCase("e")) {
                    System.out.println("Vilken attack vill du ta bort från " + pokemon.getName() + " ?, ange attack namnet");
                    String attackNamn = scan.nextLine();
                    for (Attack attack : pokemanAttacks) {
                        if (attack.getName().equalsIgnoreCase(attackNamn)) {
                            pokemanAttacks.remove(attack);
                            System.out.println("Success: Attack *" + attackNamn + "* har tagits bort från Pokemon "+pokemon.getName());
                            consoleMenu();
                            break;
                        }
                    }
                }
            }

        }



    }

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

    Pokemon pokemon_one = new Pokemon("poke_1", Type.Electric, 1000, 500, list_one);
    Pokemon pokemon_two = new Pokemon("poke_2", Type.Fire, 2000, 600, list_two);
    Pokemon pokemon_three = new Pokemon("poke_3", Type.Grass, 3000, 700, list_three);
    Pokemon pokemon_four = new Pokemon("poke_4", Type.Normal, 4000, 800, list_one);
    Pokemon pokemon_five = new Pokemon("poke_5", Type.Grass, 5000, 900, list_two);
    Pokemon pokemon_six = new Pokemon("poke_6", Type.Electric, 6000, 1000, list_three);

    pokemanList.addAll(Arrays.asList(pokemon_one, pokemon_two, pokemon_three, pokemon_four, pokemon_five, pokemon_six));

}

public void showPredifined() {
    System.out.println("Pokemon Data:\n" + pokemanList.toString());
}

public void createPokemon() {
    ArrayList<Attack> attackList = new ArrayList<>();

    System.out.println("Ange Pokemon namn");
    String name = scan.nextLine();
    for (Pokemon pokemon : pokemanList) {
        //Validera giltig pokemon namn
        while (name.isEmpty() || name.length() > 30) {
            System.out.println("Error! Pokemon namn är ogilitg. Det kan inte vara töm eller länger än 50 chars. Ange gitltig namn:");
            name = scan.nextLine();
        }
        //Validera om pokeman med samma namn redan finns
        if (pokemon.getName().equals(name)) {
            System.out.println("Info! Pokemon data redan finns!");
            consoleMenu();
        } else {
            Pokemon newPokemon = new Pokemon();
            newPokemon.setName(name);

            System.out.println("Ange Type:[Ange 'g' för Grass,'e' för Electric, 'f' för Fire, 'n' för Normal]");
            String pokemanType = scan.nextLine();

            switch (pokemanType.toLowerCase()) {
                case "g":
                    newPokemon.setType(Type.Grass);
                    break;
                case "e":
                    newPokemon.setType(Type.Electric);
                    break;
                case "f":
                    newPokemon.setType(Type.Fire);
                    break;
                case "n":
                    newPokemon.setType(Type.Normal);
                    break;
                default:
                    System.out.println("Error: Ogiltigt Pokemon Type, Try again!");
                    consoleMenu();
            }
            scan.nextLine();
            System.out.println("Ange maxHP");
            int maxHp = scan.nextInt();
            while (maxHp < 0 || maxHp > 10000) {
                System.out.println("Error: Ogiltigt värde för Max HP, Ange mellan 1 och 10,000");
                maxHp = scan.nextInt();
            }
            newPokemon.setMaxHp(maxHp);

            System.out.println("Ange currentHP");
            int currentHp = scan.nextInt();
            while (currentHp < 0 || currentHp > 10000) {
                System.out.println("Error: Ogiltigt värde för Current HP, Ange mellan 1 och 10,000");
                currentHp = scan.nextInt();
            }
            newPokemon.setCurrentHp(currentHp);

            System.out.println("Hur många attacker har den ny Pokemonen?");

            int antalAttacker = scan.nextInt();
            scan.nextLine();
            for (int i = 1; i <= antalAttacker; i++) {
                System.out.println("Skapa attack nummer: " + i);
                Attack attack = new Attack();

                System.out.println("Ange Attack namn");
                String attackName = scan.nextLine();
                attack.setName(attackName);

                System.out.println("Ange Attack Type:[Ange 'g' för Grass,'e' för Electric, 'f' för Fire, 'n' för Normal]");
                String attackType = scan.nextLine();
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
                System.out.println("Ange Attack base damage");
                int baseDamage = scan.nextInt();
                while (baseDamage < 0 || baseDamage > 10000) {
                    System.out.println("Error: ogiltigt värde för Base Damage, Ange mellan 1 och 10,000");
                    baseDamage = scan.nextInt();
                }
                attack.setBaseDamage(baseDamage);

                System.out.println("Ange Attack accuracy");
                int accuracy = scan.nextInt();
                while (accuracy < 0 || accuracy > 10000) {
                    System.out.println("Error: Ogiltigt värde för Accuracy, Ange mellan 1 och 10,000");
                    accuracy = scan.nextInt();
                }
                attack.setAccuracy(accuracy);

                attackList.add(attack);
                scan.nextLine();

            }
            newPokemon.setAttacks(attackList);
            pokemanList.add(newPokemon);
            System.out.println("Success! Pokemon har läggts till Predifinierad pokemoner! Om du vill spara den till fil, använd väl #5 från meny. **");
            consoleMenu();
        }
    }

}

public void editPokemon() {
    System.out.println("Ange pokemon namn att Radera");
    String pokemonNamn = scan.nextLine();
    for (Pokemon pokemon : pokemanList) {
        if (pokemon.getName().equalsIgnoreCase(pokemonNamn)) {
            System.out.println("Pokemon info:\n" + pokemon);
            System.out.println("Vilken fält vill du ändra? Ange 'n' för namn, 't' för Type, 'm' för Max HP, 'c' för Current HP. För att radera Attack, Tryck 'a'.");
            String val = scan.nextLine();
            while (val != null) {
                if (val.equalsIgnoreCase("n")) {
                    System.out.println("Ange ny namn");
                    String namn = scan.nextLine();
                    pokemon.setName(namn);
                    System.out.println("Success! Pokemon namn är ändrat till " + namn);
                    consoleMenu();
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
                    System.out.println("Ange ny maximalt HP");
                    int max = scan.nextInt();
                    while (max < 0 || max > 10000) {
                        System.out.println("Error: Ogiltigt värde för Max HP, Ange mellan 1 och 10,000");
                        max = scan.nextInt();
                    }
                    pokemon.setMaxHp(max);
                    System.out.println("Success! Pokemon maximalt HP är ändrat till " + max);
                    consoleMenu();
                } else if (val.equalsIgnoreCase("c")) {
                    System.out.println("Ange ny current HP");
                    int current = scan.nextInt();
                    while (current < 0 || current > 10000) {
                        System.out.println("Error: Ogiltigt värde för Current HP, Ange mellan 1 och 10,000");
                        current = scan.nextInt();
                    }
                    pokemon.setCurrentHp(current);
                    System.out.println("Success! Pokemon current HP är ändrat till " + current);
                    consoleMenu();
                } else if (val.equalsIgnoreCase("a")) {
                    editAttack(pokemon);
                } else {
                    System.out.println("Error: Ogilitigt val ändring av Pokemon, ange valid val igen! ['n' för namn , 't' för Type, 'm' för Max HP, 'c' för Current HP,'a' för Attack]");
                    val = scan.nextLine();
                }
            }
        }

    }

    System.out.println("Error: Pokemonen finns inte i listan");
    consoleMenu();


}


public void removePokemon() {
    System.out.println("Ange pokemon namn att ta bort");
    String pokemonNamn = scan.nextLine();
    for (Pokemon pokemon : pokemanList) {
        if (pokemon.getName().equalsIgnoreCase(pokemonNamn)) {
            System.out.println("Pokemon info:\n" + pokemon);
            pokemanList.remove(pokemon);
            System.out.println("** Pokemon " + pokemonNamn + " har tagits bort från listan! **");
            consoleMenu();
        }

    }
    System.out.println("Error: Pokemonen finns inte i listan");
    consoleMenu();

}

public void resetToSeededData() {
    predefinedPokemon();
    System.out.println("Info!: Pokemon data har återställt till seed data");
}

}
