/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import dtos.Boat.BoatDTO;
import entities.Boat;
import entities.Harbour;
import entities.Owner;
import utils.EMF_Creator;

public class Populator {
    public static void populate() {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        BoatFacade facade = BoatFacade.getInstance(emf);

        Boat boat = new Boat("Gump", "Honda", "Shrimping Boat", "google.com");

        Harbour harbour = new Harbour(2);

        boat.setHarbour(harbour);


        BoatDTO createdBoat = new BoatDTO(boat);
        facade.createBoat(createdBoat);

    }

    public static void main(String[] args) {
        populate();
    }
}
