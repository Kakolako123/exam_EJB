package com.example.exam_projet.managedbeans;

import com.example.exam_projet.entities.Cd;
import com.example.exam_projet.entities.Borrow;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Stateless
public class UserManagerBean {

    @PersistenceContext(unitName = "cdstorePU")
    private EntityManager em;

    // Liste tous les CDs disponibles
    public List<Cd> listAvailableCds() {
        TypedQuery<Cd> query = em.createQuery("SELECT c FROM Cd c WHERE c.available = true", Cd.class);
        return query.getResultList();
    }

    // Emprunter un CD
    public void borrowCd(Long userId, Long cdId) {
        Cd cd = em.find(Cd.class, cdId);
        if (cd != null && cd.isAvailable()) {
            // Marquer le CD comme emprunté
            cd.setAvailable(false);
            em.merge(cd);

            // Créer un enregistrement de l'emprunt
            Borrow borrow = new Borrow();
            borrow.setUserId(userId);
            borrow.setCd(cd);
            borrow.setBorrowDate(new java.util.Date());
            em.persist(borrow);
        }
    }

    // Lister les CDs empruntés par l'utilisateur
    public List<Borrow> listBorrowedCds(Long userId) {
        TypedQuery<Borrow> query = em.createQuery("SELECT b FROM Borrow b WHERE b.userId = :userId", Borrow.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    // Retourner un CD emprunté
    public void returnCd(Long borrowId) {
        Borrow borrow = em.find(Borrow.class, borrowId);
        if (borrow != null) {
            // Marquer le CD comme disponible
            Cd cd = borrow.getCd();
            cd.setAvailable(true);
            em.merge(cd);

            // Supprimer l'emprunt
            em.remove(borrow);
        }
    }
}