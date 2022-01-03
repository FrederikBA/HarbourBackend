/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import entities.Boat;
import entities.Harbour;
import entities.Owner;
import utils.EMF_Creator;

public class Populator {
    public static void populate() {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        Owner o1 = new Owner("Name", "Address", "Phone");
        Boat b1 = new Boat("Brand", "Make", "Name", "Image");
        Harbour h1 = new Harbour("Name", "Address", 50);

        Boat b2 = new Boat("Brand2", "Make2", "Name2", "Image2");

        o1.addBoat(b1);
        o1.addBoat(b2);
        b1.setHarbour(h1);

        em.getTransaction().begin();
        em.persist(b1);
        em.persist(b2);
        em.persist(o1);
        em.persist(h1);
        em.getTransaction().commit();
    }

    public static void main(String[] args) {
        populate();
    }
}
