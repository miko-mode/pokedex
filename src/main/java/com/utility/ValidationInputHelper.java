package com.utility;

import com.models.Type;
import com.models.Attack;
import com.models.Pokemon;
import com.service.PokemonOperations;

public class ValidationInputHelper extends PokemonOperations {

    public static void attackTypeValidation(String attackType, Attack attack) {
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

    public static void pokemanAttackTypeValidation(String pokemanAttackType, Pokemon pokemon) {
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
