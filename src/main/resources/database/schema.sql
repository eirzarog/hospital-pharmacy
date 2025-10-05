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
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE FUNCTION update_staff_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_updated_at
    BEFORE UPDATE ON public.staff
    FOR EACH ROW
EXECUTE FUNCTION update_staff_updated_at_column();


CREATE TABLE IF NOT EXISTS public.drug_categories (
                                 id BIGSERIAL PRIMARY KEY,
                                 name VARCHAR(100) NOT NULL UNIQUE,
                                 description VARCHAR(500),
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE FUNCTION update_drug_category_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_updated_at
    BEFORE UPDATE ON public.drug_categories
    FOR EACH ROW
EXECUTE FUNCTION update_drug_category_updated_at_column();

CREATE TABLE IF NOT EXISTS public.drugs (
                                id BIGSERIAL PRIMARY KEY,
                                name VARCHAR(200) NOT NULL,
                                code VARCHAR(50) NOT NULL UNIQUE,
                                price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
                                stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
                                category_id BIGINT NOT NULL,
                                description VARCHAR(1000),
                                manufacturer VARCHAR(100),
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (category_id) REFERENCES public.drug_categories(id) ON DELETE RESTRICT
);

-- Index for category filtering
CREATE INDEX idx_drugs_category_id
    ON public.drugs (category_id);

-- Unique index for drug code
CREATE UNIQUE INDEX idx_unique_drug_code
    ON public.drugs (code);

-- Index for price-based filtering or sorting
CREATE INDEX idx_drugs_price
    ON public.drugs (price);

-- Index for stock-based queries
CREATE INDEX idx_drugs_stock
    ON public.drugs (stock);

-- Index for creation date (e.g., recent additions)
CREATE INDEX idx_drugs_created_at
    ON public.drugs (created_at);

CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_updated_at_drugs
    BEFORE UPDATE ON public.drugs
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();


CREATE TABLE IF NOT EXISTS public.stock_movements (
                                id BIGSERIAL PRIMARY KEY,
                                movement_type VARCHAR(20) NOT NULL CHECK (movement_type IN ('INBOUND', 'OUTBOUND')),
                                drug_id BIGINT NOT NULL,
                                quantity INT NOT NULL CHECK (quantity > 0),
                                movement_date TIMESTAMP NOT NULL,
                                notes VARCHAR(500),
                                performed_by BIGINT NOT NULL,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (drug_id) REFERENCES public.drugs(id) ON DELETE CASCADE,
                                FOREIGN KEY (performed_by) REFERENCES public.staff(id) ON DELETE CASCADE
);

ALTER TABLE public.stock_movements
    DROP CONSTRAINT IF EXISTS fk_performed_by;

ALTER TABLE public.stock_movements
    ADD CONSTRAINT fk_performed_by
        FOREIGN KEY (performed_by)
            REFERENCES public.staff(id)
            ON DELETE CASCADE;





-- Index for filtering by movement type
CREATE INDEX idx_stock_movements_type
    ON public.stock_movements (movement_type);

-- Index for filtering or sorting by movement date
CREATE INDEX idx_stock_movements_date
    ON public.stock_movements (movement_date);

-- Index for drug-based lookups
CREATE INDEX idx_stock_movements_drug
    ON public.stock_movements (drug_id);

-- Composite index for drug, type, and date queries
CREATE INDEX idx_stock_movements_drug_type_date
    ON public.stock_movements (drug_id, movement_type, movement_date);

-- Trigger to update stock on INBOUND movements
CREATE OR REPLACE FUNCTION add_stock_on_inbound()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.movement_type = 'INBOUND' THEN
        UPDATE public.drugs
        SET stock = stock + NEW.quantity
        WHERE id = NEW.drug_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_add_stock
    AFTER INSERT ON public.stock_movements
    FOR EACH ROW
EXECUTE FUNCTION add_stock_on_inbound();


-- Trigger to update stock on INBOUND movements
CREATE OR REPLACE FUNCTION deduct_stock_on_outbound()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.movement_type = 'OUTBOUND' THEN
        UPDATE public.drugs
        SET stock = stock - NEW.quantity
        WHERE id = NEW.drug_id;

        --Prevent negative stock
        IF (SELECT stock FROM public.drugs WHERE id = NEW.drug_id) < 0 THEN
            RAISE EXCEPTION 'Stock cannot go below zero for drug_id %', NEW.drug_id;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_deduct_stock
    AFTER INSERT ON public.stock_movements
    FOR EACH ROW
EXECUTE FUNCTION deduct_stock_on_outbound();

-- Trigger for delete (Rollback stock)
CREATE OR REPLACE FUNCTION rollback_stock_on_delete()
    RETURNS TRIGGER AS $$
BEGIN
    IF OLD.movement_type = 'INBOUND' THEN
        UPDATE public.drugs
        SET stock = stock - OLD.quantity
        WHERE id = OLD.drug_id;
    ELSIF OLD.movement_type = 'OUTBOUND' THEN
        UPDATE public.drugs
        SET stock = stock + OLD.quantity
        WHERE id = OLD.drug_id;
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_rollback_stock_delete
    AFTER DELETE ON public.stock_movements
    FOR EACH ROW
EXECUTE FUNCTION rollback_stock_on_delete();

-- Trigger for update (Adjust stock)
CREATE OR REPLACE FUNCTION adjust_stock_on_update()
    RETURNS TRIGGER AS $$
BEGIN
    -- Roll back old quantity
    IF OLD.movement_type = 'INBOUND' THEN
        UPDATE public.drugs
        SET stock = stock - OLD.quantity
        WHERE id = OLD.drug_id;
    ELSIF OLD.movement_type = 'OUTBOUND' THEN
        UPDATE public.drugs
        SET stock = stock + OLD.quantity
        WHERE id = OLD.drug_id;
    END IF;

    -- Apply new quantity
    IF NEW.movement_type = 'INBOUND' THEN
        UPDATE public.drugs
        SET stock = stock + NEW.quantity
        WHERE id = NEW.drug_id;
    ELSIF NEW.movement_type = 'OUTBOUND' THEN
        UPDATE public.drugs
        SET stock = stock - NEW.quantity
        WHERE id = NEW.drug_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_adjust_stock_update
    AFTER UPDATE ON public.stock_movements
    FOR EACH ROW
EXECUTE FUNCTION adjust_stock_on_update();





