package com.example.concurscopii;

import com.example.concurscopii.controller.LoginController;
import com.example.concurscopii.domain.Copil;
import com.example.concurscopii.domain.Inscriere;
import com.example.concurscopii.domain.Organizator;
import com.example.concurscopii.domain.Proba;
import com.example.concurscopii.repository.Repository;
import com.example.concurscopii.repository.db.CopilDbRepository;
import com.example.concurscopii.repository.db.InscriereDbRepository;
import com.example.concurscopii.repository.db.OrganizatorDbRepository;
import com.example.concurscopii.repository.db.ProbaDbRepository;
import com.example.concurscopii.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class HelloApplication extends Application {

    Repository<String, Organizator> RepoOrganizator;
    Repository<Long, Copil> RepoCopil;
    Repository<Long, Inscriere> RepoInscriere;
    Repository<Long, Proba> RepoProba;

    Service service;
    @Override
    public void start(Stage primaryStage) throws IOException {


        Properties props=new Properties();
        try {
            props.load(new FileReader("C:\\Users\\xdber\\IdeaProjects\\MPP\\mpp-proiect-java-XDBerry29\\ConcursCopii\\bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }


        RepoOrganizator = new OrganizatorDbRepository(props);
        RepoCopil = new CopilDbRepository(props);
        RepoInscriere = new InscriereDbRepository(props);
        RepoProba = new ProbaDbRepository(props);

        service = new Service(RepoOrganizator, RepoCopil, RepoInscriere, RepoProba);
        loginView(primaryStage);
        primaryStage.show();


    }

    private void loginView(Stage stage) throws IOException {

        URL fxmlLocation = HelloApplication.class.getResource("views/Login.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        AnchorPane Layout = fxmlLoader.load();
        stage.setScene(new Scene(Layout));
        stage.setTitle("Login Page");

        LoginController userController = fxmlLoader.getController();
        userController.setService(service);
    }


    public static void main(String[] args) {
        launch(args);
        /*
        Repository<Long, Copil> copilRepository = new CopilDbRepository(props);

        copilRepository.add(new Copil("Alin", 15));*/
    }
}