package com.service;

import com.mapping.GameStats;
import com.models.Attack;
import com.models.Pokemon;

public interface IBattleEngine {
    double damagePoint_1=0.5;
    int damagePoint_2=2;
    public void battleEngine();
    public void execute(Pokemon userPokemon, Attack userAttack, Pokemon wildPokemon, Attack cpuAttack);
    public double calculateDamage(Attack attack1, Attack attack2);
    public GameStats statGenerator(String userAttackName, String cpuAttackName, Pokemon userPokemon, Pokemon wildPokemon, double userAttackDamage, double cpuAttackDamage, int turnNumber);

    }
