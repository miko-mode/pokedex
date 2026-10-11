package com.mapping;

public class AttackTurnStats {
    private int attackTurnNumber;
    private String userAttackName;
    private String cpuAttackName;
    private String userDamagePoints;
    private String cpuDamagePoints;
    private String userCurrentHp;
    private String wildCurrentPoints;
    private String turnWinner;

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

    public String getUserAttackName() {
        return userAttackName;
    }

    public void setUserAttackName(String userAttackName) {
        this.userAttackName = userAttackName;
    }

    public String getCpuAttackName() {
        return cpuAttackName;
    }

    public void setCpuAttackName(String cpuAttackName) {
        this.cpuAttackName = cpuAttackName;
    }
    public String getTurnWinner() {
        return turnWinner;
    }
    public void setTurnWinner(String turnWinner) {
        this.turnWinner = turnWinner;
    }
}
