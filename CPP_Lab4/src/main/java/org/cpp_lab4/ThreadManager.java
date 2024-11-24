package org.cpp_lab4;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;

public class ThreadManager {
    private final ScheduledThreadPoolExecutor executor;
    private final Semaphore semaphore;
    private final Queue<String> fileQueue;

    public ThreadManager(int maxThreads, int maxActiveTasks) {
        this.executor = new ScheduledThreadPoolExecutor(maxThreads);
        this.semaphore = new Semaphore(maxActiveTasks);
        this.fileQueue = new ConcurrentLinkedQueue<>();
    }

    public void processFileAtFixedRate(List<String> fileNames, long initialDelay, long period, TimeUnit timeUnit, VBox threadPanel, List<TaskResult> results) {
        fileQueue.addAll(fileNames);

        executor.scheduleAtFixedRate(() -> {
            while (!fileQueue.isEmpty()) {
                try {
                    semaphore.acquire();

                    var fileName = fileQueue.poll();

                    long startTime = System.currentTimeMillis();
                    long threadId = Thread.currentThread().threadId();

                    TaskPane taskPane = new TaskPane(fileName);
                    Platform.runLater(() -> {
                        taskPane.setThreadIdLabel(threadId);
                        taskPane.updateStatus("Processing");
                        threadPanel.getChildren().add(taskPane);
                    });

                    Thread.sleep(new java.util.Random().nextInt(4000) + 1000);

                    long endTime = System.currentTimeMillis();
                    Platform.runLater(() -> {
                        taskPane.updateStatus("Completed in " + (endTime - startTime) + " ms");
                    });

                    TaskResult taskResult = new TaskResult(fileName, startTime, endTime);
                    synchronized (results) {
                        results.add(taskResult);
                        results.notifyAll();
                    }

                    writeResultToFile(taskResult, threadId);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    semaphore.release();
                }
            }
        }, initialDelay, period, timeUnit);
    }

    public void shutdown() {
        executor.shutdown();
    }

    public void writeResultToFile(TaskResult result, long threadId) {
        String fileName = "result_" + result.getFileName().replaceAll("[^a-zA-Z0-9]", "_") + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            writer.write("File: " + result.getFileName() + "\n");
            writer.write("Thread ID: " + threadId + "\n");
            writer.write("Start Time: " + result.getStartTime() + " ms\n");
            writer.write("End Time: " + result.getEndTime() + " ms\n");
            writer.write("Duration: " + (result.getEndTime() - result.getStartTime()) + " ms\n");
            writer.write("Date Executed: " + LocalDateTime.now().format(formatter) + "\n\n");
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Write file failure: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
