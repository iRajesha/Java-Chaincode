/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uidai.gov.aadhaar.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rputtara
 */
public class Experiment {
    public static void main(String args[]){
        try {
            Aadhaar aadhaar = new Aadhaar("00ORACLE00", "First Name", "Last Name");
            System.out.println("uidai.gov.aadhaar.model.Experiment.main()"+ new ObjectMapper().writeValueAsString(aadhaar));
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Experiment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
