package com.example.exam_projet.ejb;

import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.example.exam_projet.entities.Cd;

@Stateful
public class AdminService {

    @PersistenceContext
    private EntityManager em;

    // Ajouter un nouveau CD
    public void addCd(String title, String artist) {
        Cd cd = new Cd(title, artist);
        em.persist(cd);
    }

    // Supprimer un CD
    public void removeCd(int cdId) {
        Cd cd = em.find(Cd.class, cdId);
        if (cd != null) {
            em.remove(cd);
        }
    }

    // Mettre à jour les détails d'un CD
    public void updateCd(int cdId, String newTitle, String newArtist) {
        Cd cd = em.find(Cd.class, cdId);
        if (cd != null) {
            cd.setTitle(newTitle);
            cd.setArtist(newArtist);
            em.merge(cd);
        }
    }
}
