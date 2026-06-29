package org.example.exception;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger {
    private final File logDir;
    private final File logFile;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public FileLogger() {
        String home = System.getProperty("user.home");
        this.logDir = new File(home + "/Desktop/app-logs");
        this.logFile = new File(logDir, "app.log");
    }

    public void log(String level, String message) {
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        String line = LocalDateTime.now().format(FMT) + "[" + level + "] " + message;

        try (FileWriter fw = new FileWriter(logFile, true)) {
            fw.write(line + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getLogFilePath() {
        return logFile.getAbsolutePath();
    }
}
