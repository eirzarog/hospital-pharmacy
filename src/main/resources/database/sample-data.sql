INSERT INTO public.staff (full_name, role, department, email, phone_number, hire_date, shift, status)
VALUES
    ('Dr. Eleni Papadopoulos', 'Doctor', 'Cardiology', 'epapadopoulos@hospital.gr', '2101234567', '2018-03-15', 'Morning', 'Active'),
    ('Nikos Georgiou', 'Nurse', 'Pediatrics', 'ngeorgiou@hospital.gr', '2102345678', '2020-07-01', 'Evening', 'Active'),
    ('Maria Kotsou', 'Technician', 'Radiology', 'mkotsou@hospital.gr', '2103456789', '2019-11-20', 'Night', 'On Leave'),
    ('Sofia Dimitriou', 'Admin', 'Administration', 'sdimitriou@hospital.gr', '2104567890', '2017-05-10', 'Morning', 'Active'),
    ('Dr. Andreas Vasilakis', 'Doctor', 'Neurology', 'avasilakis@hospital.gr', '2105678901', '2015-01-25', 'Evening', 'Retired');


INSERT INTO public.drug_categories (name, description)
VALUES
    ('Antibiotics', 'Drugs used to treat bacterial infections'),
    ('Analgesics', 'Pain relievers and fever reducers'),
    ('Antiseptics', 'Substances that prevent infection by killing microorganisms');

INSERT INTO public.drugs (name, code, price, stock, category_id, description, manufacturer)
VALUES
    ('Amoxicillin', 'AMX-500', 12.50, 100, 1, 'Broad-spectrum antibiotic', 'PharmaCorp'),
    ('Ibuprofen', 'IBU-200', 5.75, 200, 2, 'Non-steroidal anti-inflammatory drug', 'HealthGen'),
    ('Hydrogen Peroxide', 'H2O2-3', 3.20, 50, 3, 'Topical antiseptic', 'CleanMed');

INSERT INTO stock_movements (movement_type, drug_id, quantity, movement_date, notes, performed_by)
VALUES
    ('INBOUND', 1, 100, '2025-10-01 10:00:00', 'Initial stock delivery', 1),
    ('OUTBOUND', 1, 20, '2025-10-02 14:30:00', 'Dispensed to ward A', 2),
    ('INBOUND', 1, 50, '2025-10-03 09:00:00', 'Restock from supplier', 3);
