# Pharmacy Management System

Το Pharmacy Management System είναι ένα REST API που αναπτύχθηκε με Spring Boot και PostgreSQL για τη διαχείριση της αποθήκης φαρμάκων ενός δημόσιου νοσοκομείου. 
Το περιβάλλον ανάπτυξης της εφαρμοτής είναι το Intellij IDEA. 

## Χρήση Τεχνητής Νοημοσύνης

Για την ανάπτυξη αυτού του έργου χρησιμοποιήθηκαν εργαλεία τεχνητής 
νοημοσύνης (Claude AI, ChatGPT) ως βοηθητικά μέσα για εκπαιδευτικούς 
σκοπούς. Η χρήση των εργαλείων αυτών περιλάμβανε:

- Υποστήριξη στην κατανόηση τεχνολογιών Spring Boot και JPA
- Βοήθεια στη διαμόρφωση βέλτιστων πρακτικών (best practices)
- Debugging και επίλυση τεχνικών προβλημάτων

Όλος ο κώδικας έχει επανεξεταστεί, κατανοηθεί και προσαρμοστεί σύμφωνα με τις απαιτήσεις του έργου. 

Αυτό το project βρίσκεται σε **ενεργή ανάπτυξη** και **δεν είναι πλήρως ολοκληρωμένο**.

## Λειτουργίες
 
- Προβολή/Δημιουργια φαρμάκων  
- Καταγραφή κινήσεων αποθήκης (εισαγωγή/εξαγωγή)

## Δομή του πρότζεκτ

```
pharmacy/
├── docs/
│   ├── API-Documentation.md           # Πλήρης API documentation
│   ├── ER-Diagram.png                 # Entity-Relationship Diagram
│   └── UML-Diagram.png                # UML Class Diagram
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/
│   │   │       └── eirzarog/
│   │   │           └── pharmacy/
│   │   │               │
│   │   │               ├── controllers/
│   │   │               │   ├── DrugCategoryController.java
│   │   │               │   ├── DrugController.java
│   │   │               │   ├── StaffController.java
│   │   │               │   └── StockMovementController.java
│   │   │               │
│   │   │               ├── entities/
│   │   │               │   ├── criteria/
│   │   │               │   │   ├── DrugSearchCriteria.java
│   │   │               │   │   └── StockMovementSearchCriteria.java
│   │   │               │   │
│   │   │               │   ├── dtos/
│   │   │               │   │   ├── ApiResponse.java
│   │   │               │   │   ├── CreateDrugRequest.java
│   │   │               │   │   ├── CreateStockMovementRequest.java
│   │   │               │   │   ├── DrugCategoryDTO.java
│   │   │               │   │   ├── DrugDTO.java
│   │   │               │   │   ├── StaffDTO.java
│   │   │               │   │   └── StockMovementDTO.java
│   │   │               │   │
│   │   │               │   ├── Drug.java
│   │   │               │   ├── DrugCategory.java
│   │   │               │   ├── Staff.java
│   │   │               │   └── StockMovement.java
│   │   │               │
│   │   │               ├── exceptions/
│   │   │               │   ├── DuplicateResourceException.java
│   │   │               │   ├── ErrorMessage.java
│   │   │               │   ├── InsufficientStockException.java
│   │   │               │   ├── PharmacyException.java
│   │   │               │   ├── PharmacyExceptionHandler.java
│   │   │               │   └── ResourceNotFoundException.java
│   │   │               │
│   │   │               ├── repositories/
│   │   │               │   ├── DrugCategoryRepository.java
│   │   │               │   ├── DrugRepository.java
│   │   │               │   ├── StaffRepository.java
│   │   │               │   └── StockMovementRepository.java
│   │   │               │
│   │   │               ├── services/
│   │   │               │   ├── DrugCategoryService.java
│   │   │               │   ├── DrugService.java
│   │   │               │   ├── StaffService.java
│   │   │               │   └── StockMovementService.java
│   │   │               │
│   │   │               └── PharmacyApplication.java
│   │   │
│   │   └── resources/
│   │       ├── database/
│   │       │   ├── queries.sql
│   │       │   ├── sample-data.sql
│   │       │   └── schema.sql
│   │       │
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│           └── org/
│               └── eirzarog/
│                   └── pharmacy/
│                       └── PharmacyApplicationTests.java
│
├── mvnw
├── mvnw.cmd
└── pom.xml
```
