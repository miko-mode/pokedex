package com.repository;

import com.models.Pokemon;

import java.util.List;

public interface PokemonDao {
    void sparaPokemon(Pokemon pokemon);

    List<Pokemon> allaPokemon();

    Pokemon hittaPokemon(Integer id);

    void taBortPokemon(Integer id);

    void uppdateraPokemon(Pokemon pokemon);

    Pokemon hittaPokemonViaNamn(String pokemonName);

}
