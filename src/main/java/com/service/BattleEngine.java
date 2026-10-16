package com.service;

import com.mapping.AttackTurnStats;
import com.mapping.GameStats;
import com.models.Attack;
import com.models.Pokemon;
import com.models.Type;
import com.repository.FileService;
import com.utility.ValidationInputHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.ui.Main.consoleMenu;
import static com.utility.ValidationInputHelper.isAttackPresentInPokemon;


public class BattleEngine extends PokemonOperations implements IBattleEngine {
    GameStats gameStats;
    AttackTurnStats turnStats;
    public ArrayList<AttackTurnStats> turnStatsArrayList = new ArrayList<>();
    public List<Pokemon> wildPokemonsPool = new FileService().loadWildPokemons();
    StatsticsService statsticsService = new StatsticsService();
    int turnCount = 0;

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
                while (!isAttackTurnOver) {
                    if (!userPokemon.getAttacks().isEmpty() && userPokemon.getAttacks() != null) {
                        gameStats = new GameStats();
                        turnStats = new AttackTurnStats();
                        System.out.println("\n------  -----");
                        System.out.println("Attack tur: " + (turnCount + 1));
                        System.out.println("------  -----");
                        System.out.println("Välj en Attack från " + userPokemon.getName() + " och ange attacknamnet:");
                        String attackChoice = scan.nextLine();
                        if (isAttackPresentInPokemon(attackChoice, userPokemon)) {
                            System.out.println("Du har valt:");
                            Attack selected_User_Attack = ValidationInputHelper.getAttackObjectFromString(attackChoice, userPokemon);
                            System.out.println(selected_User_Attack);
                            System.out.println("CPU väljer: ");
                            Collections.shuffle(wildPokemon.getAttacks());
                            Attack selected_CPU_Attack = ValidationInputHelper.getAttackObjectFromString(wildPokemon.getAttacks().get(0).getName(), wildPokemon);
                            System.out.println(selected_CPU_Attack);

                            gameStats.setGameId(String.valueOf(UUID.randomUUID()));
                            gameStats.setUserPokemonName(userPokemon.getName());
                            gameStats.setCpuPokemonName(wildPokemon.getName());
                            execute(userPokemon, selected_User_Attack, wildPokemon, selected_CPU_Attack);

                        }
                    }

                    isAttackTurnOver = isAttackTurnOver(userPokemon, wildPokemon, isAttackTurnOver, wildPokemonsPool);
                    isAttackTurnOver = isAttackTurnOver(wildPokemon, userPokemon, isAttackTurnOver, pokemonList);
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
            System.out.println("\n****************  **************************");
            System.out.println("Pokemon " + attacker.getName() + " har vunnit i rund " + turnCount + "!");
            System.out.println("****************  **************************");
            statsticsService.createAndSavePokemonStats(attacker, "win");
            statsticsService.createAndSavePokemonStats(defender, "loss");
            pokemonPool.remove(defender);
            gameStatsList.add(gameStats);
            isAttackTurnOver = true;
            consoleMenu();
        }
        return isAttackTurnOver;
    }

    //gör spelturen logik
    public void execute(Pokemon userPokemon, Attack userAttack, Pokemon wildPokemon, Attack cpuAttack) {
        double userAttackDamage = calculateDamage(userAttack, cpuAttack);
        double cpuAttackDamage = calculateDamage(cpuAttack, userAttack);

        if (userAttackDamage > cpuAttackDamage) {
            if (wildPokemon.getCurrentHp() > 0) {
                String attackEffectTwice = ((userAttackDamage == (2 * cpuAttack.getBaseDamage())) && (userAttackDamage > cpuAttack.getBaseDamage())) ? " [2X]" : "";
                System.out.println("| Skaderesultat för användare: " + userAttackDamage + attackEffectTwice + " | Skaderesultat för CPU: " + cpuAttackDamage);
                System.out.println("| ---------------------------------------------------------------------------");
                wildPokemon.setCurrentHp(wildPokemon.getCurrentHp() - 100);
                System.out.println("| " + userPokemon.getName() + " current HP: " + userPokemon.getCurrentHp() + " | " + wildPokemon.getName() + " current HP: " + wildPokemon.getCurrentHp() + " [minskad med 100]");

            }
        }
        if (cpuAttackDamage > userAttackDamage) {
            if (userPokemon.getCurrentHp() > 0) {
                String attackEffectTwice = ((cpuAttackDamage == 2 * (userAttack.getBaseDamage())) && (cpuAttackDamage > userAttack.getBaseDamage())) ? " [2X]" : "";
                System.out.println("| Skaderesultat för användare: " + userAttackDamage + " | Skaderesultat för CPU: " + cpuAttackDamage + attackEffectTwice);
                System.out.println("| ---------------------------------------------------------------------------");
                userPokemon.setCurrentHp(userPokemon.getCurrentHp() - 100);
                System.out.println("| " + userPokemon.getName() + " current HP: " + userPokemon.getCurrentHp() + " [minskad med 100] | " + wildPokemon.getName() + " current HP: " + wildPokemon.getCurrentHp());

            }

        }
        //träffsäkerhet avgörs om cpuAttackDamage är lika med userAttackDamage
        if (cpuAttackDamage == userAttackDamage) {
            if (userAttack.getAccuracy() > cpuAttack.getAccuracy()) {
                System.out.println("| Skaderesultat för användare: " + userAttackDamage + " | Skaderesultat för CPU: " + cpuAttackDamage);
                System.out.println("| ------------------------------------------------------------------------");
                wildPokemon.setCurrentHp(wildPokemon.getCurrentHp() - 100);
                System.out.println(userPokemon.getName() + " current HP: " + userPokemon.getCurrentHp() + " | " + wildPokemon.getName() + " current HP: " + wildPokemon.getCurrentHp() + " [minskad med 100]");
            }
            if (userAttack.getAccuracy() < cpuAttack.getAccuracy()) {
                System.out.println("| Skaderesultat resultat för användare: " + userAttackDamage + "| Skaderesultat för CPU: " + cpuAttackDamage);
                System.out.println("| ---------------------------------------------------------------------------");
                userPokemon.setCurrentHp(userPokemon.getCurrentHp() - 100);
                System.out.println(userPokemon.getName() + " current HP: " + userPokemon.getCurrentHp() + "[minskad med 100] | " + wildPokemon.getName() + " current HP: " + wildPokemon.getCurrentHp());

            }
        }
        statGenerator(userAttack.getName(), cpuAttack.getName(), userPokemon, wildPokemon, userAttackDamage, cpuAttackDamage, turnCount + 1);
        turnCount++;

    }

    //generera stat för turen
    public GameStats statGenerator(String userAttackName, String cpuAttackName, Pokemon userPokemon, Pokemon wildPokemon, double userAttackDamage, double cpuAttackDamage, int turnNumber) {
        turnStats.setAttackTurnNumber(turnNumber);
        turnStats.setUserAttackName(userAttackName);
        turnStats.setCpuAttackName(cpuAttackName);
        turnStats.setUserDamagePoints(String.valueOf(userAttackDamage));
        turnStats.setCpuDamagePoints(String.valueOf(cpuAttackDamage));
        turnStats.setUserCurrentHp(String.valueOf(userPokemon.getCurrentHp()));
        turnStats.setWildCurrentPoints(String.valueOf(wildPokemon.getCurrentHp()));
        turnStatsArrayList.add(turnStats);
        //sätta tur vinnare
        if (userAttackDamage > cpuAttackDamage) {
            turnStats.setTurnWinner("USER");
        } else if (userAttackDamage < cpuAttackDamage) {
            turnStats.setTurnWinner("CPU");
        } else {
            turnStats.setTurnWinner("X");
        }
        gameStats.setTurnStats(turnStatsArrayList);
        return gameStats;

    }

    //beråknar skada
    public double calculateDamage(Attack attack1, Attack attack2) {
        if (attack1.getType().equals(Type.Fire) && attack2.getType().equals(Type.Grass)) {
            return damagePoint_2 * attack2.getBaseDamage();
        }
        if (attack1.getType().equals(Type.Water) && attack2.getType().equals(Type.Fire)) {
            return damagePoint_2 * attack2.getBaseDamage();
        }
        if (attack1.getType().equals(Type.Grass) && attack2.getType().equals(Type.Water)) {
            return damagePoint_2 * attack2.getBaseDamage();
        }
        if (attack1.getType().equals(Type.Electric) && attack2.getType().equals(Type.Water)) {
            return damagePoint_2 * attack2.getBaseDamage();
        }
        //0,5
        if (attack1.getType().equals(Type.Fire) && attack2.getType().equals(Type.Water)) {
            return damagePoint_1 * attack2.getBaseDamage();
        }
        if (attack1.getType().equals(Type.Water) && attack2.getType().equals(Type.Grass)) {
            return damagePoint_1 * attack2.getBaseDamage();
        }
        if (attack1.getType().equals(Type.Grass) && attack2.getType().equals(Type.Fire)) {
            return damagePoint_1 * attack2.getBaseDamage();
        }
        //normal(1x)
        if (attack1.getType().equals(Type.Normal)) {
            return attack2.getBaseDamage();
        }
        return attack2.getBaseDamage();
    }

    //blandar ihop alla wild pokemoner och plockar pokemonen som finns i toppen
    private Pokemon cpuPokemonChoice() {
        FileService fileService = new FileService();
        wildPokemonsPool = fileService.loadWildPokemons();
        Collections.shuffle(wildPokemonsPool);
        return wildPokemonsPool.get(0);
    }
}
