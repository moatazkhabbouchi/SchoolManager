package com.example.schoolmanager.Controllers;

import com.example.schoolmanager.HelloApplication;
import com.example.schoolmanager.Models.Emploi;
import com.example.schoolmanager.Models.Enseignant;
import com.example.schoolmanager.Services.EmploiService;
import com.example.schoolmanager.Services.EnseignantService;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
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
    private Button deleteUnknown;

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


    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private AnchorPane scene1AnchorPane;

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
            System.out.println(tEmploi.getItems());
        } catch (IllegalStateException ex) {
            System.out.println(ex.getMessage());
        }
        tEnseignants.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                matricule.setText(newValue.getMatricule());
                nom.setText(newValue.getNom());
                contact.setText(Integer.toString(newValue.getContact()));
            }
        });

        tEnseignants.sceneProperty().addListener((observable, oldScene, newScene) -> {
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
    void handleEnregistrerButton(ActionEvent event){
        //contact trop long
        boolean isNumber = isNumeric(this.contact.getText());
        if(isNumber){
            if(this.contact.getText().length() > 8){
                showPopup("Contact trop long, Longueur Maximal est 8", "#e54141");
                return;
            }
            //contact trop court
            if(this.contact.getText().length() < 8){
                showPopup("Contact trop court, Longueur minimal est 8", "#e54141");
                return;
            }

        }else{
            showPopup("Le contact saisi n'est pas un nombre", "#e54141");
            return;
        }


        if(!matricule.getText().isEmpty() && !nom.getText().isEmpty() && !contact.getText().isEmpty()){
            Enseignant e = new Enseignant(matricule.getText(), nom.getText(), Integer.parseInt(contact.getText()));
            int rows = ES.addEnseignant(e);
            if(rows != 0){
                showPopup("Enseignant Ajouté avec Succès!", "green");
            }else{
                showPopup("Erreur Lors de l'ajout!","#e54141");
            }
            refreshTable();
        }else{
           showPopup("Champs vides", "#e54141");
        }
    }

    @FXML
    void handleEnregistrerButton2(ActionEvent event){
        String message;
        if(this.lesJours.getValue() != "Les jours"
        && this.lesClasses.getValue() != "Les Classes"
        && this.lesHeures.getValue() != "Les Heures"
        && !this.matiere.getText().isEmpty()){
            empService = new EmploiService();
            ES = new EnseignantService();
            Enseignant emp1 = ES.getEnseignantByMatricule(this.matriculePourEmploi.getText());
            System.out.println(emp1);
            message = empService.addEmploi(new Emploi(this.lesClasses.getValue(), this.matiere.getText(), this.lesJours.getValue(), this.lesHeures.getValue(), emp1));
            System.out.println(message);
            showPopup("Séance Ajoutée avec Succès!", "green");
            refreshTable();
        }else{
            showPopup("Champs vides", "#e54141");
        }
    }

    @FXML
    void handleSupprimerButton(ActionEvent event){
        ES = new EnseignantService();
        empService.setUnknown(this.matricule.getText());
        int rowsAffected = ES.deleteEnseignant(this.matricule.getText());
        if(rowsAffected != 0){
            showPopup("Suppression avec Succès!", "green");
        }else{
            showPopup("Erreur lors de la supression", "#e54141");
        }
        refreshTable();
    }

    @FXML
    void handleModifierButton(ActionEvent event){
        ES = new EnseignantService();
        Enseignant e = new Enseignant(matricule.getText(), nom.getText(), Integer.parseInt(contact.getText()));
        int rowsAffected = ES.updateEnseignant(e, this.matricule.getText());
        boolean isNumber = isNumeric(this.contact.getText());
        if(isNumber){
            if(this.contact.getText().length() > 8){
                showPopup("Contact trop long, Longueur Maximal est 8", "#e54141");
                return;
            }
            //contact trop court
            if(this.contact.getText().length() < 8){
                showPopup("Contact trop court, Longueur minimal est 8", "#e54141");
                return;
            }

        }else{
            showPopup("Le contact saisi n'est pas un nombre", "#e54141");
            return;
        }
        if(rowsAffected!=0){
            showPopup("Enseignant Modifié avec Succès!", "green");
        }else{
            showPopup("Erreur Lors de Modification", "#e54141");
        }
        refreshTable();
    }

    @FXML
    void handleChercherButton(ActionEvent E){
        ES = new EnseignantService();
        tEnseignants.getItems().clear();
        listeEnseignant = ES.getEnseignant(this.nom.getText());
        tEnseignants.getItems().addAll(listeEnseignant);

    }
    @FXML
    void handleRequeteButton(ActionEvent e){

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Requete.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 800);
            Stage stage = new Stage();
            stage.setTitle("Requete");
            stage.setScene(scene);
            stage.show();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }


    }

    @FXML
    void handleDeleteUnknown(){
        int rowsAffected = empService.deleteAllUknown();
        if(rowsAffected != 0){
            showPopup("Seances sans enseignants ont été supprimées avec succès!", "green");
        }else{
            showPopup("Toutes les séances ont des enseignants", "#e54141");
        }

        refreshTable();
    }
    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void refreshTable(){
        ES = new EnseignantService();

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

    void showPopup(String message, String color){
        Popup p = new Popup();
        Label popupLabel = new Label(message);
        popupLabel.setStyle("-fx-background-color: " + color + "; -fx-border-radius:10px; -fx-text-fill: white; -fx-padding: 20px; -fx-font-size:20;");

        p.getContent().add(popupLabel);
        p.setAutoHide(true);

        // Position the popup near the contact text field
        Point2D p2d = this.tEnseignants.localToScene(-75.0, 200.0);
        p.show(this.tEnseignants, p2d.getX() + this.tEnseignants.getScene().getX() + this.tEnseignants.getScene().getWindow().getX(),
                p2d.getY() + this.tEnseignants.getScene().getY() + this.tEnseignants.getScene().getWindow().getY());

        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event1 -> p.hide());
        delay.play();
    }

    public boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}