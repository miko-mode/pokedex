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
            wildPokemon = cpuChoice();
            System.out.println(wildPokemon);
            if (!userPokemon.getAttacks().isEmpty()) {
                System.out.println("Välj en Attack utav Pokemonen från samling");
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
                }

            }
        }
        if (!isFound) {
            System.out.println("Error: Pokemonen finns inte i samlingen");
            consoleMenu();
        }

    }

    private void execute(Pokemon userPokemon, Attack choosenUserAttack, Pokemon wildPokemon, Attack choosenCpuAttack) {
        double userAttackPower = calculateDamage(choosenUserAttack, choosenCpuAttack);
        double cpuAttackPower = calculateDamage(choosenCpuAttack, choosenUserAttack);

        if (userAttackPower > cpuAttackPower) {
            wildPokemon.setCurrentHp(wildPokemon.getCurrentHp() - 100);
        }
        if (cpuAttackPower > userAttackPower) {
            userPokemon.setCurrentHp(userPokemon.getCurrentHp() - 100);
        }
        //träffsäkerhet avgörs
        if (cpuAttackPower == userAttackPower) {
            if (choosenUserAttack.getAccuracy() > choosenCpuAttack.getAccuracy()) {
                wildPokemon.setCurrentHp(wildPokemon.getCurrentHp() - 100);
            }
            if (choosenUserAttack.getAccuracy() < choosenCpuAttack.getAccuracy()) {
                userPokemon.setCurrentHp(userPokemon.getCurrentHp() - 100);
            }
        }


    }

    private double calculateDamage(Attack attack1, Attack attack2) {
        if (attack1.getType().equals(Type.Fire) && attack2.getType().equals(Type.Grass)) {
            return 2 * attack1.getBaseDamage();
        }
    return 0;
    }

    //shuffles randomly and returns the top wild pokemon
    public Pokemon cpuChoice() {
        FileService fileService = new FileService();
        List<Pokemon> wild_Pokemons_List = fileService.loadWildPokemons();
        Collections.shuffle(wild_Pokemons_List);
        return wild_Pokemons_List.get(0);
    }

}
