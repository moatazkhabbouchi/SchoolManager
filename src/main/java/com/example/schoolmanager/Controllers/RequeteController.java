package com.example.schoolmanager.Controllers;

import com.example.schoolmanager.Models.Emploi;
import com.example.schoolmanager.Services.EmploiService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;


public class RequeteController implements Initializable {
    @FXML
    private TextField AffecterEnseignantID;

    @FXML
    private TextField AffecterSeanceEnseignant;

    @FXML
    private TextField ChercherEmploiClasse;

    @FXML
    private TextField ChercherSeanceClasse;

    @FXML
    private TextField ChercherSéanceMatiere;

    @FXML
    private TextField SupprimerSeanceId;

    @FXML
    private Button affecter;

    @FXML
    private Button chercher1;

    @FXML
    private Button chercher2;

    @FXML
    private Button supprimer;

    EmploiService e = new EmploiService();
    @FXML
    private TableView<ObservableList<String>> tableView;

    @FXML
    private TableColumn<ObservableList<String>, String> idCol;

    @FXML
    private TableColumn<ObservableList<String>, String> classeCol;

    @FXML
    private TableColumn<ObservableList<String>, String> contactCol;

    @FXML
    private TableColumn<ObservableList<String>, String> heureCol;

    @FXML
    private TableColumn<ObservableList<String>, String> jourCol;

    @FXML
    private TableColumn<ObservableList<String>, String> matiereCol;

    @FXML
    private TableColumn<ObservableList<String>, String> nomCol;

    public void initialize(URL url, ResourceBundle resourceBundle){
        ResultSet rs = e.getAllEmploiEnseignant();
        try{
            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(rs.getString("emploiId"));
                row.add(rs.getString("classe"));
                row.add(rs.getString("contact"));
                row.add(rs.getString("heure"));
                row.add(rs.getString("jour"));
                row.add(rs.getString("matiere"));
                row.add(rs.getString("nom"));

                tableView.getItems().add(row);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        idCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        classeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        contactCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        heureCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        jourCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));
        matiereCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(5)));
        nomCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(6)));

    }
    @FXML
    void handleSupprimerSeance(){
        e.deleteById(Integer.parseInt(this.SupprimerSeanceId.getText()));
        refreshTable();
    }

    void refreshTable() {
        this.tableView.getItems().clear();
        ResultSet rs = e.getAllEmploiEnseignant();
        try {
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(rs.getString("emploiId"));
                row.add(rs.getString("classe"));
                row.add(rs.getString("contact"));
                row.add(rs.getString("heure"));
                row.add(rs.getString("jour"));
                row.add(rs.getString("matiere"));
                row.add(rs.getString("nom"));

                tableView.getItems().add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
