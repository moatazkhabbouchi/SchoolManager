package com.example.schoolmanager.Controllers;

import com.example.schoolmanager.Models.Emploi;
import com.example.schoolmanager.Models.Enseignant;
import com.example.schoolmanager.Services.EmploiService;
import com.example.schoolmanager.Services.EnseignantService;
import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class EnseignementController implements Initializable {

    @FXML
    private TextField contact;

    @FXML
    private TextField matricule;

    @FXML
    private TextField nom;

    @FXML
    private Button modifierButton;

    @FXML
    private Button enregistrerButton;

    @FXML
    private Button chercherButton;

    @FXML
    private Button supprimerButton;

    @FXML
    private TableView<Enseignant> tEnseignants;

    @FXML
    private TableColumn<Enseignant, String> colMatricule;

    @FXML
    private TableColumn<Enseignant, String> colNom;

    @FXML
    private TableColumn<Enseignant, String> colContact;

    private EnseignantService ES;
    private EmploiService empService;

    List<Enseignant> listeEnseignant;
    List<Emploi> listeEmploi;

    @FXML
    private ChoiceBox<String> lesJours;
    private ObservableList<String> lesJoursList = FXCollections.observableArrayList("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi");

    @FXML
    private ChoiceBox<String> lesClasses;
    private ObservableList<String> lesClassesListe = FXCollections.observableArrayList("1ere", "2eme", "3eme", "4eme", "5eme", "6eme");

    @FXML
    private ChoiceBox<String> lesHeures;
    private ObservableList<String> lesHeuresListe = FXCollections.observableArrayList("H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8");

    @FXML
    private TextField matiere;

    @FXML
    private TextField matriculePourEmploi;

    @FXML
    private TableView<Emploi> tEmploi;

    @FXML
    private TableColumn<Emploi, String> colClasse;

    @FXML
    private TableColumn<Emploi, String> colMatiere;

    @FXML
    private TableColumn<Emploi, String> colJour;

    @FXML
    private TableColumn<Emploi, String> colHeure;

    @FXML
    private TableColumn<Emploi, String> colEnseignant;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        this.lesJours.setValue("Les jours");
        this.lesJours.setItems(this.lesJoursList);
        this.lesHeures.setValue("Les Heures");
        this.lesHeures.setItems(this.lesHeuresListe);
        this.lesClasses.setValue("Les Classes");
        this.lesClasses.setItems(this.lesClassesListe);

        ES = new EnseignantService();

        colMatricule.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatricule()));
        colNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        colContact.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getContact())));

        empService = new EmploiService();

        colClasse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClasse()));
        colMatiere.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatiere()));
        colJour.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJour()));
        colHeure.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHeure()));
        colEnseignant.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEnseignant().getMatricule()));

        try {
            listeEnseignant = ES.getAllEnseignant();
            tEnseignants.getItems().addAll(listeEnseignant);
            listeEmploi = empService.getAll();
            tEmploi.getItems().addAll(listeEmploi);
            System.out.println(tEnseignants.getItems());
        } catch (IllegalStateException ex) {
            System.out.println(ex.getMessage());
        }
    }



    @FXML
    void handleEnregistrerButton(ActionEvent event){
        if(!matricule.getText().isEmpty() && !nom.getText().isEmpty() && !contact.getText().isEmpty()){
            Enseignant e = new Enseignant(matricule.getText(), nom.getText(), Integer.parseInt(contact.getText()));
            String message = ES.addEnseignant(e);
            this.showAlert("Alert", message);
            refreshTable();
        }else{
            this.showAlert("User not added", "Empty Fields");
        }
    }

    @FXML
    void handleEnregistrerButton2(ActionEvent event){
        String message;
        if(!this.lesJours.getValue().isEmpty()
        && !this.lesClasses.getValue().isEmpty()
        && !this.lesHeures.getValue().isEmpty()
        && !this.matiere.getText().isEmpty()
        && !this.matriculePourEmploi.getText().isEmpty()){
            empService = new EmploiService();
            ES = new EnseignantService();
            message = empService.addEmploi(new Emploi(this.lesClasses.getValue(), this.matiere.getText(), this.lesJours.getValue(), this.lesHeures.getValue(), ES.getEnseignantByMatricule(this.matriculePourEmploi.getText())));
            System.out.println(message);
            refreshTable();
        }
    }

    @FXML
    void handleSupprimerButton(ActionEvent event){
        ES = new EnseignantService();
        ES.deleteEnseignant(this.matricule.getText());
        refreshTable();
    }

    @FXML
    void handleModifierButton(ActionEvent event){
        ES = new EnseignantService();
        Enseignant e = new Enseignant(matricule.getText(), nom.getText(), Integer.parseInt(contact.getText()));
        ES.updateEnseignant(e, this.matricule.getText());
        refreshTable();
    }

    @FXML
    void handleChercherButton(ActionEvent E){
        ES = new EnseignantService();
        tEnseignants.getItems().clear();
        listeEnseignant = ES.getEnseignant(this.nom.getText());
        tEnseignants.getItems().addAll(listeEnseignant);

    }


    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void refreshTable(){
        ES = new EnseignantService();
        empService = new EmploiService();
        try {
            tEnseignants.getItems().clear();
            listeEnseignant = ES.getAllEnseignant();
            tEnseignants.getItems().addAll(listeEnseignant);

            tEmploi.getItems().clear();
            listeEmploi = empService.getAll();
            tEmploi.getItems().addAll(listeEmploi);

            System.out.println(tEnseignants.getItems());
            System.out.println(tEmploi.getItems());
        } catch (IllegalStateException ex) {
            System.out.println(ex.getMessage());
        }

    }
}