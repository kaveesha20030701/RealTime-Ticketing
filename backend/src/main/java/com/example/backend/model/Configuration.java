package com.example.backend.model;


import lombok.Getter;
import lombok.Setter;

public class Configuration {
    @Getter
    @Setter
    private int totalTickets;
    @Setter
    @Getter
    private int ticketReleaseRate;
    @Setter
    @Getter
    private int customerRetrievalRate;
    @Setter
    @Getter
    private int maxTicketCapacity;
    @Setter
    @Getter
    private int numVendors;
    @Setter
    @Getter
    private int numCustomers;

    // Constructor, Getters and Setters
    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity,int numVendors,int numCustomers) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
        this.numVendors = numVendors;
        this.numCustomers = numCustomers;
    }


}

