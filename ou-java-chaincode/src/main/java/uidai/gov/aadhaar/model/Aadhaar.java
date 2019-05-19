/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uidai.gov.aadhaar.model;

import java.io.Serializable;

/**
 *
 * @author irajesha
 */
public class Aadhaar implements Serializable{
    private String aadhaarNumber;
    private String firstName;
    private String lastName;
    private String registeredBy;

    public String getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(String registeredBy) {
        this.registeredBy = registeredBy;
    }
     public Aadhaar() {
        
    }

    public Aadhaar(String aadhaarNumber, String firstName, String lastName, String registeredBy) {
        this.aadhaarNumber = aadhaarNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registeredBy = registeredBy;
    }

    public Aadhaar(String aadhaarNumber, String firstName, String lastName) {
        this.aadhaarNumber = aadhaarNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

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
}
