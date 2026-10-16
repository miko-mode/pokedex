package com.service;

import com.mapping.GameStats;
import com.models.Attack;
import com.models.Pokemon;

public interface IBattleEngine {
    double damagePoint_1 = 0.5;
    int damagePoint_2 = 2;

    void battleEngine();

    void execute(Pokemon userPokemon, Attack userAttack, Pokemon wildPokemon, Attack cpuAttack);

    double calculateDamage(Attack attack1, Attack attack2);

    void statGenerator(String userAttackName, String cpuAttackName, Pokemon userPokemon, Pokemon wildPokemon, double userAttackDamage, double cpuAttackDamage, int turnNumber);

}
