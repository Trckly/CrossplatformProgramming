package org.athleteManager;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.io.AthleteIoManager;
import org.javaNative.AthleteNativeManager;
import org.json.AthleteJsonManager;
import org.yaml.AthleteYamlManager;

public class Main extends Application {
    private ObservableList<Athlete> athleteIOList = FXCollections.observableArrayList();

    private ObservableList<Athlete> athleteNativeList = FXCollections.observableArrayList();

    private ObservableList<Athlete> athleteJsonList = FXCollections.observableArrayList();

    private ObservableList<Athlete> athleteYamlList = FXCollections.observableArrayList();


    @Override
    public void start(Stage primaryStage) {

        athleteIOList = AthleteIoManager.readAthleteData("athlete_io_data.txt");
        TableView<Athlete> ioTableView = AthleteTableBuilder.buildAthleteTable(athleteIOList);

        athleteNativeList = AthleteNativeManager.readAthleteData("athlete_native_data.dat");
        TableView<Athlete> nativeTableView = AthleteTableBuilder.buildAthleteTable(athleteNativeList);

        athleteJsonList = AthleteJsonManager.readAthleteData("athlete_data.json");
        TableView<Athlete> jsonTableView = AthleteTableBuilder.buildAthleteTable(athleteJsonList);

        athleteYamlList = AthleteYamlManager.readAthleteData("athlete_data.yaml");
        TableView<Athlete> yamlTableView = AthleteTableBuilder.buildAthleteTable(athleteYamlList);

        // Input fields
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        TextField sportField = new TextField();
        sportField.setPromptText("Sport");

        TextField medalsField = new TextField();
        medalsField.setPromptText("Medals Count");

        TextField ageField = new TextField();
        ageField.setPromptText("Age");

        // Add button
        Button addButton = new Button("Add Athlete");
        addButton.setMinWidth(200);
        addButton.setOnAction(event -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String sport = sportField.getText();
            String medalsStr = medalsField.getText();
            String ageStr = ageField.getText();

            if(firstName.isEmpty() || lastName.isEmpty() || sport.isEmpty() || medalsStr.isEmpty() || ageStr.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Empty Fields");
                alert.setContentText("Please ensure that all input fields are not empty");
                alert.showAndWait();

                return;
            }

            int medals;
            int age;
            try {
                medals = Integer.parseInt(medalsStr);
                age = Integer.parseInt(ageStr);
                if(medals < 0 || age < 0)
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Invalid Medals Count or Age!");
                alert.setContentText("Medals Count and Age must be a positive whole numeric value");
                alert.showAndWait();

                return;
            }

            Athlete newAthlete = new Athlete(firstName, lastName, sport, medals, age);

            AthleteIoManager.writeAthleteData(newAthlete, "athlete_io_data.txt");
            athleteIOList = AthleteIoManager.readAthleteData("athlete_io_data.txt");
            AthleteTableBuilder.updateAthleteTable(ioTableView, athleteIOList);

            AthleteNativeManager.writeAthleteData(newAthlete, "athlete_native_data.dat");
            athleteNativeList = AthleteNativeManager.readAthleteData("athlete_native_data.dat");
            AthleteTableBuilder.updateAthleteTable(nativeTableView, athleteNativeList);

            AthleteJsonManager.writeAthleteData(newAthlete, "athlete_data.json");
            athleteJsonList = AthleteJsonManager.readAthleteData("athlete_data.json");
            AthleteTableBuilder.updateAthleteTable(jsonTableView, athleteJsonList);

            AthleteYamlManager.writeAthleteData(newAthlete, "athlete_data.yaml");
            athleteYamlList = AthleteYamlManager.readAthleteData("athlete_data.yaml");
            AthleteTableBuilder.updateAthleteTable(yamlTableView, athleteYamlList);

            // Clear fields
            firstNameField.clear();
            lastNameField.clear();
            sportField.clear();
            medalsField.clear();
        });

        // Layout setup
        VBox mainLayout = new VBox(10);

        TabPane tabPane = new TabPane();

        Tab ioTableTab = new Tab("IO Table");
        ioTableTab.setContent(ioTableView);
        Tab nativeTableTab = new Tab("Native Table");
        nativeTableTab.setContent(nativeTableView);
        Tab jsonTableTab = new Tab("JSON Table");
        jsonTableTab.setContent(jsonTableView);
        Tab yamlTableTab = new Tab("YAML Table");
        yamlTableTab.setContent(yamlTableView);

        tabPane.getTabs().addAll(ioTableTab, nativeTableTab, jsonTableTab, yamlTableTab);

        VBox inputBox = new VBox(10, firstNameField, lastNameField, sportField, medalsField, ageField, addButton);
        inputBox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(inputBox);

        mainLayout.getChildren().addAll(tabPane, borderPane);


        Scene scene = new Scene(mainLayout, 800, 500);

        primaryStage.setTitle("Athlete Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
