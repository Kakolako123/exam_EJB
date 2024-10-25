package com.example.exam_projet.managedbeans;

import com.example.exam_projet.ejb.UserService;
import com.example.exam_projet.entities.Cd;
import jakarta.ejb.EJB;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import java.util.List;

@ManagedBean
@RequestScoped
public class CdManagedBean {

    @EJB
    private UserService userService;

    public List<Cd> getAvailableCds() {
        return userService.listAvailableCds();
    }

    public void borrowCd(int cdId, String user) {
        userService.borrowCd(cdId, user);
    }

    public void returnCd(int borrowId) {
        userService.returnCd(borrowId);
    }
}
