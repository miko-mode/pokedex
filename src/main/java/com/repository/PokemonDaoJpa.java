package com.repository;

import com.config.EntityManagerUtil;
import com.models.Pokemon;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class PokemonDaoJpa implements PokemonDao {

    private final EntityManager em = EntityManagerUtil.getEntityManager();

    @Override
    public void sparaPokemon(Pokemon pokemon) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(pokemon);
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
    public List<Pokemon> allaPokemon() {
        try {
            return em.createQuery(
                    "SELECT p FROM Pokemon p",
                    Pokemon.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

    @Override
    public Pokemon hittaPokemon(Integer id) {
        try {
            return em.find(Pokemon.class, id);

        } finally {
            em.close();
        }
    }

    @Override
    public void taBortPokemon(Integer id) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createNativeQuery("SET TRANSACTION ISOLATION LEVEL SERIALIZABLE").executeUpdate();
            Pokemon pokemon = em.find(Pokemon.class, id);
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
    public void uppdateraPokemon(Pokemon pokemon) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(pokemon);
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
    public Pokemon hittaPokemonViaNamn(String pokemonName) {
        try {
            List<Pokemon> resultat = em.createQuery(
                            "SELECT p FROM Pokemon p WHERE p.name = :pokemonName",
                            Pokemon.class
                    )
                    .setParameter("pokemonName", pokemonName)
                    .getResultList();

            return resultat.isEmpty() ? null : resultat.get(0);

        } finally {
            em.close();
        }
    }
}
