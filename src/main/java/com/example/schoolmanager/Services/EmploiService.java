package com.example.schoolmanager.Services;

import com.example.schoolmanager.Models.Emploi;
import com.example.schoolmanager.Models.Enseignant;
import com.example.schoolmanager.Utils.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmploiService {
    Connection cnx = DB.getInstance().getCnx();
    public String addEmploi(Emploi e){
        try{
            String query = "INSERT INTO emploi (classe, matiere, jour, heure, matriculeEnseignant) VALUES (?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, e.getClasse());
            ps.setString(2, e.getMatiere());
            ps.setString(3, e.getJour());
            ps.setString(4, e.getHeure());
            if(e.getEnseignant().getMatricule()  != "000"){
                System.out.println("True");
                ps.setString(5, e.getEnseignant().getMatricule());

            }else{

                ps.setString(5, "UNKNOWN");
            }


            int rowsAffected = ps.executeUpdate();
            return e.toString();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            return ex.getMessage();
        }

    }

    public List<Emploi> getAll(){
        EnseignantService es = new EnseignantService();
        List<Emploi> lEmploi = new ArrayList<>();
        try{
            String query = "SELECT * FROM emploi";
            PreparedStatement ps = cnx.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Emploi e = new Emploi();
                e.setClasse(rs.getString("classe"));
                e.setMatiere(rs.getString("matiere"));
                e.setJour(rs.getString("jour"));
                e.setHeure(rs.getString("heure"));
                e.setEnseignant(es.getEnseignantByMatricule(rs.getString("matriculeEnseignant")));
                lEmploi.add(e);
            }
        }catch(SQLException ex){
            System.out.println("here:" + ex.getMessage());
        }
        return lEmploi;
    }

    public ResultSet getAllEmploiEnseignant(){
        ResultSet rs;
        try{
            String query = "SELECT e.emploiId, e.classe, e.matiere, e.jour, e.heure, es.nom, es.contact  FROM emploi e, enseignant es where e.matriculeEnseignant = es.matricule";
            PreparedStatement ps = cnx.prepareStatement(query);
            rs = ps.executeQuery();

        }catch(SQLException ex){
            System.out.println("here:" + ex.getMessage());
            rs = null;
        }
        return rs;
    }

    public void setUnknown(String matricule){
        try{
            String query = "UPDATE emploi e SET matriculeEnseignant = 'UNKNOWN' WHERE e.matriculeEnseignant = ?";
            PreparedStatement ps = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, matricule);
            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteById(int Id){
        try{
            String query = "DELETE FROM emploi WHERE emploiId = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setInt(1, Id);
            System.out.println(ps.executeUpdate());

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public void deleteAllUknown(){
        try{
            String query = "DELETE FROM emploi WHERE matriculeEnseignant = 'UNKNOWN'";
            PreparedStatement ps = cnx.prepareStatement(query);
            System.out.println(ps.executeUpdate());

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
}
