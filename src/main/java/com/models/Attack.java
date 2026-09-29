package com.models;

import com.enums.Type;

public class Attack {

    private String name;
    private Type type;
    private int baseDamage;
    private int accuracy;

    public Attack(String name, Type type, int baseDamage, int accuracy) {
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
    }

    public Attack() {

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
        return name + ":" + type + ":" + baseDamage + ":" + accuracy;
    }
}
