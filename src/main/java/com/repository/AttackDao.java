package com.repository;

import com.models.Attack;
import java.util.List;

public interface AttackDao {
    void sparaAttack(Attack attack);

    List<Attack> allaAttack();

    Attack hittaAttack(Integer id);

    void taBortAttack(Integer id);

    void uppdateraAttack(Attack attack);

    Attack hittaAttackViaNamn(String attackName);
}
