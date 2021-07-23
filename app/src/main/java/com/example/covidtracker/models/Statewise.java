package com.example.covidtracker.models;

import java.io.Serializable;

public class Statewise implements Serializable {
    public String active;
    public String confirmed;
    public String deaths;
    public String deltaconfirmed;
    public String deltadeaths;
    public String deltarecovered;
    public String lastupdatedtime;
    public String migratedother;
    public String recovered;
    public String state;
    public String statecode;
    public String statenotes;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getDeltaconfirmed() {
        return deltaconfirmed;
    }

    public void setDeltaconfirmed(String deltaconfirmed) {
        this.deltaconfirmed = deltaconfirmed;
    }

    public String getDeltadeaths() {
        return deltadeaths;
    }

    public void setDeltadeaths(String deltadeaths) {
        this.deltadeaths = deltadeaths;
    }

    public String getDeltarecovered() {
        return deltarecovered;
    }

    public void setDeltarecovered(String deltarecovered) {
        this.deltarecovered = deltarecovered;
    }

    public String getLastupdatedtime() {
        return lastupdatedtime;
    }

    public void setLastupdatedtime(String lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }

    public String getMigratedother() {
        return migratedother;
    }

    public void setMigratedother(String migratedother) {
        this.migratedother = migratedother;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getStatenotes() {
        return statenotes;
    }

    public void setStatenotes(String statenotes) {
        this.statenotes = statenotes;
    }
}
