package org.athleteManager;

import java.io.Serializable;

public class Athlete implements Serializable {
    private String firstName;
    private String lastName;
    private String sport;
    private Integer age;
    private transient Integer medalsCount;


    public Athlete() {
        firstName = "";
        lastName = "";
        sport = "";
        medalsCount = 0;
        this.age = 0;
    }

    public Athlete(String firstName, String lastName, String sport) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sport = sport;
        this.medalsCount = null;
        this.age = 0;
    }

    public Athlete(String firstName, String lastName, String sport, Integer medalsCount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sport = sport;
        this.medalsCount = medalsCount;
        this.age = 0;
    }

    public Athlete(String firstName, String lastName, String sport, int medalsCount, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sport = sport;
        this.medalsCount = medalsCount;
        this.age = age;
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

    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " - " + sport + " - Medals: " + medalsCount + " - Age: " + age;
    }
}
