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
            ps.setString(5, e.getEnseignant().getMatricule());


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
}
