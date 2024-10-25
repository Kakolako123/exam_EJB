package com.example.exam_projet.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.example.exam_projet.entities.Cd;
import com.example.exam_projet.entities.Borrow;
import java.util.List;

@Stateless
public class UserService {

    @PersistenceContext
    private EntityManager em;

    // Lister tous les CD disponibles
    public List<Cd> listAvailableCds() {
        return em.createQuery("SELECT c FROM Cd c WHERE c.borrowed = false", Cd.class).getResultList();
    }

    // Emprunter un CD
    public void borrowCd(int cdId, String user) {
        Cd cd = em.find(Cd.class, cdId);
        if (cd != null && !cd.isBorrowed()) {
            cd.setBorrowed(true);
            Borrow borrow = new Borrow(user, cd);
            em.persist(borrow);
            em.merge(cd);
        }
    }

    // Lister les CDs empruntés par un utilisateur
    public List<Borrow> listUserBorrows(String user) {
        return em.createQuery("SELECT b FROM Borrow b WHERE b.user = :user", Borrow.class)
                .setParameter("user", user)
                .getResultList();
    }

    // Retourner un CD
    public void returnCd(int borrowId) {
        Borrow borrow = em.find(Borrow.class, borrowId);
        if (borrow != null) {
            Cd cd = borrow.getCd();
            cd.setBorrowed(false);
            em.remove(borrow);
            em.merge(cd);
        }
    }
}
