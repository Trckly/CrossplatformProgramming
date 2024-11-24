package org.cpp_lab4;

public class TaskResult {
    private final String fileName;
    private final long startTime;
    private final long endTime;

    public TaskResult(String fileName, long startTime, long endTime) {
        this.fileName = fileName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getFileName() {
        return fileName;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
