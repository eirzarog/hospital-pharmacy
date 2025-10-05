CREATE TABLE IF NOT EXISTS public.staff (
                                 id BIGSERIAL PRIMARY KEY,
                                 full_name VARCHAR(100) NOT NULL,
                                 role VARCHAR(50) NOT NULL, -- e.g., Doctor, Nurse, Technician, Admin
                                 department VARCHAR(50),    -- e.g., Cardiology, Pediatrics, Radiology
                                 email VARCHAR(100) UNIQUE,
                                 phone_number VARCHAR(20),
                                 hire_date DATE NOT NULL,
                                 shift VARCHAR(20),         -- e.g., Morning, Evening, Night
                                 status VARCHAR(20) DEFAULT 'Active' CHECK (status IN ('Active', 'On Leave', 'Retired')),
                                 created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS public.drug_categories (
                                 id BIGSERIAL PRIMARY KEY,
                                 name VARCHAR(100) NOT NULL UNIQUE,
                                 description VARCHAR(500),
                                 created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS public.drugs (
                                id BIGSERIAL PRIMARY KEY,
                                name VARCHAR(200) NOT NULL,
                                code VARCHAR(50) NOT NULL UNIQUE,
                                price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
                                stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
                                category_id BIGINT NOT NULL,
                                description VARCHAR(1000),
                                manufacturer VARCHAR(100),
                                created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (category_id) REFERENCES public.drug_categories(id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS public.stock_movements (
                                id BIGSERIAL PRIMARY KEY,
                                movement_type VARCHAR(20) NOT NULL CHECK (movement_type IN ('INBOUND', 'OUTBOUND')),
                                drug_id BIGINT NOT NULL,
                                quantity INT NOT NULL CHECK (quantity > 0),
                                movement_date TIMESTAMP NOT NULL,
                                notes VARCHAR(500),
                                performed_by BIGINT NOT NULL,
                                created_at TIMESTAMP WITH TIME ZONE  DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (drug_id) REFERENCES public.drugs(id) ON DELETE CASCADE,
                                FOREIGN KEY (performed_by) REFERENCES public.staff(id) ON DELETE CASCADE
);