package com.service;

import com.exceptions.*;
import com.models.Attack;
import com.models.Pokemon;
import com.utility.ValidationInputHelper;

import java.util.ArrayList;

import static com.ui.Main.consoleMenu;

public class CreatePokemon extends PokemonOperations {
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

    public void createPokemon() throws DuplicatePokemonException {
        ArrayList<Attack> attackList = new ArrayList<>();
        Pokemon pokemon = new Pokemon();
        System.out.println("Ange Pokemon namn");
        while (!isValidPokemonName) {
            try {
                pokemonName = scan.nextLine();
                String finalPokemonName = pokemonName;

                if ((pokemonName.length() < 2 || pokemonName.length() > 20)) {
                    throw new InvalidPokemonNameFormat("Pokemon namn storlek för lite.");
                }

                if (pokemonList.stream().anyMatch(p -> p.getName().equalsIgnoreCase(finalPokemonName))) {
                    throw new DuplicatePokemonException("Pokemon redan finns");
                }
                if (!pokemonName.isBlank() && (pokemonName.length() >= 2 && pokemonName.length() <= 20)) {
                    pokemon.setName(pokemonName);
                    isValidPokemonName = true;
                }

            } catch (InvalidPokemonNameFormat e) {
                System.out.println("Error: Pokemonen namn storlek borde vara mellan 2 och 20.");
            } catch (DuplicatePokemonException e) {
                System.out.println("Error: Pokemonen redan finns! Ange ett ny namn");
            }
        }

        System.out.println("Ange Type:[Ange 'g' för Grass,'e' för Electric, 'f' för Fire, 'n' för Normal]");
        String pokemanAttackType = scan.nextLine();
        ValidationInputHelper.pokemanAttackTypeValidation(pokemanAttackType, pokemon);
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
                if (Integer.parseInt(currentHp) < 1 || Integer.parseInt(currentHp) > 1000) {
                    throw new NumberNotInIntervalException("Ogiltigt värde för Current HP");
                }
                if (Integer.parseInt(currentHp) > Integer.MAX_VALUE || Integer.parseInt(currentHp) < Integer.MIN_VALUE) {
                    throw new NumberFormatException();
                }
                if (Integer.parseInt(currentHp) > Integer.parseInt(maxHp)) {
                    throw new CurrentHpGreaterThanMaxHpException("Current HP högre än Max HP.");
                }
                if (Integer.parseInt(currentHp) >= 1 && Integer.parseInt(currentHp) <= 1000) {
                    pokemon.setCurrentHp(Integer.parseInt(currentHp));
                    isValidCurrentHp = true;
                }

            } catch (CurrentHpGreaterThanMaxHpException e) {
                System.out.println("Error: Current HP kan inte vara högre än Max HP. Försök igen");
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
        validateAndCreateAttack(pokemon,Integer.parseInt(numberOfAttacks),attackList);
        pokemonList.add(pokemon);
        System.out.println("Success! Pokemon " + pokemon.getName() + " skapades och lagt till Predifinierad pokemoner!");
        consoleMenu();
    }

    public void validateAndCreateAttack(Pokemon pokemon, int numberOfAttacks,ArrayList<Attack> attackList){
        for (int i = 1; i <= numberOfAttacks; i++) {
            System.out.println("Skapa attack nummer: " + i);
            boolean isValidAttackEntry = false;
            Attack attack = new Attack();
            System.out.println("Ange Attack Namn");
            while (!isValidAttackEntry) {
                try {
                    attackName = scan.nextLine();
                    String finalAttackName = attackName;
                    if (attackName.isBlank() || attackName.length() < 2 || attackName.length() > 20) {
                        throw new InvalidAttackEntryException("Attack namnet är ogiltig.");
                    }
                    if (pokemon.getAttacks().stream().anyMatch(a -> a.getName().equalsIgnoreCase(finalAttackName))) {
                        throw new DuplicateAttackEntryException("Attack namnet redan finns.");
                    }

                    System.out.println("Ange Attack Type:[Ange 'g' för Grass,'e' för Electric, 'f' för Fire, 'n' för Normal]");
                    String attackType = scan.nextLine();
                    ValidationInputHelper.attackTypeValidation(attackType, attack);

                    System.out.println("Ange Basskada värde för " + attackName);
                    while (!isValidBaseDamage) {
                        try {
                            baseDamage = scan.nextLine().trim();
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
                            attackAccuracy = scan.nextLine().trim();
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
                    isValidAttackEntry = true;

                } catch (InvalidAttackEntryException | NullPointerException e) {
                    System.out.println("Attack namnet kan inte vara töm eller mindre än 2 chars och längre än 20 chars.");
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
    }
}
