/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.tests;

import com.esprit.entities.Reservation;
import com.esprit.entities.Seance;
import com.esprit.entities.User;
import com.esprit.services.ReservationService;
import com.esprit.services.ServiceSeance;
import java.time.LocalDate;

/**
 *
 * @author hadil
 */
public class MainProg {
    
    public static void main(String[] args) {
        
       
       
        
        // Test the SeanceService class
        ServiceSeance seanceService = new ServiceSeance();
        
        // id coach
        int idCoach = 2; 
        
        // Add a seance
        //////////////// (int id, int nbr_grp, String description, int nbr_seance, int user_id, String color, String titre)
        Seance seance = new Seance(2,1, "haaadiiiilll", 10, new User(idCoach) , "red" , "Yoga");
        //seanceService.ajouter(seance);
        
        // Update the seance's information
        seance.setNbr_seance(20);
        seanceService.modifier(seance);
        
        // Get all the seances
        System.out.println("All seances:");
        for (Seance s : seanceService.afficher()) {
            System.out.println(s);
        }
        
      
        // seanceService.supprimer(seance);
        
        // Tesr de la ReservationService class
        ReservationService reservationService = new ReservationService();
        
        // Add a reservation
        Reservation reservation = new Reservation(2, seance, LocalDate.now());
        reservationService.ajouter(reservation);
        
        // Try to add a reservation for a seance that is already full
        Reservation reservation2 = new Reservation(2, seance, LocalDate.now());
        reservationService.ajouter(reservation2);
        
        
    }
}
