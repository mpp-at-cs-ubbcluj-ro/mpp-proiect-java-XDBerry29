package com.example.concurscopii.controller;

import com.example.concurscopii.HelloApplication;
import com.example.concurscopii.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;

public class LoginController {
    @FXML
    public TextField UsernameField;
    @FXML
    public javafx.scene.control.PasswordField PasswordField;
    @FXML
    public Button SubmitButton;
    @FXML
    public Label ErrorLabel;

    Service service;

    public void setService(Service service){
        this.service=service;
    }

    public void handleLogin(ActionEvent actionEvent) {

        if(service.verify_account(UsernameField.getText(),PasswordField.getText())) {
            ErrorLabel.setText("Login succesful!");
            try{
                URL fxmlLocation = HelloApplication.class.getResource("views/ProbeView.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Main Page");
                ProbeController ProbeController = fxmlLoader.getController();
                //curseController.setUserCurent(u);
                ProbeController.setService(service);
                stage.show();
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        else
            ErrorLabel.setText("Login failed!");
    }

}
