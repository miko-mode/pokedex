package com.models;

import java.util.List;

public class Pokemon {
    public Pokemon(String name, int maxHp, int currentHp, List<Attack> attacks) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = currentHp;
        this.attacks = attacks;
    }

    private String name;
    enum Type {Fire, Water, Grass, Electric, Normal}
    private int maxHp;
    private int currentHp;
    private List<Attack> attacks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
