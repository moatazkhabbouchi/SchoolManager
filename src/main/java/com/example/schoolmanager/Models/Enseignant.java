package com.example.schoolmanager.Models;

public class Enseignant {
    private String matricule;
    private String nom;
    private int contact;

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Enseignant{" +
                "matricule=" + matricule +
                ", nom='" + nom + '\'' +
                ", contact=" + contact +
                '}';
    }

    public Enseignant(String matricule, String nom, int contact) {
        this.matricule = matricule;
        this.nom = nom;
        this.contact = contact;
    }
    public Enseignant(){
        this.matricule = "000";
        this.nom = "nom";
        this.contact = 000;
    }
}
