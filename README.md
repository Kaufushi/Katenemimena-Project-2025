# Katenemimena-Project-2025

StreetFoodGo

StreetFoodGo είναι μία Spring Boot εφαρμογή που παρέχει λειτουργίες αναζήτησης εστιατορίων, καθώς και REST API για κατανάλωση από άλλες εφαρμογές (π.χ. frontend ή mobile).

Technologies

- Java 21

- Spring Boot

- Spring Web MVC

- Spring Data JPA

- Spring Security

- Thymeleaf

- OpenAPI 3

- Swagger UI

REST API Documentation

Η εφαρμογή παρέχει REST API για την αναζήτηση εστιατορίων.
Η τεκμηρίωση του API υλοποιείται με Swagger / OpenAPI 3 μέσω του springdoc-openapi.

Η τεκμηρίωση παράγεται αυτόματα από τον πηγαίο κώδικα και παραμένει συγχρονισμένη με τα όλα τα διαθέσιμα endpoints.

Swagger UI

Μετά την εκκίνηση της εφαρμογής, η τεκμηρίωση είναι διαθέσιμη στη διεύθυνση:

http://localhost:8080/swagger-ui/index.html


Μέσω του Swagger UI μπορούμε να:

- Δούμε όλα τα REST endpoints,

- Εξετάσουμε query parameters,

- Δούμε τα JSON responses και

- Εκτελέσουμε δοκιμαστικές κλήσεις

API Endpoints

GET /api/restaurants

Επιστρέφει λίστα εστιατορίων σε μορφή JSON.


Example Request: 
GET /api/restaurants?area=Αθήνα&cuisine=Ιταλικό

Response

- Status Code: 200 OK

- Body: Λίστα αντικειμένων RestaurantDto σε μορφή JSON

Configuration

Η γενική ρύθμιση του API (τίτλος, έκδοση, περιγραφή) γίνεται στην κλάση:
OpenApiConfig


Η τεκμηρίωση των endpoints υλοποιείται στον controller
RestaurantApiController
μέσω OpenAPI annotations (@Operation, @Parameter, @ApiResponse).

Run the Application

mvn spring-boot:run


ή μέσω του IDE (IntelliJ / Eclipse).

Development Notes

- Το REST API είναι ανεξάρτητο από τα MVC controllers

- Η τεκμηρίωση ενημερώνεται αυτόματα με κάθε αλλαγή στον controller

- Το Swagger UI χρησιμοποιείται ως εργαλείο δοκιμών κατά την ανάπτυξη

Academic Context

Το project υλοποιήθηκε στο πλαίσιο πανεπιστημιακής εργασίας και ακολουθεί σύγχρονες πρακτικές ανάπτυξης RESTful APIs και τεκμηρίωσης.
