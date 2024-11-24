package org.cpp_lab4;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


public class TaskPane extends HBox {
    private final Label statusLabel;
    private final Label threadIdLabel;

    public TaskPane(String file) {
        Label fileLabel = new Label(file);
        this.threadIdLabel = new Label("----");
        this.statusLabel = new Label("Waiting");

        this.setSpacing(20);
        this.getChildren().addAll(threadIdLabel, fileLabel, statusLabel);
    }

    public void updateStatus(String status) {
        Platform.runLater(() -> statusLabel.setText(status));
    }

    public void setThreadIdLabel(long threadId) {
        Platform.runLater(() -> threadIdLabel.setText(Long.toString(threadId)));
    }
}
