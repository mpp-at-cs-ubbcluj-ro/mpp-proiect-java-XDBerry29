package com.example.concurscopii.controller;

import com.example.concurscopii.HelloApplication;
import com.example.concurscopii.domain.Copil;
import com.example.concurscopii.domain.Proba;
import com.example.concurscopii.service.Service;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.Objects;

public class ProbeController {
    @FXML
    public TableView<Proba> ProbeTableView;
    @FXML
    public TableColumn<Proba,String> ProbaColumn;
    @FXML
    public TableColumn<Proba,String> VarstaProbaColumn;
    @FXML
    public TableView<Copil> CopiiTableView;
    @FXML
    public TableColumn< Copil, String> NumeColumn;
    @FXML
    public TableColumn<Copil,Integer> VarstaColumn;
    @FXML
    public TextField ProbaField;
    @FXML
    public ComboBox<Integer> VarstaMinCombo;
    @FXML
    public ComboBox<Integer> VarstaMaxCombo;
    @FXML
    public Button SearchButton;
    @FXML
    public TextField NumeField;
    @FXML
    public ComboBox<Integer> VarstaCombo;
    @FXML
    public Button RegisterButton;
    @FXML
    public Label RegisterBox;
    @FXML
    public Button LogOutButton;

    Service service;

    public void setService(Service service){
        this.service=service;
        initModel();
    }


    private ObservableList<Proba> modelProba = FXCollections.observableArrayList();
    private ObservableList<Copil> modelCopil = FXCollections.observableArrayList();
    private ObservableList<Integer> modelVarstaMin =  FXCollections.observableArrayList();
    private ObservableList<Integer> modelVarstaMax =  FXCollections.observableArrayList();
    private ObservableList<Integer> modelVarsta =  FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ProbaColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        VarstaProbaColumn.setCellValueFactory(c -> {
            Proba proba = c.getValue();

            String interval = proba.getVarsta_min() + " - " + proba.getVarsta_max();
            return new ReadOnlyObjectWrapper<>(interval);
        });
        ProbeTableView.setItems(modelProba);

        VarstaMinCombo.setItems(modelVarstaMin);
        VarstaMaxCombo.setItems(modelVarstaMax);
        VarstaCombo.setItems(modelVarsta);

        NumeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        VarstaColumn.setCellValueFactory(new PropertyValueFactory<>("varsta"));

//        CopiiTableView.getSelectionModel().setSelectionMode(
//                SelectionMode.MULTIPLE
//        );

        /*tableViewCurse.setRowFactory(tv -> {
            TableRow row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                handleShowLocuri(null);});
            return row;
        });*/
    }

    private void initModel() {
        modelProba.setAll();
        modelProba.addAll(service.getAllProbe());

        modelVarstaMin.setAll();
        modelVarstaMin.addAll(service.setAllVarste());

        modelVarstaMax.setAll();
        modelVarstaMax.addAll(service.setAllVarste());

        modelVarsta.setAll();
        modelVarsta.addAll(service.setAllVarste());
    }

    public void handleCauta(ActionEvent actionEvent) {

        String numeProba = ProbaField.getText();
        int varstaMin = VarstaMinCombo.getSelectionModel().getSelectedItem();
        int varstaMax = VarstaMaxCombo.getSelectionModel().getSelectedItem();

        System.out.println(numeProba);
        System.out.println(varstaMin);
        System.out.println(varstaMax);

        if(Objects.equals(numeProba, ""))
        {
            modelProba.setAll();
            modelProba.addAll(service.getAllProbe());
        }
        else{
            List<Proba> filtered = service.getAllProbe().stream().filter(proba -> Objects.equals(proba.getNume(), numeProba) && proba.getVarsta_min() == varstaMin && proba.getVarsta_max() == varstaMax).toList();

            modelProba.setAll();
            modelProba.addAll(filtered);

            ProbaField.clear();
        }
    }

    public void handleShowCopii(MouseEvent mouseEvent) {
        Proba proba = ProbeTableView.getSelectionModel().getSelectedItem();
            if(proba != null) {
                modelCopil.setAll();
                modelCopil.addAll(service.findAllCopiiInscrisi(proba.getID()));

                CopiiTableView.setItems(modelCopil);
            }

    }

    public void handleInregistreaza(ActionEvent actionEvent) {

        Long idProba = ProbeTableView.getSelectionModel().getSelectedItem().getID();

        String numeCopil = NumeField.getText();
        int varstaCopil = VarstaCombo.getSelectionModel().getSelectedItem();

        Copil c = service.findCopil(numeCopil, varstaCopil);
        if(c == null){
            service.addCopil(numeCopil, varstaCopil);
            c = service.findCopil(numeCopil, varstaCopil);
        }

        if(service.findInregistrare(c.getID()) >= 2)
            RegisterBox.setText("Nu s a putut face inregistrarea!");
        else
            service.addInregistrare(c.getID(), idProba);

        modelCopil.setAll();
        modelCopil.addAll(service.findAllCopiiInscrisi(idProba));

    }

    public void handleLogOut(ActionEvent actionEvent) {
        try{
            URL fxmlLocation = HelloApplication.class.getResource("views/Login.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login Page");
            LoginController userController = fxmlLoader.getController();
            userController.setService(service);
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
