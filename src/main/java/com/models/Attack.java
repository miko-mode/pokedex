package com.models;

public class Attack {
    public Attack(String name, int baseDamage, int accuracy) {
        this.name = name;
        this.baseDamage = baseDamage;
        this.accuracy = accuracy;
    }

    private String name;
    enum Type {Fire, Water, Grass, Electric, Normal}
    private int baseDamage;
    private int accuracy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
