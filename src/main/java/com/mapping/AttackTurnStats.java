package com.mapping;

public class AttackTurnStats {
    private int attackTurnNumber;
    private String userDamagePoints;
    private String cpuDamagePoints;
    private String userCurrentHp;
    private String wildCurrentPoints;

    public int getAttackTurnNumber() {
        return attackTurnNumber;
    }

    public void setAttackTurnNumber(int attackTurnNumber) {
        this.attackTurnNumber = attackTurnNumber;
    }

    public String getUserDamagePoints() {
        return userDamagePoints;
    }

    public void setUserDamagePoints(String userDamagePoints) {
        this.userDamagePoints = userDamagePoints;
    }

    public String getCpuDamagePoints() {
        return cpuDamagePoints;
    }

    public void setCpuDamagePoints(String cpuDamagePoints) {
        this.cpuDamagePoints = cpuDamagePoints;
    }

    public String getUserCurrentHp() {
        return userCurrentHp;
    }

    public void setUserCurrentHp(String userCurrentHp) {
        this.userCurrentHp = userCurrentHp;
    }

    public String getWildCurrentPoints() {
        return wildCurrentPoints;
    }

    public void setWildCurrentPoints(String wildCurrentPoints) {
        this.wildCurrentPoints = wildCurrentPoints;
    }
}
