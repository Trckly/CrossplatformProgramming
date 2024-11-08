package org.athleteManager;

import java.io.Serializable;

public class Athlete implements Serializable {
    private String firstName;
    private String lastName;
    private String sport;
    private transient Integer medalsCount;

    public Athlete() {
        firstName = "";
        lastName = "";
        sport = "";
        medalsCount = 0;
    }

    public Athlete(String firstName, String lastName, String sport, int medalsCount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sport = sport;
        this.medalsCount = medalsCount;
    }

    // Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public Integer getMedalsCount() {
        return medalsCount;
    }

    public void setMedalsCount(int medalsCount) {
        this.medalsCount = medalsCount;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " - " + sport + " - Medals: " + medalsCount;
    }
}
