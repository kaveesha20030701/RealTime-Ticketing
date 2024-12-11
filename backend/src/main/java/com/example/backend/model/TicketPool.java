package com.example.backend.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final int maxCapacity;
    private final int numVendors;
    private final int ticketCount;
    private final Queue<String> tickets = new LinkedList<>();
    private static final String LOG_FILE_PATH = "ticket_pool_log.txt";
    private boolean isTerminated = false; // Flag to indicate termination

    public TicketPool(int maxCapacity,int ticketCount,int numVendors) {
        this.maxCapacity = maxCapacity;
        this.ticketCount = ticketCount;
        this.numVendors = numVendors;
    }

    // Add tickets to the pool
    public synchronized void addTickets(int vendorId) {

        while (tickets.size()  > maxCapacity && !isTerminated) {
            try {
                log("Vendor " + vendorId + " is waiting as the pool is full. Current tickets: " + tickets.size());
                wait(); // Wait until there's room in the pool
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log("Vendor " + vendorId + " interrupted.");
                return;
            }
        }

        if (isTerminated) return; // Exit if the system is terminating

        for (int i = 0; i < ticketCount; i++) {
            tickets.add("Ticket-" + (tickets.size() + 1));
        }
        int ticketsPerVendor = ticketCount / numVendors;
        int remainingTickets = ticketCount % numVendors;
        int ticketCount = ticketsPerVendor+(remainingTickets-->0?1:0);

        log("Vendor " + vendorId + " added " + ticketCount + " tickets. Total tickets: " + tickets.size());

//                if doesn't exist create new file
        try{
            File logFile = new File("status.txt");
            if (!logFile.exists()) {
                if (logFile.createNewFile()) {
                    System.out.println("File created: " + logFile.getName());
                } else {
                    System.out.println("Failed to create the file.");
                }
            }
        }catch (Exception e){}



        try (FileWriter writer = new FileWriter("status.txt", true)) {
            writer.write("Vendor " + vendorId + " added " + ticketCount + " tickets. Total tickets: " + tickets.size() +  System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
        notifyAll(); // Notify waiting customers that tickets are available
    }

    // Remove a ticket from the pool
    public synchronized String removeTicket(int customerId) {
        while (tickets.isEmpty() && !isTerminated) {
            try {
                log("Customer " + customerId + " is waiting as there are no tickets available.");
                wait(); // Wait until tickets are added
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log("Customer " + customerId + " interrupted.");
                return null;
            }
        }

        if (isTerminated) return null; // Exit if the system is terminating

        String ticket = tickets.poll(); // Retrieve and remove the ticket
        log("Customer " + customerId + " retrieved " + ticket + ". Remaining tickets: " + tickets.size());


//                if doesn't exist create new file
        try{
            File logFile = new File("status.txt");
            if (!logFile.exists()) {
                if (logFile.createNewFile()) {
                    System.out.println("File created: " + logFile.getName());
                } else {
                    System.out.println("Failed to create the file.");
                }
            }
        }catch (Exception e){}



        try (FileWriter writer = new FileWriter("status.txt", true)) {
            writer.write("Customer " + customerId + " retrieved " + ticket + ". Remaining tickets: " + tickets.size() +  System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
        notifyAll(); // Notify waiting vendors that space is available
        return ticket;
    }

    // Method to check if the pool is empty
    public synchronized boolean isEmpty() {
        return tickets.isEmpty();
    }

    // Method to terminate the system
    public synchronized void terminate() {
        isTerminated = true; // Set the termination flag
        log("TicketPool is terminating. No further actions will be processed.");
        notifyAll(); // Notify all waiting threads to exit
    }

    // Method to check if the system is terminated
    public synchronized boolean isTerminated() {
        return isTerminated;
    }

    // Log messages to the console and a file
    private synchronized void log(String message) {
        String logMessage = "[" + java.time.LocalDateTime.now() + "] " + message;

        // Console logging
        System.out.println(logMessage);

        // File logging
        try (FileWriter fw = new FileWriter(LOG_FILE_PATH, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(logMessage);
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }
}
