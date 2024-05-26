package com.example.schoolmanager.Controllers;


import com.example.schoolmanager.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Button loginId;

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    @FXML
    private Text prompt;

    @FXML
    void handleLoginButton(ActionEvent event) {

        if (this.username.getText() != "" && this.password.getText() != "") {
            if (this.username.getText().equals("admin") && this.password.getText().equals("admin")) {
                try{
                    Stage stage2 = (Stage) loginId.getScene().getWindow();
                    // do what you have to do
                    stage2.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Enseignement.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1280, 800);
                    Stage stage = new Stage();
                    stage.setTitle("Enseignement");
                    stage.setScene(scene);
                    stage.show();
                }catch (Exception exception){
                    System.out.println(exception.getMessage());
                }
            } else{
                System.out.println("wrong credentials");
                this.prompt.setText("Cordonnées Erronées!");
            };
        }else {
            this.prompt.setText("Champs Vides!");
            System.out.println("empty fields");
        };

    }



}
