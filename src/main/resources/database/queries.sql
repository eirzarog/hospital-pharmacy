-- Find all drugs that belong to the 'Antibiotics' category.
SELECT d.*
FROM drugs d
         JOIN drug_categories dc ON d.category_id = dc.id
WHERE dc.name = 'Antibiotics';

-- List drugs with stock less than 20 units.
SELECT name, stock
FROM drugs
WHERE stock < 20;

-- Filter stock movements that occurred between dates.
SELECT *
FROM stock_movements
WHERE movement_date BETWEEN '2025-10-01' AND '2025-10-05';

