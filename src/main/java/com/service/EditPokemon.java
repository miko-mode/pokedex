package com.service;

import com.models.Type;
import com.exceptions.*;
import com.models.Attack;
import com.models.Pokemon;

import java.util.List;
import java.util.NoSuchElementException;

import static com.ui.Main.consoleMenu;
import static com.utility.ValidationInputHelper.attackTypeValidation;

public class EditPokemon extends PokemonOperations {

    public void editPokemon() {
        String maxHp = null;
        String currentHp;
        boolean isValidMaxHp = false;
        boolean isValidCurrentHp = false;
        boolean isValidPokemonName = false;
        System.out.println("Ange pokemon namn att redigera");
        String pokemonNamn = scan.nextLine();
        for (Pokemon pokemon : pokemonList) {
            if (pokemon.getName().equalsIgnoreCase(pokemonNamn)) {
                System.out.println("Pokemon info:\n" + pokemon);
                System.out.println("Vilken fält vill du ändra? Ange 'n' för namn, 't' för Type, 'm' för Max HP, 'c' för Current HP. För att redigera(lägg tiill/ta bort) Attack, Tryck 'a'.");
                String val = scan.nextLine();
                while (val != null) {
                    if (val.equalsIgnoreCase("n")) {
                        System.out.println("Ange ny pokemon namn");

                        while (!isValidPokemonName) {
                            try {
                                String pokemonName = scan.nextLine();
                                String finalPokemonName = pokemonName;

                                if ((pokemonName.length() < 2 || pokemonName.length() > 20)) {
                                    throw new InvalidPokemonNameFormat("Pokemon namn storlek är för lite.");
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
                                System.out.println("Error: Pokemonen namn storlek borde vara mellan 2 och 20.");
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
                                assert maxHp != null;
                                if (Integer.parseInt(currentHp) > pokemon.getMaxHp()) {
                                    throw new CurrentHpGreaterThanMaxHpException("Högre currentHp an maxHp");
                                }
                                if (Integer.parseInt(currentHp) >= 1 && Integer.parseInt(currentHp) <= 1000) {
                                    pokemon.setCurrentHp(Integer.parseInt(currentHp));
                                    isValidCurrentHp = true;
                                    System.out.println("Success! " + pokemon.getName() + " Current HP ändrats till " + currentHp);
                                    consoleMenu();
                                }

                            } catch (CurrentHpGreaterThanMaxHpException e) {
                                System.out.println("Error: Current HP kan inte var högre än Max HP: Försök igen");
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
            }
        }
        System.out.println("Error: Pokemonen finns inte i listan");
        consoleMenu();
    }

    public void editAttack(Pokemon pokemon) throws NoSuchElementException, NullPointerException {
        String attackName;
        boolean isValidAttackEntry = false;
        boolean isValidBaseDamage = false;
        boolean isValidAttackAccuracy = false;
        boolean attackListSizeValid = false;

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
                            if (attackName.isBlank() || attackName.length() < 4 || attackName.length() > 20) {
                                throw new InvalidAttackEntryException("Attack namnet är ogiltig.");
                            }
                            if (pokemon.getAttacks().stream().anyMatch(a -> a.getName().equalsIgnoreCase(finalAttackName))) {
                                throw new DuplicateAttackEntryException("Attack namnet redan finns.");
                            }


                            System.out.println("Ange Attack Typ:[Ange 'g' för Grass,'e' för Electric, 'f' för Fire, 'n' för Normal]");
                            String attackType = scan.nextLine();
                            attackTypeValidation(attackType, attack);
                            System.out.println("Ange Basskada värde för " + attackName);
                            while (!isValidBaseDamage) {
                                try {
                                    String baseDamage = scan.nextLine().trim();
                                    if (Integer.parseInt(baseDamage) >= 1 && Integer.parseInt(baseDamage) <= 100) {
                                        attack.setBaseDamage(Integer.parseInt(baseDamage));
                                        isValidBaseDamage = true;
                                    } else if (Integer.parseInt(baseDamage) < 1 || Integer.parseInt(baseDamage) > 100) {
                                        throw new NumberNotInIntervalException("Ogiltigt värde för Basskada");
                                    } else if (Integer.parseInt(baseDamage) > Integer.MAX_VALUE || Integer.parseInt(baseDamage) < Integer.MIN_VALUE) {
                                        throw new NumberFormatException();
                                    }

                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Ogiltigt värde för Basskada, Vänliga ange bara giltig Integer värde");
                                } catch (NumberNotInIntervalException e) {
                                    System.out.println("Error: Ogiltigt värde för Basskada, Ange mellan 1 och 100");
                                }
                            }

                            System.out.println("Ange Träffsäkerhet värde för " + attackName);
                            while (!isValidAttackAccuracy) {
                                try {
                                    String attackAccuracy = scan.nextLine().trim();
                                    if (Integer.parseInt(attackAccuracy) >= 1 && Integer.parseInt(attackAccuracy) <= 100) {
                                        attack.setAccuracy(Integer.parseInt(attackAccuracy));
                                        isValidAttackAccuracy = true;
                                    } else if (Integer.parseInt(attackAccuracy) < 1 || Integer.parseInt(attackAccuracy) > 100) {
                                        throw new NumberNotInIntervalException("Ogiltigt värde för Träffsäkerhet");
                                    } else if (Integer.parseInt(attackAccuracy) > Integer.MAX_VALUE || Integer.parseInt(attackAccuracy) < Integer.MIN_VALUE) {
                                        throw new NumberFormatException();
                                    }

                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Ogiltigt värde för Träffsäkerhet, Vänliga ange bara giltig Integer värde");
                                } catch (NumberNotInIntervalException e) {
                                    System.out.println("Error: Ogiltigt värde för Träffsäkerhet, Ange mellan 1 och 100");
                                }
                            }

                            attack.setName(attackName);
                            pokemon.getAttacks().add(attack);
                            isValidAttackEntry = true;
                            System.out.println("Success! Ny Attacken |" + attackName + "| har lägt till " + pokemon.getName());
                            consoleMenu();

                        } catch (NullPointerException e) {
                            System.out.println("Attack namnet kan inte vara töm");
                        } catch (DuplicateAttackEntryException e) {
                            System.out.println("Attack namnet redan finns! Ange nytt namn");
                        }


                    }


                }
                case "b" -> {
                    List<Attack> pokemonAttacks = pokemon.getAttacks();
                    int attackCount = pokemonAttacks.size();
                    if (!pokemonAttacks.isEmpty() && attackCount > 1) {
                        while (!attackListSizeValid) {
                            try {
                                System.out.println("Vilken attack vill du ta bort från " + pokemon.getName() + " ?, ange attack namnet");
                                String attackToDelete = scan.nextLine().trim();
                                pokemonAttacks.remove(pokemonAttacks.stream()
                                        .filter(a -> a.getName().equalsIgnoreCase(attackToDelete))
                                        .findFirst()
                                        .orElseThrow(() -> new AttackNotFoundException("Attack hitta ej.")));
                                attackCount--;
                                System.out.println("Success: Attack " + attackToDelete + " har tagits bort från Pokemon " + pokemon.getName());
                                if (attackCount > 1) {
                                    System.out.println("Vill du radera andra Attack för " + pokemon.getName() + "? [j:ja, n:nej]");
                                    String inputChoice = scan.nextLine();
                                    if (inputChoice.equalsIgnoreCase("j")) {
                                        continue;
                                    } else if (inputChoice.equalsIgnoreCase("n")) {
                                        attackListSizeValid = true;
                                        consoleMenu();
                                    }
                                }
                                if (attackCount == 1) {
                                    attackListSizeValid = true;
                                    consoleMenu();
                                }
                            } catch (NullPointerException e) {
                                System.out.println("Error: Lyckades inte att ta bort attacken! Försök igen");

                            } catch (AttackNotFoundException e) {
                                System.out.println("Error: Lyckades inte att hitta Attacken! Försök igen");
                            }
                        }
                    } else {
                        System.out.println("Error: En Pokemon måste ha en attack, så det går inte att radera!");
                        consoleMenu();
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

}
