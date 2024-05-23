package com.example.schoolmanager.Controllers;

import com.example.schoolmanager.Models.Emploi;
import com.example.schoolmanager.Models.Enseignant;
import com.example.schoolmanager.Services.EmploiService;
import com.example.schoolmanager.Services.EnseignantService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import javax.xml.transform.Result;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class RequeteController implements Initializable {
    @FXML
    private TextField AffecterEnseignantID;

    @FXML
    private TextField AffecterSeanceEnseignant;

    @FXML
    private TextField ChercherEmploiClasse;

    @FXML
    private TextField ChercherSeanceMatiere;

    @FXML
    private ChoiceBox lesClasses;
    private ObservableList<String> lesClassesListe = FXCollections.observableArrayList("1ere", "2eme", "3eme", "4eme", "5eme", "6eme");
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

    @FXML
    private TableView<Enseignant> tEnseignants;

    @FXML
    private TableColumn<Enseignant, String> matriculeCol;

    @FXML
    private TableColumn<Enseignant, String> nomCol2;

    @FXML
    private TableColumn<Enseignant, String> contactCol2;

    public void initialize(URL url, ResourceBundle resourceBundle){
        EnseignantService ES = new EnseignantService();
        List<Enseignant> listeEnseignant;
        matriculeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatricule()));
        nomCol2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        contactCol2.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getContact())));
        try {
            listeEnseignant = ES.getAllEnseignant();
            tEnseignants.getItems().addAll(listeEnseignant);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        this.lesClasses.setValue("Les Classes");
        this.lesClasses.setItems(this.lesClassesListe);
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

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                SupprimerSeanceId.setText(newValue.get(0));
                AffecterSeanceEnseignant.setText(newValue.get(0));
            }
        });

        tEnseignants.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                AffecterEnseignantID.setText(newValue.getMatricule());
            }
        });

        tableView.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.F5) {
                        refreshTable();
                    }
                });
            }
        });

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
    @FXML
    void handleChercher1(){
        EmploiService e = new EmploiService();
        ResultSet rs;
        if(this.ChercherSeanceMatiere.getText() != ""){
            rs = e.getAllEmploiEnseignant(this.lesClasses.getValue().toString(), this.ChercherSeanceMatiere.getText());
        }else{
            rs = e.getAllEmploiEnseignant(this.lesClasses.getValue().toString());
        }

        tableView.getItems().clear();
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    @FXML
    void handleAffecterButton() {
        EmploiService e = new EmploiService();
        e.setEnseignat(Integer.parseInt(this.AffecterSeanceEnseignant.getText()), this.AffecterEnseignantID.getText());
        refreshTable();
    }


}
