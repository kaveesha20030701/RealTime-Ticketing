package com.example.backend.controller;


import com.example.backend.model.Configuration;
import com.example.backend.service.TicketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin("*")
public class Controller {

    @Autowired
    private TicketingService ticketingService;

    @PostMapping("/startSimulation")
    public void startSimulation(@RequestBody Configuration config) {
        ticketingService.startSimulation(config);
    }

    @GetMapping("/status")
    public String getStatus() {
        return ticketingService.getStatus();
    }

    @PostMapping("/stopSimulation")
    public void stopSimulation() {
        ticketingService.stopSimulation();
    }
    @GetMapping(path = "status-note")
    public ResponseEntity<?>getStatusNote(){
        return  ticketingService.getStatusNote();
    }
}
