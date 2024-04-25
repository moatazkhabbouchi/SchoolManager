package com.example.schoolmanager.Services;

import com.example.schoolmanager.Models.Enseignant;
import com.example.schoolmanager.Utils.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnseignantService {
    Connection cnx = DB.getInstance().getCnx();
    public String addEnseignant(Enseignant e){
        try{
            String query = "INSERT INTO enseignant (matricule, nom, contact) VALUES (?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, e.getMatricule());
            ps.setString(2, e.getNom());
            ps.setInt(3, e.getContact());

            int rowsAffected = ps.executeUpdate();
            return e.toString();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            return ex.getMessage();
        }

    }
    public List<Enseignant> getAllEnseignant(){
        List<Enseignant> lEnseignants = new ArrayList<>();
        try{
            String query = "SELECT * FROM enseignant";
            PreparedStatement ps = cnx.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Enseignant e = new Enseignant();
                e.setMatricule(rs.getString("matricule"));
                e.setNom(rs.getString("nom"));
                e.setContact(rs.getInt("contact"));
                lEnseignants.add(e);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return lEnseignants;
    }

    public void deleteEnseignant(String matricule){
        List<Enseignant> lEnseignants = new ArrayList<>();
        try{
            String query = "DELETE FROM enseignant WHERE matricule = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, matricule);
            System.out.println(ps.executeUpdate());

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void updateEnseignant(Enseignant e, String matricule){
        try{
            String query = "update enseignant set `nom` = ?, `contact` = ? where matricule = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, e.getNom());
            ps.setInt(2, e.getContact());
            ps.setString(3, matricule);
            System.out.println(ps.executeUpdate());
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public List<Enseignant> getEnseignant(String nom){
        List<Enseignant> lEnseignants = new ArrayList<>();
        try {
            String query = "SELECT * FROM enseignant WHERE `nom` = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Enseignant e = new Enseignant();
                e.setMatricule(rs.getString("matricule"));
                e.setNom(rs.getString("nom"));
                e.setContact(rs.getInt("contact"));
                lEnseignants.add(e);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return lEnseignants;
    }

    public Enseignant getEnseignantByMatricule(String matricule){
        Enseignant e = new Enseignant();
        try {
            String query = "SELECT * FROM enseignant WHERE `matricule` = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, matricule);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                e.setMatricule(rs.getString("matricule"));
                e.setNom(rs.getString("nom"));
                e.setContact(rs.getInt("contact"));
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return e;
    }
}
