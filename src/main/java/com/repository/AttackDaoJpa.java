package com.repository;

import com.models.Attack;
import com.models.Pokemon;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class AttackDaoJpa implements AttackDao {
    private EntityManager em;

    public AttackDaoJpa(EntityManager em) {
        this.em = em;
    }
    @Override
    public void sparaAttack(Attack attack) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(attack);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Attack> allaAttack() {
        try {
            return em.createQuery(
                    "SELECT a FROM Attack a",
                    Attack.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

    @Override
    public Attack hittaAttack(Integer id) {
        try {
            return em.find(Attack.class, id);

        } finally {
            em.close();
        }
    }

    @Override
    public void taBortAttack(Integer id) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createNativeQuery("SET TRANSACTION ISOLATION LEVEL SERIALIZABLE").executeUpdate();
            Attack pokemon = em.find(Attack.class, id);
            if (pokemon != null) {
                em.remove(pokemon);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void uppdateraAttack(Attack attack) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(attack);
            tx.commit();

        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }

    @Override
    public Attack hittaAttackViaNamn(String attackName) {
        try {
            List<Attack> resultat = em.createQuery(
                            "SELECT a FROM Attack a WHERE a.name = :attackName",
                            Attack.class
                    )
                    .setParameter("attackName", attackName)
                    .getResultList();

            return resultat.isEmpty() ? null : resultat.get(0);

        } finally {
            em.close();
        }
    }
}
