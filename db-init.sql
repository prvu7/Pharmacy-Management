-- creating the database for app

CREATE DATABASE pharmacy_management;

\c pharmacy_management


CREATE TABLE person (
                        person_id SERIAL PRIMARY KEY,
                        first_name VARCHAR(50) NOT NULL,
                        last_name VARCHAR(50) NOT NULL,
                        sex VARCHAR(10) CHECK (sex IN ('M','F','O')) NOT NULL,
                        date_of_birth DATE NOT NULL,
                        phone VARCHAR(20),
                        email VARCHAR(100),
                        address VARCHAR(255),
                        role VARCHAR(20) CHECK (role IN ('patient','doctor','pharmacist')) NOT NULL
);

CREATE TABLE treatment (
                           treatment_id SERIAL PRIMARY KEY,
                           treatment_name VARCHAR(100) NOT NULL,
                           description TEXT,
                           doctor_id INT,
                           start_date DATE,
                           end_date DATE,
                           FOREIGN KEY (doctor_id) REFERENCES person(person_id)
                               ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE prescription (
                              prescription_id SERIAL PRIMARY KEY,
                              patient_id INT NOT NULL,
                              doctor_id INT NOT NULL,
                              treatment_id INT,
                              prescription_date DATE NOT NULL,
                              notes TEXT,
                              FOREIGN KEY (patient_id) REFERENCES person(person_id)
                                  ON UPDATE CASCADE ON DELETE CASCADE,
                              FOREIGN KEY (doctor_id) REFERENCES person(person_id)
                                  ON UPDATE CASCADE ON DELETE CASCADE,
                              FOREIGN KEY (treatment_id) REFERENCES treatment(treatment_id)
                                  ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE drug (
                      drug_id SERIAL PRIMARY KEY,
                      drug_name VARCHAR(100) NOT NULL,
                      generic_name VARCHAR(100),
                      description TEXT,
                      dosage_form VARCHAR(50),
                      manufacturer VARCHAR(100),
                      price DECIMAL(10,2)
);

CREATE TABLE prescription_detail (
                                     prescription_id INT NOT NULL,
                                     drug_id INT NOT NULL,
                                     dosage VARCHAR(50),
                                     duration_days INT,
                                     quantity INT,
                                     PRIMARY KEY (prescription_id, drug_id),
                                     FOREIGN KEY (prescription_id) REFERENCES prescription(prescription_id)
                                         ON UPDATE CASCADE ON DELETE CASCADE,
                                     FOREIGN KEY (drug_id) REFERENCES drug(drug_id)
                                         ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE pharmacy (
                          pharmacy_id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          address VARCHAR(255),
                          phone VARCHAR(20)
);

CREATE TABLE inventory (
                           inventory_id SERIAL PRIMARY KEY,
                           pharmacy_id INT NOT NULL,
                           drug_id INT NOT NULL,
                           quantity_in_stock INT DEFAULT 0,
                           expiry_date DATE,
                           FOREIGN KEY (pharmacy_id) REFERENCES pharmacy(pharmacy_id)
                               ON UPDATE CASCADE ON DELETE CASCADE,
                           FOREIGN KEY (drug_id) REFERENCES drug(drug_id)
                               ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE purchase (
                          purchase_id SERIAL PRIMARY KEY,
                          pharmacy_id INT NOT NULL,
                          patient_id INT NOT NULL,
                          prescription_id INT,
                          purchase_date DATE NOT NULL,
                          total_amount DECIMAL(10,2),
                          FOREIGN KEY (pharmacy_id) REFERENCES pharmacy(pharmacy_id)
                              ON UPDATE CASCADE ON DELETE CASCADE,
                          FOREIGN KEY (patient_id) REFERENCES person(person_id)
                              ON UPDATE CASCADE ON DELETE CASCADE,
                          FOREIGN KEY (prescription_id) REFERENCES prescription(prescription_id)
                              ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE purchase_detail (
                                 purchase_id INT NOT NULL,
                                 drug_id INT NOT NULL,
                                 quantity INT NOT NULL,
                                 unit_price DECIMAL(10,2),
                                 PRIMARY KEY (purchase_id, drug_id),
                                 FOREIGN KEY (purchase_id) REFERENCES purchase(purchase_id)
                                     ON UPDATE CASCADE ON DELETE CASCADE,
                                 FOREIGN KEY (drug_id) REFERENCES drug(drug_id)
                                     ON UPDATE CASCADE ON DELETE CASCADE
);


INSERT INTO person (first_name, last_name, sex, date_of_birth, phone, email, address, role) VALUES
                                                                                                ('John', 'Smith', 'M', '1980-04-12', '555-1001', 'john.smith@email.com', '123 Oak St', 'doctor'),
                                                                                                ('Emily', 'Brown', 'F', '1985-06-22', '555-1002', 'emily.brown@email.com', '45 Pine Rd', 'doctor'),
                                                                                                ('Michael', 'Lee', 'M', '1978-01-30', '555-1003', 'michael.lee@email.com', '789 Maple Ave', 'doctor'),
                                                                                                ('Sarah', 'Jones', 'F', '1990-02-11', '555-1004', 'sarah.jones@email.com', '34 Willow Dr', 'doctor'),
                                                                                                ('David', 'White', 'M', '1982-07-25', '555-1005', 'david.white@email.com', '67 Birch Ln', 'doctor'),

-- pharmacists
                                                                                                ('Anna', 'Taylor', 'F', '1988-09-13', '555-2001', 'anna.taylor@email.com', '12 Cedar Blvd', 'pharmacist'),
                                                                                                ('Mark', 'Evans', 'M', '1975-03-17', '555-2002', 'mark.evans@email.com', '56 Elm St', 'pharmacist'),
                                                                                                ('Olivia', 'Nguyen', 'F', '1991-11-09', '555-2003', 'olivia.nguyen@email.com', '23 Walnut Way', 'pharmacist'),
                                                                                                ('James', 'Martinez', 'M', '1989-05-01', '555-2004', 'james.martinez@email.com', '101 Spruce Pl', 'pharmacist'),
                                                                                                ('Sophia', 'Harris', 'F', '1994-10-05', '555-2005', 'sophia.harris@email.com', '9 Chestnut Ct', 'pharmacist'),

-- patients
                                                                                                ('Liam', 'Walker', 'M', '1995-03-22', '555-3001', 'liam.walker@email.com', '45 Cherry St', 'patient'),
                                                                                                ('Emma', 'Hall', 'F', '1997-07-14', '555-3002', 'emma.hall@email.com', '11 Poplar Rd', 'patient'),
                                                                                                ('Noah', 'Allen', 'M', '1989-01-09', '555-3003', 'noah.allen@email.com', '22 Hickory St', 'patient'),
                                                                                                ('Ava', 'Young', 'F', '1993-09-30', '555-3004', 'ava.young@email.com', '88 Beech Ave', 'patient'),
                                                                                                ('Lucas', 'King', 'M', '1998-02-25', '555-3005', 'lucas.king@email.com', '77 Palm Dr', 'patient'),
                                                                                                ('Mia', 'Wright', 'F', '1996-12-19', '555-3006', 'mia.wright@email.com', '100 Magnolia Ln', 'patient'),
                                                                                                ('Ethan', 'Scott', 'M', '1990-04-05', '555-3007', 'ethan.scott@email.com', '33 Redwood Rd', 'patient'),
                                                                                                ('Isabella', 'Green', 'F', '1992-06-17', '555-3008', 'isabella.green@email.com', '19 Fir Ct', 'patient'),
                                                                                                ('Jacob', 'Baker', 'M', '1994-11-21', '555-3009', 'jacob.baker@email.com', '2 Cypress Blvd', 'patient'),
                                                                                                ('Charlotte', 'Adams', 'F', '1999-08-13', '555-3010', 'charlotte.adams@email.com', '51 Lilac St', 'patient'),
                                                                                                ('Henry', 'Nelson', 'M', '1988-05-10', '555-3011', 'henry.nelson@email.com', '29 Dogwood Rd', 'patient'),
                                                                                                ('Amelia', 'Carter', 'F', '1991-10-02', '555-3012', 'amelia.carter@email.com', '64 Juniper Dr', 'patient'),
                                                                                                ('William', 'Mitchell', 'M', '1993-12-29', '555-3013', 'william.mitchell@email.com', '5 Olive Pl', 'patient'),
                                                                                                ('Evelyn', 'Perez', 'F', '1995-09-08', '555-3014', 'evelyn.perez@email.com', '84 Alder Ave', 'patient'),
                                                                                                ('Elijah', 'Roberts', 'M', '1997-07-01', '555-3015', 'elijah.roberts@email.com', '12 Cottonwood St', 'patient'),
                                                                                                ('Harper', 'Turner', 'F', '1990-01-25', '555-3016', 'harper.turner@email.com', '71 Laurel Ln', 'patient'),
                                                                                                ('Daniel', 'Phillips', 'M', '1986-08-16', '555-3017', 'daniel.phillips@email.com', '8 Palm Way', 'patient'),
                                                                                                ('Grace', 'Campbell', 'F', '1998-03-11', '555-3018', 'grace.campbell@email.com', '94 Birch Ct', 'patient'),
                                                                                                ('Matthew', 'Parker', 'M', '1999-11-05', '555-3019', 'matthew.parker@email.com', '15 Redwood Ave', 'patient'),
                                                                                                ('Sofia', 'Murphy', 'F', '1992-09-23', '555-3020', 'sofia.murphy@email.com', '60 Willow Blvd', 'patient'),
                                                                                                ('Jackson', 'Flores', 'M', '1989-04-04', '555-3021', 'jackson.flores@email.com', '40 Maple St', 'patient');

INSERT INTO treatment (treatment_name, description, doctor_id, start_date, end_date) VALUES
                                                                                         ('Antibiotic Course', 'Treatment for bacterial infection', 1, '2025-01-01', '2025-01-10'),
                                                                                         ('Hypertension Control', 'Blood pressure management plan', 2, '2025-02-05', '2025-08-05'),
                                                                                         ('Diabetes Monitoring', 'Routine glucose level check-up', 3, '2025-03-10', NULL),
                                                                                         ('Asthma Therapy', 'Inhaler-based respiratory treatment', 4, '2025-04-12', '2025-07-12'),
                                                                                         ('Migraine Relief', 'Pain management for migraines', 5, '2025-05-01', NULL),
                                                                                         ('Skin Allergy Care', 'Antihistamine and ointment course', 1, '2025-05-20', '2025-06-10'),
                                                                                         ('Flu Treatment', 'Antiviral therapy and rest', 2, '2025-06-01', '2025-06-07'),
                                                                                         ('Gastroenteritis', 'Hydration and medication for stomach infection', 3, '2025-06-15', '2025-06-25'),
                                                                                         ('Back Pain Management', 'Physical therapy and analgesics', 4, '2025-06-20', '2025-08-20'),
                                                                                         ('Cholesterol Reduction', 'Statin treatment plan', 5, '2025-07-01', NULL),
                                                                                         ('Anemia Correction', 'Iron supplements and diet monitoring', 1, '2025-07-10', NULL),
                                                                                         ('Post-surgery Recovery', 'Pain relief and wound care', 2, '2025-07-20', '2025-08-20'),
                                                                                         ('Thyroid Regulation', 'Hormone replacement therapy', 3, '2025-08-01', NULL),
                                                                                         ('Depression Therapy', 'Cognitive behavioral therapy', 4, '2025-08-10', NULL),
                                                                                         ('Arthritis Relief', 'Anti-inflammatory medication plan', 5, '2025-08-15', NULL),
                                                                                         ('Weight Management', 'Diet and exercise supervision', 1, '2025-09-01', NULL),
                                                                                         ('Insomnia Care', 'Sleep hygiene and medication', 2, '2025-09-10', '2025-10-10'),
                                                                                         ('COVID-19 Recovery', 'Post-virus fatigue management', 3, '2025-09-15', '2025-10-15'),
                                                                                         ('Allergy Desensitization', 'Gradual exposure treatment', 4, '2025-09-20', NULL),
                                                                                         ('UTI Treatment', 'Antibiotics for urinary infection', 5, '2025-09-25', '2025-10-05'),
                                                                                         ('Vitamin Deficiency', 'Supplement plan for deficiencies', 1, '2025-10-01', NULL),
                                                                                         ('Pediatric Vaccination', 'Child immunization schedule', 2, '2025-10-05', NULL),
                                                                                         ('Smoking Cessation', 'Nicotine replacement therapy', 3, '2025-10-10', NULL),
                                                                                         ('Heart Disease Prevention', 'Lifestyle and medication plan', 4, '2025-10-15', NULL),
                                                                                         ('Cold and Cough', 'Cough syrup and antihistamines', 5, '2025-10-20', '2025-10-25'),
                                                                                         ('Postpartum Checkup', 'New mother recovery plan', 1, '2025-10-25', '2025-11-05'),
                                                                                         ('Kidney Stone Treatment', 'Hydration and medication plan', 2, '2025-10-28', NULL),
                                                                                         ('Liver Function Check', 'Hepatic health monitoring', 3, '2025-11-01', NULL),
                                                                                         ('Chronic Pain Management', 'Long-term pain control program', 4, '2025-11-03', NULL),
                                                                                         ('Osteoporosis Treatment', 'Calcium and vitamin D supplements', 5, '2025-11-05', NULL);

INSERT INTO drug (drug_name, generic_name, description, dosage_form, manufacturer, price) VALUES
                                                                                              ('Amoxicillin', 'Amoxicillin', 'Antibiotic for bacterial infections', 'Capsule', 'Pfizer', 5.25),
                                                                                              ('Paracetamol', 'Acetaminophen', 'Pain reliever and fever reducer', 'Tablet', 'GSK', 2.10),
                                                                                              ('Ibuprofen', 'Ibuprofen', 'Anti-inflammatory painkiller', 'Tablet', 'Bayer', 3.75),
                                                                                              ('Metformin', 'Metformin', 'Blood sugar control for diabetes', 'Tablet', 'Novartis', 4.80),
                                                                                              ('Losartan', 'Losartan', 'Blood pressure medicine', 'Tablet', 'Teva', 3.50),
                                                                                              ('Omeprazole', 'Omeprazole', 'Reduces stomach acid', 'Capsule', 'Pfizer', 6.00),
                                                                                              ('Cetirizine', 'Cetirizine', 'Antihistamine for allergies', 'Tablet', 'Sanofi', 2.50),
                                                                                              ('Azithromycin', 'Azithromycin', 'Broad-spectrum antibiotic', 'Tablet', 'Cipla', 7.20),
                                                                                              ('Amlodipine', 'Amlodipine', 'Treats high blood pressure', 'Tablet', 'GSK', 3.90),
                                                                                              ('Simvastatin', 'Simvastatin', 'Cholesterol-lowering drug', 'Tablet', 'Merck', 4.30),
                                                                                              ('Cough Syrup', 'Dextromethorphan', 'Suppresses cough reflex', 'Syrup', 'Roche', 5.10),
                                                                                              ('Vitamin C', 'Ascorbic Acid', 'Boosts immune system', 'Tablet', 'Sun Pharma', 1.50),
                                                                                              ('Iron Supplement', 'Ferrous Sulfate', 'Treats iron deficiency anemia', 'Tablet', 'Bayer', 2.70),
                                                                                              ('Levothyroxine', 'Levothyroxine', 'Regulates thyroid hormones', 'Tablet', 'Pfizer', 3.40),
                                                                                              ('Sertraline', 'Sertraline', 'Antidepressant', 'Tablet', 'Lilly', 8.00),
                                                                                              ('Prednisone', 'Prednisone', 'Anti-inflammatory steroid', 'Tablet', 'Roche', 4.50),
                                                                                              ('Albuterol', 'Salbutamol', 'Bronchodilator for asthma', 'Inhaler', 'GSK', 12.00),
                                                                                              ('Atorvastatin', 'Atorvastatin', 'Lowers cholesterol', 'Tablet', 'Pfizer', 4.10),
                                                                                              ('Loratadine', 'Loratadine', 'Allergy medication', 'Tablet', 'Teva', 2.20),
                                                                                              ('Hydroxyzine', 'Hydroxyzine', 'Antihistamine & sedative', 'Tablet', 'Sanofi', 3.10),
                                                                                              ('Insulin', 'Human Insulin', 'Blood sugar control injection', 'Injection', 'Novo Nordisk', 15.00),
                                                                                              ('Calcium D3', 'Calcium Carbonate + Vitamin D3', 'Bone health supplement', 'Tablet', 'Abbott', 3.00),
                                                                                              ('Zinc Supplement', 'Zinc Sulfate', 'Immune support', 'Tablet', 'Teva', 2.60),
                                                                                              ('Diclofenac', 'Diclofenac', 'Pain and inflammation relief', 'Tablet', 'Novartis', 3.80),
                                                                                              ('Hydrochlorothiazide', 'HCTZ', 'Diuretic for hypertension', 'Tablet', 'GSK', 3.20),
                                                                                              ('Ciprofloxacin', 'Ciprofloxacin', 'Broad-spectrum antibiotic', 'Tablet', 'Cipla', 6.80),
                                                                                              ('Folic Acid', 'Folate', 'Vitamin supplement', 'Tablet', 'Sun Pharma', 1.80),
                                                                                              ('Amoxiclav', 'Amoxicillin + Clavulanic Acid', 'Antibiotic combination', 'Tablet', 'Cipla', 6.20),
                                                                                              ('Melatonin', 'Melatonin', 'Sleep aid', 'Tablet', 'Lilly', 4.90),
                                                                                              ('Pantoprazole', 'Pantoprazole', 'Reduces stomach acid', 'Tablet', 'Pfizer', 5.00);

INSERT INTO pharmacy (name, address, phone) VALUES
                                                ('HealthPlus Pharmacy', '12 Market St', '555-4001'),
                                                ('CityCare Pharmacy', '45 King Rd', '555-4002'),
                                                ('Wellness Pharmacy', '78 River Blvd', '555-4003'),
                                                ('Community Pharmacy', '22 Hill St', '555-4004'),
                                                ('GoodLife Pharmacy', '10 Main Ave', '555-4005'),
                                                ('MediTrust Pharmacy', '56 Oak Dr', '555-4006'),
                                                ('Sunrise Pharmacy', '33 Maple St', '555-4007'),
                                                ('GreenCross Pharmacy', '90 Birch Rd', '555-4008'),
                                                ('CureWell Pharmacy', '17 Pine Ave', '555-4009'),
                                                ('LifeCare Pharmacy', '81 Cedar Blvd', '555-4010');

INSERT INTO inventory (pharmacy_id, drug_id, quantity_in_stock, expiry_date) VALUES
                                                                                 (1, 1, 120, '2026-03-01'), (1, 2, 250, '2026-08-15'), (1, 3, 150, '2026-12-01'),
                                                                                 (1, 5, 180, '2027-01-10'), (1, 7, 300, '2026-09-01'), (1, 9, 100, '2026-11-20'),
                                                                                 (1, 10, 80, '2027-02-01'), (1, 11, 60, '2026-06-15'), (1, 12, 400, '2027-01-01'), (1, 13, 100, '2026-04-05'),

                                                                                 (2, 4, 120, '2026-10-01'), (2, 6, 180, '2026-11-10'), (2, 8, 90, '2026-08-01'),
                                                                                 (2, 9, 200, '2027-01-15'), (2, 14, 75, '2026-12-20'), (2, 15, 60, '2027-02-15'),
                                                                                 (2, 16, 110, '2026-09-09'), (2, 17, 50, '2026-12-01'), (2, 18, 90, '2026-07-15'), (2, 19, 200, '2026-05-01'),

                                                                                 (3, 3, 90, '2026-09-01'), (3, 6, 120, '2026-11-01'), (3, 9, 130, '2027-02-15'),
                                                                                 (3, 10, 80, '2027-01-01'), (3, 11, 95, '2026-10-25'), (3, 13, 110, '2026-09-09'),
                                                                                 (3, 20, 100, '2026-12-12'), (3, 22, 90, '2027-03-01'), (3, 24, 85, '2026-08-01'), (3, 25, 140, '2027-01-15'),

                                                                                 (4, 5, 160, '2026-12-05'), (4, 7, 150, '2026-07-20'), (4, 9, 100, '2026-10-10'),
                                                                                 (4, 14, 60, '2026-11-30'), (4, 17, 40, '2026-12-15'), (4, 19, 200, '2026-06-20'),
                                                                                 (4, 20, 100, '2026-09-30'), (4, 21, 70, '2026-08-01'), (4, 23, 60, '2026-07-01'), (4, 28, 80, '2026-12-31'),

                                                                                 (5, 1, 200, '2026-12-10'), (5, 2, 300, '2026-10-10'), (5, 4, 180, '2027-02-20'),
                                                                                 (5, 6, 140, '2026-08-30'), (5, 10, 100, '2026-12-15'), (5, 12, 400, '2026-11-11'),
                                                                                 (5, 13, 90, '2026-10-01'), (5, 15, 80, '2027-01-15'), (5, 16, 60, '2026-06-06'), (5, 18, 75, '2026-09-09');

INSERT INTO prescription (patient_id, doctor_id, treatment_id, prescription_date, notes) VALUES
                                                                                             (11, 1, 1, '2025-01-02', 'Take after meals'),
                                                                                             (12, 2, 2, '2025-02-06', 'Monitor blood pressure'),
                                                                                             (13, 3, 3, '2025-03-11', 'Check glucose weekly'),
                                                                                             (14, 4, 4, '2025-04-13', 'Use inhaler twice daily'),
                                                                                             (15, 5, 5, '2025-05-02', 'Take before sleep'),
                                                                                             (16, 1, 6, '2025-05-21', 'Apply cream twice daily'),
                                                                                             (17, 2, 7, '2025-06-02', 'Drink fluids, rest'),
                                                                                             (18, 3, 8, '2025-06-16', 'Take medicine after meals'),
                                                                                             (19, 4, 9, '2025-06-21', 'Attend physiotherapy'),
                                                                                             (20, 5, 10, '2025-07-02', 'Dietary changes required'),
                                                                                             (21, 1, 11, '2025-07-11', 'Take iron tablet daily'),
                                                                                             (22, 2, 12, '2025-07-21', 'Pain relief as needed'),
                                                                                             (23, 3, 13, '2025-08-02', 'Take morning before food'),
                                                                                             (24, 4, 14, '2025-08-11', 'Therapy twice a week'),
                                                                                             (25, 5, 15, '2025-08-16', 'Take after meals'),
                                                                                             (26, 1, 16, '2025-09-02', 'Follow diet plan'),
                                                                                             (27, 2, 17, '2025-09-11', 'Take at bedtime'),
                                                                                             (28, 3, 18, '2025-09-16', 'Monitor oxygen levels'),
                                                                                             (29, 4, 19, '2025-09-21', 'Avoid allergens'),
                                                                                             (30, 5, 20, '2025-09-26', 'Drink plenty of water'),
                                                                                             (11, 1, 21, '2025-10-02', 'Vitamin supplements daily'),
                                                                                             (12, 2, 22, '2025-10-06', 'Vaccination schedule follow-up'),
                                                                                             (13, 3, 23, '2025-10-11', 'Nicotine patch daily'),
                                                                                             (14, 4, 24, '2025-10-16', 'Regular exercise advised'),
                                                                                             (15, 5, 25, '2025-10-21', 'Drink warm fluids'),
                                                                                             (16, 1, 26, '2025-10-26', 'Follow-up in two weeks'),
                                                                                             (17, 2, 27, '2025-10-29', 'Hydrate well'),
                                                                                             (18, 3, 28, '2025-11-02', 'Check liver enzymes'),
                                                                                             (19, 4, 29, '2025-11-04', 'Reduce pain medication gradually'),
                                                                                             (20, 5, 30, '2025-11-06', 'Calcium daily morning');

INSERT INTO prescription_detail (prescription_id, drug_id, dosage, duration_days, quantity) VALUES
                                                                                                (1, 1, '500mg twice daily', 7, 14),
                                                                                                (2, 5, '50mg daily', 30, 30),
                                                                                                (3, 4, '500mg twice daily', 60, 120),
                                                                                                (4, 17, '2 puffs twice daily', 30, 1),
                                                                                                (5, 15, '50mg daily', 15, 15),
                                                                                                (6, 7, '10mg once daily', 14, 14),
                                                                                                (7, 8, '500mg once daily', 5, 5),
                                                                                                (8, 9, '5mg once daily', 10, 10),
                                                                                                (9, 24, '50mg twice daily', 10, 20),
                                                                                                (10, 10, '20mg once daily', 30, 30),
                                                                                                (11, 13, '325mg daily', 30, 30),
                                                                                                (12, 16, '10mg as needed', 10, 10),
                                                                                                (13, 14, '25mcg daily', 30, 30),
                                                                                                (14, 15, '100mg daily', 15, 15),
                                                                                                (15, 23, '50mg daily', 30, 30),
                                                                                                (16, 22, '1 tablet daily', 60, 60),
                                                                                                (17, 29, '1 tablet at bedtime', 10, 10),
                                                                                                (18, 1, '500mg daily', 10, 10),
                                                                                                (19, 7, '10mg twice daily', 30, 60),
                                                                                                (20, 26, '250mg twice daily', 7, 14),
                                                                                                (21, 12, '500mg daily', 30, 30),
                                                                                                (22, 28, '1 tablet daily', 15, 15),
                                                                                                (23, 15, '50mg daily', 20, 20),
                                                                                                (24, 18, '10mg daily', 30, 30),
                                                                                                (25, 11, '2 tsp every 6 hours', 5, 1),
                                                                                                (26, 10, '20mg daily', 15, 15),
                                                                                                (27, 6, '20mg daily', 30, 30),
                                                                                                (28, 30, '1 tablet daily', 10, 10),
                                                                                                (29, 24, '50mg twice daily', 15, 30),
                                                                                                (30, 22, '1 tablet daily', 60, 60);

INSERT INTO purchase (pharmacy_id, patient_id, prescription_id, purchase_date, total_amount) VALUES
                                                                                                 (1, 11, 1, '2025-01-03', 12.50),
                                                                                                 (2, 12, 2, '2025-02-07', 15.00),
                                                                                                 (3, 13, 3, '2025-03-12', 20.00),
                                                                                                 (4, 14, 4, '2025-04-14', 18.00),
                                                                                                 (5, 15, 5, '2025-05-03', 10.50),
                                                                                                 (1, 16, 6, '2025-05-22', 9.00),
                                                                                                 (2, 17, 7, '2025-06-03', 7.50),
                                                                                                 (3, 18, 8, '2025-06-17', 11.00),
                                                                                                 (4, 19, 9, '2025-06-22', 14.00),
                                                                                                 (5, 20, 10, '2025-07-03', 16.00),
                                                                                                 (6, 21, 11, '2025-07-12', 13.50),
                                                                                                 (7, 22, 12, '2025-07-22', 8.00),
                                                                                                 (8, 23, 13, '2025-08-03', 17.50),
                                                                                                 (9, 24, 14, '2025-08-12', 12.00),
                                                                                                 (10, 25, 15, '2025-08-17', 15.50),
                                                                                                 (1, 26, 16, '2025-09-03', 10.00),
                                                                                                 (2, 27, 17, '2025-09-12', 9.50),
                                                                                                 (3, 28, 18, '2025-09-17', 7.00),
                                                                                                 (4, 29, 19, '2025-09-22', 8.50),
                                                                                                 (5, 30, 20, '2025-09-27', 11.00),
                                                                                                 (6, 11, NULL, '2025-10-02', 5.00),
                                                                                                 (7, 12, NULL, '2025-10-06', 6.50),
                                                                                                 (8, 13, NULL, '2025-10-11', 4.75),
                                                                                                 (9, 14, NULL, '2025-10-16', 7.25),
                                                                                                 (10, 15, NULL, '2025-10-21', 9.00),
                                                                                                 (1, 16, 21, '2025-10-27', 13.00),
                                                                                                 (2, 17, 22, '2025-10-30', 10.50),
                                                                                                 (3, 18, 23, '2025-11-03', 12.75),
                                                                                                 (4, 19, 24, '2025-11-05', 8.50),
                                                                                                 (5, 20, 25, '2025-11-07', 9.75);

INSERT INTO purchase_detail (purchase_id, drug_id, quantity, unit_price) VALUES
                                                                             (1, 1, 14, 0.90),   -- matches prescription
                                                                             (2, 5, 30, 0.50),
                                                                             (3, 4, 120, 0.15),
                                                                             (4, 17, 1, 18.00),
                                                                             (5, 15, 15, 0.70),
                                                                             (6, 7, 14, 0.64),
                                                                             (7, 8, 5, 1.50),
                                                                             (8, 9, 10, 1.10),
                                                                             (9, 24, 20, 0.70),
                                                                             (10, 10, 30, 0.53),
                                                                             (11, 13, 30, 0.45),
                                                                             (12, 16, 10, 0.80),
                                                                             (13, 14, 30, 0.60),
                                                                             (14, 15, 15, 0.70),
                                                                             (15, 23, 30, 0.52),
                                                                             (16, 22, 60, 0.25),
                                                                             (17, 29, 10, 0.95),
                                                                             (18, 1, 10, 0.90),
                                                                             (19, 7, 30, 0.64),
                                                                             (20, 26, 14, 0.75),
                                                                             (21, 12, 5, 1.20),   -- OTC purchase
                                                                             (22, 8, 10, 1.50),   -- OTC purchase
                                                                             (23, 15, 20, 0.70),
                                                                             (24, 18, 30, 0.60),
                                                                             (25, 11, 5, 0.80),
                                                                             (26, 12, 30, 0.50),
                                                                             (27, 28, 15, 0.85),
                                                                             (28, 30, 10, 0.90),
                                                                             (29, 24, 20, 0.70),
                                                                             (30, 22, 60, 0.25);


