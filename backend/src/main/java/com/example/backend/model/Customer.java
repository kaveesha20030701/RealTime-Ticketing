package com.example.backend.model;

public class Customer implements Runnable {
    private final int customerId;
    private final int ticketsToRetrieve;
    private final TicketPool ticketPool;

    public Customer(int customerId, int ticketsToRetrieve, TicketPool ticketPool) {
        this.customerId = customerId;
        this.ticketsToRetrieve=ticketsToRetrieve;
        this.ticketPool = ticketPool;

    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Attempt to retrieve a ticket
                String ticket = ticketPool.removeTicket(customerId);

                // Log the retrieval action
                if (ticket != null) {
                    System.out.println("Customer " + customerId + " retrieved " + ticket + ".");
                } else {
                    System.out.println("Customer " + customerId + " found no tickets available.");
                }

                // Wait before trying to retrieve another ticket
                Thread.sleep(1000*ticketsToRetrieve);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Customer " + customerId + " interrupted and stopped.");
        } catch (Exception e) {
            System.out.println("Customer " + customerId + " encountered an error: " + e.getMessage());
        }
    }
}
