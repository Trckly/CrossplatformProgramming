//package org.athleteManager;
//
//import javafx.application.Application;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//import java.io.*;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Main extends Application {
//    private ObservableList<Athlete> athleteList = FXCollections.observableArrayList();
//
//    @Override
//    public void start(Stage primaryStage) {
//        // Create TableView
//        TableView<Athlete> tableView = new TableView<>();
//
//        // Create columns
//        TableColumn<Athlete, String> firstNameCol = new TableColumn<>("First Name");
//        firstNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFirstName()));
//
//        TableColumn<Athlete, String> lastNameCol = new TableColumn<>("Last Name");
//        lastNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLastName()));
//
//        TableColumn<Athlete, String> sportCol = new TableColumn<>("Sport");
//        sportCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSport()));
//
//        TableColumn<Athlete, Integer> medalsCol = new TableColumn<>("Medals");
//        medalsCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getMedalsCount()).asObject());
//
//        tableView.getColumns().addAll(firstNameCol, lastNameCol, sportCol, medalsCol);
//        tableView.setItems(athleteList);
//
//        // Input fields
//        TextField firstNameField = new TextField();
//        firstNameField.setPromptText("First Name");
//
//        TextField lastNameField = new TextField();
//        lastNameField.setPromptText("Last Name");
//
//        TextField sportField = new TextField();
//        sportField.setPromptText("Sport");
//
//        TextField medalsField = new TextField();
//        medalsField.setPromptText("Medals Count");
//
//        List<Athlete> newAthletes = PlainFileIn();
//
//        System.out.println(newAthletes.size());
//
//        // Add button
//        Button addButton = new Button("Add Athlete");
//        addButton.setMinWidth(200);
//        addButton.setOnAction(event -> {
//            String firstName = firstNameField.getText();
//            String lastName = lastNameField.getText();
//            String sport = sportField.getText();
//
//            if(firstName.isEmpty() || lastName.isEmpty() || sport.isEmpty()) {
//                return;
//            }
//
//            String medalsStr = medalsField.getText();
//            if(medalsStr.isEmpty())
//                return;
//
//            int medals = Integer.parseInt(medalsStr);
//
//            Athlete newAthlete = new Athlete(firstName, lastName, sport, medals);
//            athleteList.add(newAthlete);
//
//            PlainFileOut(newAthlete);
//
//            // Clear fields
//            firstNameField.clear();
//            lastNameField.clear();
//            sportField.clear();
//            medalsField.clear();
//        });
//
//        // Delete button
//        Button deleteButton = new Button("Delete Selected Athlete");
//        deleteButton.setMinWidth(200);
//        deleteButton.setOnAction(event -> {
//            Athlete selectedAthlete = tableView.getSelectionModel().getSelectedItem();
//            if (selectedAthlete != null) {
//                athleteList.remove(selectedAthlete);
//            }
//        });
//
//        // Layout setup
//        VBox mainLayout = new VBox(10);
//
//        StackPane tablePane = new StackPane(tableView);
//
//        VBox inputBox = new VBox(10, firstNameField, lastNameField, sportField, medalsField);
//
//        HBox buttonsBox = new HBox(50, addButton, deleteButton);
//        buttonsBox.setAlignment(Pos.CENTER);
//
//        mainLayout.getChildren().addAll(tablePane, inputBox, buttonsBox);
//
//
//        Scene scene = new Scene(mainLayout, 800, 500);
//
//        primaryStage.setTitle("Athlete Manager");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
