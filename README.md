# Ticketing System Backend

This is the backend for the Ticketing System simulation project. It is built with **Spring Boot** and provides RESTful APIs for managing ticketing operations, including simulation start, status checks, and termination. The backend interacts with a frontend built using React.

---

## Features
- Configure simulation parameters (total tickets, release rate, retrieval rate, etc.)
- Manage vendors and customers during the ticketing simulation
- Provide real-time status of the simulation via APIs
- Support for starting and stopping simulations

---

## Folder Structure
src/main/java/com/example/backend
├── config
│   └── Configuration.java
├── controller
│   └── TicketingController.java
├── model
│   ├── Customer.java
│   ├── TicketPool.java
│   └── Vendor.java
├── repository
│   └── TicketRepository.java
├── service
│   └── TicketingService.java
└── CourseworkApplication.java

## Prerequisites
1. **Java Development Kit (JDK)**: Version 11 or higher.
2. **Maven**: For building and running the application.
3. **Spring Boot**: Embedded in the project via Maven dependencies.
4. **Postman** or any API testing tool (optional, for testing APIs).

Navigate to the project directory:
        cd ticketing-system-backend

Build the project:
        mvn clean install

Run the application:
        mvn spring-boot:run

The server will be available at:
        http://localhost:8080


API Endpoints
Base URL: http://localhost:8080/api/tickets
    Start Simulation
        Endpoint: /startSimulation
        Method: POST
        Request Body:
    {
      "totalTickets": 100,
      "ticketReleaseRate": 5,
      "customerRetrievalRate": 2,
      "maxTicketCapacity": 50
    }
    Description: Starts the ticketing simulation with the given configuration.

Check Status
    Endpoint: /status
    Method: GET
    Response:
        {
          "status": "Running",
          "ticketsRemaining": 40
        }
        Description: Fetches the current status of the simulation.
    Stop Simulation
        Endpoint: /stopSimulation
        Method: POST
        Description: Stops the simulation and terminates all threads.

Technologies Used
Java: Core programming language
Spring Boot: Backend framework
Maven: Build automation tool
REST APIs: For frontend-backend communication

How to Test
    Use Postman or cURL to send HTTP requests to the provided endpoints.
    Example cURL request to start the simulation:
    

curl -X POST -H "Content-Type: application/json" -d '{
    "totalTickets": 100,
    "ticketReleaseRate": 5,
    "customerRetrievalRate": 2,
    "maxTicketCapacity": 50
}' http://localhost:8080/api/tickets/startSimulation


Future Enhancements
    Add database integration for persistent storage of tickets and configurations.
    Implement more detailed logging and monitoring.
    Enhance error handling for invalid inputs and unexpected scenarios.
