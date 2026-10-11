package com.service;

import com.mapping.AttackTurnStats;
import com.mapping.PokemonStats;
import com.models.Attack;
import com.models.Pokemon;
import com.models.Type;
import com.repository.FileService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ui.Main.consoleMenu;
import static com.utility.ValidationInputHelper.getAttackObjectFromString;
import static com.utility.ValidationInputHelper.isAttackPresentInPokemon;


public class BattleEngine extends PokemonOperations {


    PokemonStats pokemonStats = new PokemonStats();
    AttackTurnStats turnStats;
    ArrayList<AttackTurnStats> turnStatsArrayList = new ArrayList<>();
    int turnCount = 0;

    public void battleEngine() {
        boolean isFound = false;
        Pokemon userPokemon = null;
        Pokemon wildPokemon;
        boolean isAttackTurnOver = false;


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
            wildPokemon = cpuChoice();
            System.out.println(wildPokemon);

            while (!isAttackTurnOver) {
                if (!userPokemon.getAttacks().isEmpty()) {
                    turnStats = new AttackTurnStats();
                    System.out.println("\n------  -----");
                    System.out.println("Attack tur: " + (turnCount + 1));
                    System.out.println("------  -----");

                    System.out.println("Välj en Attack från " + userPokemon.getName() + " och ange attacknamnet:");
                    String attackChoice = scan.nextLine();

                    if (isAttackPresentInPokemon(attackChoice, userPokemon)) {
                        System.out.println("Du har valt:");
                        Attack selected_User_Attack = getAttackObjectFromString(attackChoice, userPokemon);

                        System.out.println(selected_User_Attack);
                        System.out.println("CPU väljer: ");
                        Collections.shuffle(wildPokemon.getAttacks());
                        Attack selected_CPU_Attack = getAttackObjectFromString(wildPokemon.getAttacks().get(0).getName(), wildPokemon);

                        System.out.println(selected_CPU_Attack);
                        pokemonStats.setUserPokemonName(userPokemon.getName());
                        pokemonStats.setCpuPokemonName(wildPokemon.getName());
                        execute(userPokemon, selected_User_Attack, wildPokemon, selected_CPU_Attack);
                        //sätta match vinnare
                        if(userPokemon.getCurrentHp()<=0&&wildPokemon.getCurrentHp()>0){
                            pokemonStats.setWinnerPokemon(userPokemon.getName());
                        }
                        if(userPokemon.getCurrentHp()>0&&wildPokemon.getCurrentHp()<=0){
                            pokemonStats.setWinnerPokemon(wildPokemon.getName());
                        }

                    }

                }

                if (wildPokemon.getCurrentHp() <= 0 && userPokemon.getCurrentHp() > 0) {
                    System.out.println("\n****************  **************************");
                    System.out.println("Pokemon " + userPokemon.getName() + " har vunnit i rund " + turnCount + "!");
                    System.out.println("****************  **************************");

                    new FileService().saveToBattleResultFile(pokemonStats);
                    isAttackTurnOver = true;
                    consoleMenu();

                }
                if (userPokemon.getCurrentHp() <= 0 && wildPokemon.getCurrentHp() > 0) {
                    System.out.println("\n****************  **************************");
                    System.out.println("Pokemon " + wildPokemon.getName() + " har vunnit i rund " + turnCount + "!");
                    System.out.println("****************  **************************");
                    new FileService().saveToBattleResultFile(pokemonStats);
                    isAttackTurnOver = true;
                    consoleMenu();

                }

            }

        }
        if (!isFound) {
            System.out.println("Error: Pokemonen finns inte i samlingen, Forsök igen");
            consoleMenu();
        }


    }

    private void execute(Pokemon userPokemon, Attack userAttack, Pokemon wildPokemon, Attack cpuAttack) {
        double userAttackDamage = calculateDamage(userAttack, cpuAttack);
        double cpuAttackDamage = calculateDamage(cpuAttack, userAttack);

        if (userAttackDamage > cpuAttackDamage) {
            if (wildPokemon.getCurrentHp() > 0) {
                System.out.println("| Skaderesultat för användare: " + userAttackDamage + " | Skaderesultat för CPU: " + cpuAttackDamage);
                System.out.println("| ------------------------------------------------------------------------");
                wildPokemon.setCurrentHp(wildPokemon.getCurrentHp() - 100);
                System.out.println("| " + userPokemon.getName() + " current HP: " + userPokemon.getCurrentHp() + " | " + wildPokemon.getName() + " current HP: " + wildPokemon.getCurrentHp() + " (minskad med 100)");

            }
        }
        if (cpuAttackDamage > userAttackDamage) {
            if (userPokemon.getCurrentHp() > 0) {
                System.out.println("| Skaderesultat för användare: " + userAttackDamage + " | Skaderesultat för CPU: " + cpuAttackDamage);
                System.out.println("| ------------------------------------------------------------------------");
                userPokemon.setCurrentHp(userPokemon.getCurrentHp() - 100);
                System.out.println("| " + userPokemon.getName() + " current HP: " + userPokemon.getCurrentHp() + " (minskad med 100) | " + wildPokemon.getName() + " current HP: " + wildPokemon.getCurrentHp());

            }

        }
        //träffsäkerhet avgörs
        if (cpuAttackDamage == userAttackDamage) {
            if (userAttack.getAccuracy() > cpuAttack.getAccuracy()) {
                System.out.println("| Skaderesultat för användare: " + userAttackDamage + " | Skaderesultat för CPU: " + cpuAttackDamage);
                System.out.println("| ------------------------------------------------------------------------");
                wildPokemon.setCurrentHp(wildPokemon.getCurrentHp() - 100);
                System.out.println(userPokemon.getName() + " current HP: " + userPokemon.getCurrentHp() + " | " + wildPokemon.getName() + " current HP: " + wildPokemon.getCurrentHp() + " (minskad med 100)");
            }
            if (userAttack.getAccuracy() < cpuAttack.getAccuracy()) {
                System.out.println("| Skaderesultat resultat för användare: " + userAttackDamage + "| Skaderesultat för CPU: " + cpuAttackDamage);
                System.out.println("| ---------------------------------------------------------------------------");
                userPokemon.setCurrentHp(userPokemon.getCurrentHp() - 100);
                System.out.println(userPokemon.getName() + " current HP: " + userPokemon.getCurrentHp() + "(minskad med 100) | " + wildPokemon.getName() + " current HP: " + wildPokemon.getCurrentHp());

            }
        }
        statGenerator(userAttack.getName(), cpuAttack.getName(), userPokemon, wildPokemon, userAttackDamage, cpuAttackDamage, turnCount + 1);
        turnCount++;

    }

    private double calculateDamage(Attack attack1, Attack attack2) {
        if (attack1.getType().equals(Type.Fire) && attack2.getType().equals(Type.Grass)) {
            return 2 * attack2.getBaseDamage();
        }
        if (attack1.getType().equals(Type.Water) && attack2.getType().equals(Type.Fire)) {
            return 2 * attack2.getBaseDamage();
        }
        if (attack1.getType().equals(Type.Grass) && attack2.getType().equals(Type.Water)) {
            return 2 * attack2.getBaseDamage();
        }
        if (attack1.getType().equals(Type.Electric) && attack2.getType().equals(Type.Water)) {
            return 2 * attack2.getBaseDamage();
        }
        if (attack1.getType().equals(Type.Fire) && attack2.getType().equals(Type.Water)) {
            return 0.5 * attack2.getBaseDamage();
        }
        if (attack1.getType().equals(Type.Water) && attack2.getType().equals(Type.Grass)) {
            return 0.5 * attack2.getBaseDamage();
        }
        if (attack1.getType().equals(Type.Grass) && attack2.getType().equals(Type.Fire)) {
            return 0.5 * attack2.getBaseDamage();
        }
        if (attack1.getType().equals(Type.Normal)) {
            return 2 * attack2.getBaseDamage();
        }
        return attack2.getBaseDamage();
    }

    //shuffles randomly and returns the top wild pokemon
    public Pokemon cpuChoice() {
        FileService fileService = new FileService();
        List<Pokemon> wild_Pokemons_List = fileService.loadWildPokemons();
        Collections.shuffle(wild_Pokemons_List);
        return wild_Pokemons_List.get(0);
    }

    private PokemonStats statGenerator(String userAttackName, String cpuAttackName, Pokemon userPokemon, Pokemon wildPokemon, double userAttackDamage, double cpuAttackDamage, int turnNumber) {

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
        }
        else if (userAttackDamage < cpuAttackDamage) {
            turnStats.setTurnWinner("CPU");
        }
        else{
            turnStats.setTurnWinner("X");
        }
        pokemonStats.setTurnStats(turnStatsArrayList);
        return pokemonStats;

    }

}
