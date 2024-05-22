package com.example.schoolmanager.Models;

import com.example.schoolmanager.Services.EnseignantService;

import java.util.List;

public class Emploi {
    private int emploiId;
    private String classe;
    private String matiere;
    private String jour;
    private String heure;
    private Enseignant enseignant;



    public Emploi(String classe, String matiere, String jour, String heure, Enseignant enseignant) {
        this.classe = classe;
        this.matiere = matiere;
        this.jour = jour;
        this.heure = heure;
        this.enseignant = enseignant;
    }
    public Emploi(){

    }

    public int getEmploiId() {
        return emploiId;
    }

    public void setEmploiId(int emploiId) {
        this.emploiId = emploiId;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    @Override
    public String toString() {
        return "Emploi{" +
                "emploiId=" + emploiId +
                ", classe='" + classe + '\'' +
                ", matiere='" + matiere + '\'' +
                ", jour='" + jour + '\'' +
                ", heure='" + heure + '\'' +
                ", enseignant=" + enseignant +
                '}';
    }
}
