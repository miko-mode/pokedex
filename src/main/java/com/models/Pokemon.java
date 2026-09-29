package com.models;

import com.enums.Type;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {

    private String name;
    private Type type;
    private int maxHp;
    private int currentHp;
    private List<Attack> attacks=new ArrayList<>();

    public Pokemon(String name, Type type, int maxHp, int currentHp, List<Attack> attacks) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("namn får inte vara tomt");
        }
        if (maxHp <= 0) {
            throw new IllegalArgumentException("max-HP måste vara > 0");
        }
        this.name = name;
        this.type = type;
        this.maxHp = maxHp;
        this.currentHp = currentHp;
        this.attacks = attacks;
    }

    public Pokemon() {

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

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(List<Attack> attacks) {
        this.attacks = attacks;
    }

    @Override
    public String toString() {
        return "\n---------------------------------------------------------------------"+
                "\nPokemon{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", maxHp=" + maxHp +
                ", currentHp=" + currentHp +"\n"+
                " attacks=" + attacks +
                '}'+"\n";
    }
}
