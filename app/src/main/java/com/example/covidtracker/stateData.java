package com.example.covidtracker;

public class stateData {
    private String sNo, stateName, actCase, recCase, deathCase;

    public stateData(String sNo, String stateName, String actCase, String recCase, String deathCase) {
        this.sNo = sNo;
        this.stateName = stateName;
        this.actCase = actCase;
        this.recCase = recCase;
        this.deathCase = deathCase;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getActCase() {
        return actCase;
    }

    public void setActCase(String actCase) {
        this.actCase = actCase;
    }

    public String getRecCase() {
        return recCase;
    }

    public void setRecCase(String recCase) {
        this.recCase = recCase;
    }

    public String getDeathCase() {
        return deathCase;
    }

    public void setDeathCase(String deathCase) {
        this.deathCase = deathCase;
    }
}
