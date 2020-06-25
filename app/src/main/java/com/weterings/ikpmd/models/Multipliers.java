package com.weterings.ikpmd.models;

public class Multipliers {
        private String multiplier1;
        private String multiplier2;
        private String multiplier3;
        private String multiplier4;

        public Multipliers(String multiplier1, String multiplier2, String multiplier3, String multiplier4){
            this.multiplier1 = multiplier1;
            this.multiplier2 = multiplier2;
            this.multiplier3 = multiplier3;
            this.multiplier4 = multiplier4;
        }
    public void setMultiplier1(String multiplier1) {
        this.multiplier1 = multiplier1;
    }

    public String getMultiplier1() {
        return multiplier1;
    }

    public void setMultiplier2(String multiplier2) {
        this.multiplier2 = multiplier2;
    }

    public String getMultiplier2() {
        return multiplier2;
    }

    public void setMultiplier3(String multiplier3) {
        this.multiplier3 = multiplier3;
    }

    public String getMultiplier3() {
        return multiplier3;
    }

    public void setMultiplier4(String multiplier4) {
        this.multiplier4 = multiplier4;
    }

    public String getMultiplier4() {
        return multiplier4;
    }
}

