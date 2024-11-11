package org.athleteManager;

import java.io.Serializable;

public class Address implements Serializable {
    private String street;
    private Integer number;

    public Address(){
        street = "default street";
        number = 15;
    }

    public Address(String street, Integer number) {
        this.street = street;
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return street + " " + number;
    }
}
