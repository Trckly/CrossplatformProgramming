package org.athleteManager;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class AthleteTableBuilder {
    public static TableView<Athlete> buildAthleteTable(ObservableList<Athlete> athleteObservableList) {
        // Create TableView
        TableView<Athlete> tableView = new TableView<>();

        // Create columns
        TableColumn<Athlete, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFirstName()));

        TableColumn<Athlete, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLastName()));

        TableColumn<Athlete, String> sportCol = new TableColumn<>("Sport");
        sportCol.setCellValueFactory(data -> {
            String sport = data.getValue().getSport();
            return new SimpleStringProperty(sport != null ? sport : "null");
        });

        TableColumn<Athlete, String> medalsCol = new TableColumn<>("Medals");
        medalsCol.setCellValueFactory(data -> {
            Integer medalsCount = data.getValue().getMedalsCount();
            return new SimpleStringProperty(medalsCount != null ? medalsCount.toString() : "null");
        });

        tableView.getColumns().addAll(firstNameCol, lastNameCol, sportCol, medalsCol);
        tableView.setItems(athleteObservableList);

        return tableView;
    }

    public static void updateAthleteTable(TableView<Athlete> tableToUpdate, ObservableList<Athlete> athleteObservableList){
        tableToUpdate.getItems().clear();

        tableToUpdate.setItems(athleteObservableList);
    }
}
