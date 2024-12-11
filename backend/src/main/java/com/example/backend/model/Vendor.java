package com.example.backend.model;

public class Vendor implements Runnable {
    private final int vendorId;
    private final int ticketsPerRelease;
    private final TicketPool ticketPool;


    public Vendor(int vendorId, int ticketsPerRelease, TicketPool ticketPool) {
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.ticketPool = ticketPool;

    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Add tickets to the pool
                ticketPool.addTickets(vendorId);


                if (Thread.currentThread().isInterrupted())
                    break;

                // Wait before adding tickets again
                Thread.sleep(1000*ticketsPerRelease);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}