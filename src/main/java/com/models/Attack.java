package com.models;

import jakarta.persistence.*;

@Entity
@Table(name = "attack")
public class Attack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "namn", nullable = false, unique = true)
    private String name;
    @Column(name = "type", nullable = false)
    private Type type;
    @Column(name = "baseDamage", nullable = false)
    private int baseDamage;
    @Column(name = "accuracy", nullable = false)
    private int accuracy;
    @ManyToOne
    @JoinColumn(name = "pokemon_id", nullable = false)
    private Pokemon pokemon;

    public Attack(String name, Type type, int baseDamage, int accuracy, Pokemon pokemon) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("namn får inte vara tomt");
        }
        if (baseDamage <= 0) {
            throw new IllegalArgumentException("basskada måste vara > 0");
        }
        if (accuracy < 0 || accuracy > 100) {
            throw new IllegalArgumentException("träffsäkerhet måste vara 0-100");
        }
        this.name = name;
        this.type = type;
        this.baseDamage = baseDamage;
        this.accuracy = accuracy;
        this.pokemon = pokemon;
    }

    public Attack() {

    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public String toString() {
        return "[Namn:" + name + ", " + "Typ:" + type + ", " + "Basskada:" + baseDamage + ", " + "Träffsäkerhet:" + accuracy + "]\n\t\t";
    }
}
