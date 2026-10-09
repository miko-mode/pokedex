package com.service;

import com.models.Attack;
import com.models.Pokemon;
import com.models.Type;
import com.repository.FileService;

import java.util.Collections;
import java.util.List;

import static com.ui.Main.consoleMenu;
import static com.utility.ValidationInputHelper.getAttackObjectFromString;
import static com.utility.ValidationInputHelper.isAttackPresentInPokemon;


public class BattleEngine extends PokemonOperations {
    boolean isAttackTurnOver = false;
    int turnCount = 0;

    public void battleEngine() {
        boolean isFound = false;
        Pokemon userPokemon = null;
        Pokemon wildPokemon;
        boolean isAttackTurnOver = false;
        int turnCount = 0;

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
                    System.out.println("\n------  ---");
                    System.out.println("Attack tur: " + (turnCount + 1));
                    System.out.println("------ ---");
                    System.out.println("Välj en Attack utav " + userPokemon.getName() + " och ange attacknamnet:");
                    String attackChoice = scan.nextLine();
                    if (isAttackPresentInPokemon(attackChoice, userPokemon)) {
                        System.out.println("Du har valt:");
                        Attack choosen_User_Attack = getAttackObjectFromString(attackChoice, userPokemon);
                        System.out.println(choosen_User_Attack);
                        System.out.println("CPU väljer: ");
                        Collections.shuffle(wildPokemon.getAttacks());
                        Attack choosen_CPU_Attack = getAttackObjectFromString(wildPokemon.getAttacks().get(0).getName(), wildPokemon);
                        System.out.println(choosen_CPU_Attack);
                        execute(userPokemon, choosen_User_Attack, wildPokemon, choosen_CPU_Attack);
                        turnCount++;

                    }

                }
            }
            if (!isFound) {
                System.out.println("Error: Pokemonen finns inte i samlingen");
                consoleMenu();
            }
        }


    }

    private void execute(Pokemon userPokemon, Attack choosenUserAttack, Pokemon wildPokemon, Attack choosenCpuAttack) {
        double userAttackDamage = calculateDamage(choosenUserAttack, choosenCpuAttack);
        double cpuAttackDamage = calculateDamage(choosenCpuAttack, choosenUserAttack);

        if (userAttackDamage > cpuAttackDamage) {
            if (wildPokemon.getCurrentHp() > 0) {
                System.out.println("| Skaderesultat för användare: " + userAttackDamage + " | Skaderesultat för CPU: " + cpuAttackDamage+"     |");
                System.out.println("| ---------------------------------------------------------------------");
                wildPokemon.setCurrentHp(wildPokemon.getCurrentHp() - 100);
                System.out.println("| "+userPokemon.getName() + " current HP: " + userPokemon.getCurrentHp() + " | " + wildPokemon.getName() + " current HP: " + wildPokemon.getCurrentHp() + " (minskad med 100) |");

            }
        }
        if (cpuAttackDamage > userAttackDamage) {
            if (userPokemon.getCurrentHp() > 0) {
                System.out.println("| Skaderesultat för användare: " + userAttackDamage + " | Skaderesultat för CPU: " + cpuAttackDamage+"     |");
                System.out.println("| ---------------------------------------------------------------------");
                userPokemon.setCurrentHp(userPokemon.getCurrentHp() - 100);
                System.out.println("| "+userPokemon.getName() + " current HP: " + userPokemon.getCurrentHp() + "(minskad med 100) | " + wildPokemon.getName() + " current HP: " + wildPokemon.getCurrentHp()+" |");

            }

        }
        //träffsäkerhet avgörs
        if (cpuAttackDamage == userAttackDamage) {
            if (choosenUserAttack.getAccuracy() > choosenCpuAttack.getAccuracy()) {
                System.out.println("| Skaderesultat för användare: " + userAttackDamage + " | Skaderesultat för CPU: " + cpuAttackDamage+"     |");
                System.out.println("| ---------------------------------------------------------------------");
                wildPokemon.setCurrentHp(wildPokemon.getCurrentHp() - 100);
                System.out.println(userPokemon.getName() + " current HP: " + userPokemon.getCurrentHp() + " | " + wildPokemon.getName() + " current HP: " + wildPokemon.getCurrentHp() + " (minskad med 100)|");
            }
            if (choosenUserAttack.getAccuracy() < choosenCpuAttack.getAccuracy()) {
                System.out.println("| Skaderesultat resultat för användare: " + userAttackDamage + "| Skaderesultat för CPU: " + cpuAttackDamage+"     |");
                System.out.println("| ---------------------------------------------------------------------");
                userPokemon.setCurrentHp(userPokemon.getCurrentHp() - 100);
                System.out.println(userPokemon.getName() + " current HP: " + userPokemon.getCurrentHp() + "(minskad med 100) | " + wildPokemon.getName() + " current HP: " + wildPokemon.getCurrentHp()+" |");

            }
        }

        if (wildPokemon.getCurrentHp() <= 0 && userPokemon.getCurrentHp() > 0) {
            System.out.println("\n****************  **************************");
            System.out.println(userPokemon.getName() + " har vunnit efter " + turnCount + " rundor!");
            System.out.println("****************  **************************");
            isAttackTurnOver = true;
            consoleMenu();
        }
        if (userPokemon.getCurrentHp() <= 0 && wildPokemon.getCurrentHp() > 0) {
            System.out.println("\n****************  **************************");
            System.out.println(wildPokemon.getName() + " har vunnit efter " + turnCount + " rundor!");
            System.out.println("****************  **************************");
            isAttackTurnOver = true;
            consoleMenu();
        }

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

}
