# Pharmacy-Management
Layered, object-oriented Java application that manages pharmacy operations, including patients, doctors, treatments, prescriptions, pharmacies, and drug inventory. The project will demonstrate data validation, exception handling, logging, unit testing, database integration, and external API usage in accordance with defined business rules.

# Objecive
1. Demonstrate object-oriented design (POO) using Java classes, inheritance, and encapsulation.

2. Ensure data validation and business logic enforcement (e.g., prescription rules, inventory limits).

3. Implement logging and exception handling for reliability.

4. Cover unit testing for at least 100 scenarios, including mock testing.

5. Implement layered architecture (Data Access Layer, Business Logic Layer, Presentation Layer - CLI).

6. Connect to the PostgreSQL database, using stored procedures for key operations.

7. Integrate at least one external API (e.g., a drug information API for checking drug interactions or expiration dates).

# Entities & Main Features
1. Person Management -> Add/update/delete patients, doctors, pharmacists
                     -> Validate mandatory fields: name, role, sex, date of birth, email
                     -> Example validation: Name > 4 characters; email must be valid format.

2. Treatment Management -> Assign treatments to patients with doctors
                        -> Assign treatments to patients with doctors
                        -> Assign treatments to patients with doctors
                        -> Ensure start and end dates are valid (start < end)
                        -> Ensure start and end dates are valid (start < end)
                        -> Business rule: One patient cannot have overlapping treatments for the same condition

3. Drug Management -> Add/update/delete drugs and manage inventory across pharmacies
                   -> Validate drug names, dosages, prices (>0), and stock quantity

4. Prescription Management -> Create prescriptions linking patients, doctors, treatments, and drugs
                           -> Validate: drugs in prescription must exist in inventory; quantity <= stock
                        

5. Inventory & Pharmacy Management -> Track stock levels, expiry dates
                                   -> Business rules: Trigger alerts if stock < minimum threshold

6. Purchase Management -> Record patient purchases (with/without prescriptions)
                       -> Validate total amount and prescription adherence
                       -> Logging of each purchase transaction.

# Workflow
1. **Login/Initialization**
   - CLI application starts
   - Verify user role (doctor, pharmacist, admin)
   - Connect to PostgreSQL database

2. **Main Menu**
   - Manage Persons
   - Manage Drugs & Inventory
   - Manage Treatments
   - Manage Prescriptions
   - Manage Purchases
   - Reporting & Logging

3. **Operations**
   - Input validation
   - Exception handling
   - Logging
   - Database interaction via DAO layer
   - External API calls for drug checks

4. **Unit Testing**
   - At least 100 test methods covering:
     - Data validation
     - Stock checks
     - Prescription rules
     - Purchase validation
     - Mocking API responses

5. **Reporting**
   - Prescriptions per doctor/patient
   - Low inventory alerts
   - Purchases exceeding budget or violating rules

---

# Architecture

- **Presentation Layer** (CLI)
  - Handles user interaction
  - Calls business logic layer methods

- **Business Logic Layer**
  - Implements rules and validations
  - Handles exceptions and logging
  - Calls DAO layer for database access

- **Data Access Layer (DAO)**
  - Interacts with PostgreSQL database
  - Executes SQL queries and stored procedures

---

# Data Validation Examples
- Name length > 4 characters
- Mandatory fields completion
- Drug quantity ≤ inventory stock
- Prescription start/end date validation
- Role-based action enforcement (e.g., only doctors can create prescriptions)

---

# Logging & Exception Handling
- Logging: `java.util.logging` or `log4j`
  - Logs creation, updates, deletions, purchases, and prescriptions
- Exceptions:
  - `InvalidDataException`
  - `StockException`
  - `PrescriptionException`
  - Database connection failures

---

# Unit Testing
- Framework: **JUnit 5**
- Includes **mock testing** using **Mockito**
- Covers validation, stock, prescription rules, and API integration

---

# External API Integration
- Optional integration with a **drug information API**:
  - Check for drug interactions
  - Verify expiry dates
  - Suggest dosage guidelines
- API responses are mocked in unit tests

---

# Technologies Used
- **Java 17+**
- **PostgreSQL 17**

---

# Project Outcome
A fully functional Pharmacy Management System that:
- Demonstrates **layered OOP architecture**
- Ensures **data integrity and business rule compliance**
- Integrates with a **realistic PostgreSQL database**
- Includes **robust logging, exception handling, and unit testing**
- Consumes **external APIs** for drug validation

  # Database
  <img width="1588" height="1818" alt="Untitled" src="https://github.com/user-attachments/assets/6f82829a-2f47-4942-9f8c-4a29a718a2a8" />






