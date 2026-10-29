package com.service;

import com.models.Attack;
import com.models.Pokemon;
import com.models.Type;

public interface IBattleEngine {

    void battleEngine();
    void executeUserAttack(Attack userAttack, Pokemon wildPokemon);
    void executeCpuAttack(Attack cpuAttack, Pokemon userPokemon);
    double calculateDamage(Attack attackerAttack, Pokemon defender);
    double calculateTypeEfficiency(Type attackerType, Type defenderType);
    void statGenerator(Pokemon attacker, Pokemon defender, int totalNumberOfTurns);

}
