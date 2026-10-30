package com.service;

import com.mapping.GameStats;
import com.models.Attack;
import com.models.Pokemon;
import com.models.Type;
import com.repository.FileService;
import com.utility.ValidationInputHelper;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.ui.Main.consoleMenu;
import static com.utility.ValidationInputHelper.isAttackPresentInPokemon;


public class BattleEngine extends PokemonOperations implements IBattleEngine {
    GameStats gameStats;
    public List<Pokemon> wildPokemonsPool = new FileService().loadWildPokemons();
    StatsticsService statsticsService = new StatsticsService();
    Random random = new Random();
    int randomNumber = random.nextInt(16);
    static int turnCount = 0;
    boolean isCriticalHit = false;
    boolean isForOneTurnOnly = true;

    public void battleEngine() {
        boolean isFound = false;
        Pokemon userPokemon = null;
        Pokemon wildPokemon;

        System.out.println("Välj en Pokemon namn från samling");
        String userChoice = scan.nextLine();
        for (Pokemon pokemon : pokemonList) {
            if (pokemon.getName().equalsIgnoreCase(userChoice)) {
                isFound = true;
                userPokemon = pokemon;
                break;
            }
        }
        if (isFound) {
            System.out.println("Pokemon info: ");
            System.out.println(userPokemon);
            System.out.println("CPU väljer: ");
            wildPokemon = cpuPokemonChoice();
            System.out.println(wildPokemon);

            if (isValidToStartTurn(userPokemon, wildPokemon)) {
                boolean isAttackTurnOver = false;
                boolean currentHPZeroAndBelow = false;
                while (!isAttackTurnOver) {
                    Attack selected_User_Attack = null;
                    Attack selected_CPU_Attack = null;
                    if (!userPokemon.getAttacks().isEmpty() && userPokemon.getAttacks() != null) {
                        gameStats = new GameStats();
                        System.out.println("Välj en Attack från " + userPokemon.getName() + " och ange attacknamnet:");
                        String attackChoice = scan.nextLine();
                        if (isAttackPresentInPokemon(attackChoice, userPokemon)) {
                            System.out.println("Du har valt:");
                            selected_User_Attack = ValidationInputHelper.getAttackObjectFromString(attackChoice, userPokemon);
                            System.out.println(selected_User_Attack);
                            System.out.println("CPU väljer: ");
                            Collections.shuffle(wildPokemon.getAttacks());
                            selected_CPU_Attack = ValidationInputHelper.getAttackObjectFromString(wildPokemon.getAttacks().get(0).getName(), wildPokemon);
                            System.out.println(selected_CPU_Attack);

                            gameStats.setGameId(String.valueOf(UUID.randomUUID()));
                            gameStats.setUserPokemonName(userPokemon.getName());
                            gameStats.setCpuPokemonName(wildPokemon.getName());


                        }
                    }
                    while (!currentHPZeroAndBelow) {
                        if (userPokemon.getCurrentHp() > 0) {
                            executeUserAttack(selected_User_Attack, wildPokemon);
                            isAttackTurnOver = isAttackTurnOver(userPokemon, wildPokemon, isAttackTurnOver, wildPokemonsPool);
                        }
                        if (wildPokemon.getCurrentHp() > 0) {
                            executeCpuAttack(selected_CPU_Attack, userPokemon);
                            isAttackTurnOver = isAttackTurnOver(wildPokemon, userPokemon, isAttackTurnOver, pokemonList);
                        }
                        currentHPZeroAndBelow = isAttackTurnOver;
                    }
                }

            }
            if (!isValidToStartTurn(userPokemon, wildPokemon)) {
                System.out.println("Error: Det går inte att starta strid eftersom det finns ogiltig Current HP!");
                consoleMenu();
            }
        }
        if (!isFound) {
            System.out.println("Error: Pokemonen finns inte i samlingen. Detta kan beror på att den är besegrad i tidigare tur, Forsök igen");
            consoleMenu();
        }
    }

    private boolean isValidToStartTurn(Pokemon userPokemon, Pokemon wildPokemon) {
        return userPokemon.getCurrentHp() > 0 && wildPokemon.getCurrentHp() > 0;
    }

    private boolean isAttackTurnOver(Pokemon attacker, Pokemon defender, boolean isAttackTurnOver, List<Pokemon> pokemonPool) {
        if (defender.getCurrentHp() <= 0 && attacker.getCurrentHp() > 0) {
            gameStats.setWinnerPokemon(attacker.getName());
            gameStats.setDefeatedPokemon(defender.getName());
            System.out.println("\n****************  **************************");
            System.out.println("Pokemon " + attacker.getName() + " har vunnit i rund " + turnCount + "!");
            System.out.println("****************  **************************");
            statsticsService.createAndSavePokemonStats(attacker, "win");
            statsticsService.createAndSavePokemonStats(defender, "loss");
            statGenerator(attacker, defender, turnCount);
            pokemonPool.remove(defender);
            gameStatsList.add(gameStats);
            FileService.saveToBattleResultFile();
            isAttackTurnOver = true;
            turnCount = 0;
            consoleMenu();
        }
        return isAttackTurnOver;
    }

