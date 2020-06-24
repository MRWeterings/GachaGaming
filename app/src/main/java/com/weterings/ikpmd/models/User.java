package com.hsleiden.ipkmd.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    private String name;
    private String key;
    private double score;
    private double shekels;
    private int multiplier1;
    private int multiplier2;
    private int multiplier3;
    private int multiplier4;

    public User() {
    }

    public User(String name, String key, double score, double shekels, int multi1, int multi2, int multi3, int multi4) {
        this.name = name;
        this.key = key;
        this.score = score;
        this.shekels = shekels;
        this.multiplier1 = multi1;
        this.multiplier2 = multi2;
        this.multiplier3 = multi3;
        this.multiplier4 = multi4;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public double getScore() {
        return score;
    }

    public double getGold() {
        return shekels;
    }

    public int getMultiplier1() {
        return multiplier1;
    }

    public int getMultiplier2() {
        return multiplier2;
    }

    public int getMultiplier3() {
        return multiplier3;
    }

    public int getMultiplier4() {
        return multiplier4;
    }
}
