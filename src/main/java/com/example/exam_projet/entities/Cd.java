package com.example.exam_projet.entities;

import jakarta.persistence.*;


@Entity
public class Cd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String artist;
    private boolean available = true; // Par défaut, les CDs sont disponibles

    // Constructeur par défaut requis par JPA
    public Cd() {
    }

    // Constructeur personnalisé pour créer un CD avec un titre et un artiste
    public Cd(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Méthode pour vérifier si le CD est emprunté (non disponible)
    public boolean isBorrowed() {
        return !available;
    }

    // Méthode pour définir si le CD est emprunté ou non
    public void setBorrowed(boolean b) {
        this.available = !b; // Si b est true, le CD est emprunté (available = false)
    }
}