    //gör spelturen logik
    public void executeUserAttack(Attack userAttack, Pokemon wildPokemon) {
        double userAttackDamage = Math.round((calculateDamage(userAttack, wildPokemon) * 100.0) / 100.0);
        System.out.println("\nAnvändare tur:");
        System.out.println("--------------");
        if (wildPokemon.getCurrentHp() > 0) {
            String criticalHit = isCriticalHit ? " [2X]" : "";
            System.out.println("| Skaderesultat: " + userAttackDamage + criticalHit);
            System.out.println("| --------------------------------------------");
            int currentHpTurnDeduction = (int) (wildPokemon.getCurrentHp() - userAttackDamage);
            wildPokemon.setCurrentHp(currentHpTurnDeduction);
            System.out.println("|" + wildPokemon.getName() + " current HP: " + wildPokemon.getCurrentHp() + " [minskad med " + userAttackDamage + "]\n");
            isCriticalHit=false; //reset
        }
        turnCount++;
    }

    public void executeCpuAttack(Attack cpuAttack, Pokemon userPokemon) {
        double cpuAttackDamage = Math.round((calculateDamage(cpuAttack, userPokemon) * 100.0) / 100.0);
        System.out.println("\nCPU tur:");
        System.out.println("--------------");
        if (userPokemon.getCurrentHp() > 0) {
            String criticalHit = isCriticalHit ? " [2X]" : "";
            System.out.println("| Skaderesultat: " + cpuAttackDamage + criticalHit);
            System.out.println("| --------------------------------------------");
            int currentHpTurnDeduction = (int) (userPokemon.getCurrentHp() - cpuAttackDamage);
            userPokemon.setCurrentHp(currentHpTurnDeduction);
            System.out.println("|" + userPokemon.getName() + " current HP: " + userPokemon.getCurrentHp() + " [minskad med " + cpuAttackDamage + "]");
            isCriticalHit=false; //reset
        }
        turnCount++;
    }

    //generera stat för turen
    public void statGenerator(Pokemon attacker, Pokemon defender, int totalNumberOfTurns) {
        gameStats.setNumberOfTurns(turnCount);
        //sätta tur vinnare
        if (attacker.getCurrentHp() > defender.getCurrentHp()) {
            gameStats.setWinnerPokemon("USER");
            gameStats.setDefeatedPokemon("CPU");
        } else if (attacker.getCurrentHp() < defender.getCurrentHp()) {
            gameStats.setWinnerPokemon("CPU");
            gameStats.setDefeatedPokemon("USER");
        } else {
            gameStats.setWinnerPokemon("X");
            gameStats.setDefeatedPokemon("X");
        }
    }

    //blandar ihop alla wild pokemoner och plockar pokemonen som finns i toppen
    private Pokemon cpuPokemonChoice() {
        FileService fileService = new FileService();
        wildPokemonsPool = fileService.loadWildPokemons();
        Collections.shuffle(wildPokemonsPool);
        return wildPokemonsPool.get(0);
    }

    public double calculateTypeEfficiency(Type attackerType, Type defenderType) {
        if (attackerType == Type.Fire && defenderType == Type.Grass) {
            return 2.0;
        }
        if (attackerType == Type.Water && defenderType == Type.Fire) {
            return 2.0;
        }
        if (attackerType == Type.Grass && defenderType == Type.Water) {
            return 2.0;
        }
        if (attackerType == Type.Electric && defenderType == Type.Water) {
            return 2.0;
        }
        if (attackerType == Type.Fire && defenderType == Type.Water) {
            return 0.5;
        }
        if (attackerType == Type.Water && defenderType == Type.Grass) {
            return 0.5;
        }
        if (attackerType == Type.Grass && defenderType == Type.Fire) {
            return 0.5;
        }

        return 1.0; // Standard om ingen särskild effekt finns
    }

    //skadeberäkning metod
    public double calculateDamage(Attack attackerAttack, Pokemon defender) {
        // 1. Hämta typeffektiviteten
        double typeEfficiency = calculateTypeEfficiency(attackerAttack.getType(), defender.getType());
        double randomFactor = 0.85 + (Math.random() * 0.30); // Slump mellan 0.85 och 1.15
        double damage = attackerAttack.getBaseDamage() * typeEfficiency * randomFactor;
        if ((randomNumber <= turnCount) && isForOneTurnOnly) {
            isCriticalHit = true;
            isForOneTurnOnly = false;
            return damage * 2;
        }
        return damage;
    }
}
