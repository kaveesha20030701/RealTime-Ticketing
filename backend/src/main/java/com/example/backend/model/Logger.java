package com.example.backend.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private BufferedWriter writer;

    public Logger(String fileName) {
        try {
            writer = new BufferedWriter(new FileWriter(fileName, true)); // Append mode
        } catch (IOException e) {
            System.out.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    public synchronized void log(String message) {
        System.out.println(message); // Console logging

        // File logging
        if (writer != null) {
            try {
                writer.write(message);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                System.out.println("Failed to write to log file: " + e.getMessage());
            }
        }
    }
}