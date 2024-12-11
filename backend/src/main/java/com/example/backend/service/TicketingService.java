package com.example.backend.service;


import com.example.backend.model.Configuration;
import com.example.backend.model.Customer;
import com.example.backend.model.Vendor;
import com.example.backend.model.TicketPool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.backend.model.Logger;

import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;


@Service
public class TicketingService {

    private TicketPool ticketPool;
    private AtomicBoolean simulationRunning = new AtomicBoolean(false);

    public void startSimulation(Configuration config) {
        try{
            FileWriter writer = new FileWriter("status.txt", false);
            writer.close(); // Close the writer, leaving the file empty
        }catch (Exception e){}
        if (simulationRunning.get()) {
            throw new IllegalStateException("Simulation is already running");
        }
        Logger logger = new Logger("system_logs.txt");
        ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets(), config.getNumVendors());
        simulationRunning.set(true);

        // Start vendors and customers
        startVendorThreads(config);
        startCustomerThreads(config);
    }

    private void startVendorThreads(Configuration config) {
        Thread[] vendorThreads = new Thread[config.getNumVendors()];
        for (int i = 0; i < config.getNumVendors(); i++) {
            Vendor vendor = new Vendor(i + 1, config.getTicketReleaseRate(), ticketPool);
            vendorThreads[i] = new Thread(vendor);
            vendorThreads[i].start();
        }
    }

    private void startCustomerThreads(Configuration config) {
        Thread[] customerThreads = new Thread[config.getNumCustomers()];
        for (int i = 0; i < config.getNumCustomers(); i++) {
            Customer customer = new Customer(i + 1, config.getCustomerRetrievalRate(), ticketPool);
            customerThreads[i] = new Thread(customer);
            customerThreads[i].start();
        }
    }

    public String getStatus() {
        // Return the status of the simulation
        return simulationRunning.get() ? "Running" : "Not running";
    }

    public void stopSimulation() {
        try{
            FileWriter writer = new FileWriter("status.txt", false);
            writer.close(); // Close the writer, leaving the file empty
        }catch (Exception e){}
        
        if (!simulationRunning.get()) {
            throw new IllegalStateException("Simulation is not running");
        }
        simulationRunning.set(false);
        ticketPool.terminate(); // Stop the ticket pool
    }

    public ResponseEntity<?> getStatusNote() {
        StringBuilder logContent = new StringBuilder();



        try (BufferedReader reader = new BufferedReader(new FileReader("status.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logContent.append(line).append(System.lineSeparator());

            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
        }
        return ResponseEntity.ok(logContent.toString());
    }
}

