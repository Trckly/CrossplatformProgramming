package org.cpp_lab4;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class MainViewController {
    @FXML
    private TextField threadCountField;
    @FXML
    private TextField maxActiveTasksField;
    @FXML
    private TextField filesAmountField;
    @FXML
    private Button startButton;
    @FXML
    private VBox threadPanel;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label totalTimeLabel;

    private ThreadManager threadManager;
    private long startTime;

    @FXML
    public void initialize() {
        startButton.setOnAction(event -> startProcessing());
    }

    private void startProcessing() {
        var threadCountString = threadCountField.getText();
        var maxActiveTasksString = maxActiveTasksField.getText();
        var filesAmountStr = filesAmountField.getText();

        var uintRegex = "^\\d+$";
        if (!threadCountString.matches(uintRegex) || !maxActiveTasksString.matches(uintRegex) ||
                !filesAmountStr.matches(uintRegex)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid number");
            alert.showAndWait();
            return;
        }

        int threadCount = Integer.parseInt(threadCountString);
        int maxActiveTasks = Integer.parseInt(maxActiveTasksString);
        int filesAmount = Integer.parseInt(filesAmountStr);
        threadManager = new ThreadManager(threadCount, maxActiveTasks);

        List<String> files = generateDummyFiles(filesAmount);

        threadPanel.getChildren().clear();
        totalTimeLabel.setText("Total time: Calculating...");
        startTime = System.currentTimeMillis();

        List<TaskResult> results = Collections.synchronizedList(new ArrayList<>());

        int chunkSize = (int) Math.ceil((double) files.size() / threadCount);
        for (int i = 0; i < threadCount; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, files.size());

            List<String> thisThreadFiles = files.subList(start, end);

            threadManager.processFileAtFixedRate(
                    thisThreadFiles,
                    500,
                    500,
                    TimeUnit.MILLISECONDS,
                    threadPanel,
                    results
            );
        }

        new Thread(() -> {
            synchronized (results) {
                while (results.size() < files.size()) {
                    try {
                        results.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }

            // Calculate the total time
            long totalEndTime = results.stream()
                    .mapToLong(TaskResult::getEndTime)
                    .max()
                    .orElse(System.currentTimeMillis());

            long totalTime = totalEndTime - startTime;

            // Update the UI on the JavaFX Application thread
            Platform.runLater(() -> {
                totalTimeLabel.setText("Total time: " + totalTime + " ms");
                scrollPane.setVvalue(1.0);  // Auto-scroll to the bottom of the ScrollPane
            });

            threadManager.shutdown();
        }).start();
    }

    private List<String> generateDummyFiles(int count) {
        List<String> files = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            files.add("File_" + i + ".log");
        }
        return files;
    }
}

