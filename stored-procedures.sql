
-- Create Drug
CREATE OR REPLACE FUNCTION create_drug(
    p_drug_name VARCHAR(100),
    p_generic_name VARCHAR(100),
    p_description TEXT,
    p_dosage_form VARCHAR(50),
    p_manufacturer VARCHAR(100),
    p_price NUMERIC(10,2)
)
RETURNS TABLE(
    drug_id BIGINT,
    drug_name VARCHAR(100),
    generic_name VARCHAR(100),
    description TEXT,
    dosage_form VARCHAR(50),
    manufacturer VARCHAR(100),
    price NUMERIC(10,2)
) AS $$
BEGIN
    RETURN QUERY
    INSERT INTO drug (drug_name, generic_name, description, dosage_form, manufacturer, price)
    VALUES (p_drug_name, p_generic_name, p_description, p_dosage_form, p_manufacturer, p_price)
    RETURNING drug.drug_id, drug.drug_name, drug.generic_name, drug.description, 
              drug.dosage_form, drug.manufacturer, drug.price;
END;
$$ LANGUAGE plpgsql;

-- Get Drug by ID
CREATE OR REPLACE FUNCTION get_drug_by_id(p_drug_id BIGINT)
RETURNS TABLE(
    drug_id BIGINT,
    drug_name VARCHAR(100),
    generic_name VARCHAR(100),
    description TEXT,
    dosage_form VARCHAR(50),
    manufacturer VARCHAR(100),
    price NUMERIC(10,2)
) AS $$
BEGIN
    RETURN QUERY
    SELECT d.drug_id, d.drug_name, d.generic_name, d.description, 
           d.dosage_form, d.manufacturer, d.price
    FROM drug d
    WHERE d.drug_id = p_drug_id;
END;
$$ LANGUAGE plpgsql;

-- Get All Drugs
CREATE OR REPLACE FUNCTION get_all_drugs()
RETURNS TABLE(
    drug_id BIGINT,
    drug_name VARCHAR(100),
    generic_name VARCHAR(100),
    description TEXT,
    dosage_form VARCHAR(50),
    manufacturer VARCHAR(100),
    price NUMERIC(10,2)
) AS $$
BEGIN
    RETURN QUERY
    SELECT d.drug_id, d.drug_name, d.generic_name, d.description, 
           d.dosage_form, d.manufacturer, d.price
    FROM drug d
    ORDER BY d.drug_id;
END;
$$ LANGUAGE plpgsql;

-- Update Drug
CREATE OR REPLACE FUNCTION update_drug(
    p_drug_id BIGINT,
    p_drug_name VARCHAR(100),
    p_generic_name VARCHAR(100),
    p_description TEXT,
    p_dosage_form VARCHAR(50),
    p_manufacturer VARCHAR(100),
    p_price NUMERIC(10,2)
)
RETURNS TABLE(
    drug_id BIGINT,
    drug_name VARCHAR(100),
    generic_name VARCHAR(100),
    description TEXT,
    dosage_form VARCHAR(50),
    manufacturer VARCHAR(100),
    price NUMERIC(10,2)
) AS $$
BEGIN
    RETURN QUERY
    UPDATE drug
    SET drug_name = p_drug_name,
        generic_name = p_generic_name,
        description = p_description,
        dosage_form = p_dosage_form,
        manufacturer = p_manufacturer,
        price = p_price
    WHERE drug.drug_id = p_drug_id
    RETURNING drug.drug_id, drug.drug_name, drug.generic_name, drug.description, 
              drug.dosage_form, drug.manufacturer, drug.price;
END;
$$ LANGUAGE plpgsql;

-- Delete Drug
CREATE OR REPLACE FUNCTION delete_drug(p_drug_id BIGINT)
RETURNS BOOLEAN AS $$
DECLARE
    rows_deleted INTEGER;
BEGIN
    DELETE FROM drug WHERE drug_id = p_drug_id;
    GET DIAGNOSTICS rows_deleted = ROW_COUNT;
    RETURN rows_deleted > 0;
END;
$$ LANGUAGE plpgsql;

-- Check if Drug exists by ID
CREATE OR REPLACE FUNCTION exists_drug_by_id(p_drug_id BIGINT)
RETURNS BOOLEAN AS $$
BEGIN
    RETURN EXISTS(SELECT 1 FROM drug WHERE drug_id = p_drug_id);
END;
$$ LANGUAGE plpgsql;

-- Check if Drug exists by name (case-insensitive)
CREATE OR REPLACE FUNCTION exists_drug_by_name(p_drug_name VARCHAR(100))
RETURNS BOOLEAN AS $$
BEGIN
    RETURN EXISTS(SELECT 1 FROM drug WHERE LOWER(drug_name) = LOWER(p_drug_name));
END;
$$ LANGUAGE plpgsql;

-- Find Drug by name (case-insensitive)
CREATE OR REPLACE FUNCTION find_drug_by_name(p_drug_name VARCHAR(100))
RETURNS TABLE(
    drug_id BIGINT,
    drug_name VARCHAR(100),
    generic_name VARCHAR(100),
    description TEXT,
    dosage_form VARCHAR(50),
    manufacturer VARCHAR(100),
    price NUMERIC(10,2)
) AS $$
BEGIN
    RETURN QUERY
    SELECT d.drug_id, d.drug_name, d.generic_name, d.description, 
           d.dosage_form, d.manufacturer, d.price
    FROM drug d
    WHERE LOWER(d.drug_name) = LOWER(p_drug_name);
END;
$$ LANGUAGE plpgsql;

-- Find Drugs by name containing (case-insensitive)
CREATE OR REPLACE FUNCTION find_drugs_by_name_containing(p_search_term VARCHAR(100))
RETURNS TABLE(
    drug_id BIGINT,
    drug_name VARCHAR(100),
    generic_name VARCHAR(100),
    description TEXT,
    dosage_form VARCHAR(50),
    manufacturer VARCHAR(100),
    price NUMERIC(10,2)
) AS $$
BEGIN
    RETURN QUERY
    SELECT d.drug_id, d.drug_name, d.generic_name, d.description, 
           d.dosage_form, d.manufacturer, d.price
    FROM drug d
    WHERE LOWER(d.drug_name) LIKE LOWER('%' || p_search_term || '%')
    ORDER BY d.drug_id;
END;
$$ LANGUAGE plpgsql;

-- Find Drugs by manufacturer (case-insensitive)
CREATE OR REPLACE FUNCTION find_drugs_by_manufacturer(p_manufacturer VARCHAR(100))
RETURNS TABLE(
    drug_id BIGINT,
    drug_name VARCHAR(100),
    generic_name VARCHAR(100),
    description TEXT,
    dosage_form VARCHAR(50),
    manufacturer VARCHAR(100),
    price NUMERIC(10,2)
) AS $$
BEGIN
    RETURN QUERY
    SELECT d.drug_id, d.drug_name, d.generic_name, d.description, 
           d.dosage_form, d.manufacturer, d.price
    FROM drug d
    WHERE LOWER(d.manufacturer) = LOWER(p_manufacturer)
    ORDER BY d.drug_id;
END;
$$ LANGUAGE plpgsql;

-- Find Drugs by dosage form (case-insensitive)
CREATE OR REPLACE FUNCTION find_drugs_by_dosage_form(p_dosage_form VARCHAR(50))
RETURNS TABLE(
    drug_id BIGINT,
    drug_name VARCHAR(100),
    generic_name VARCHAR(100),
    description TEXT,
    dosage_form VARCHAR(50),
    manufacturer VARCHAR(100),
    price NUMERIC(10,2)
) AS $$
BEGIN
    RETURN QUERY
    SELECT d.drug_id, d.drug_name, d.generic_name, d.description, 
           d.dosage_form, d.manufacturer, d.price
    FROM drug d
    WHERE LOWER(d.dosage_form) = LOWER(p_dosage_form)
    ORDER BY d.drug_id;
END;
$$ LANGUAGE plpgsql;

-- Find Drugs by generic name (case-insensitive)
CREATE OR REPLACE FUNCTION find_drugs_by_generic_name(p_generic_name VARCHAR(100))
RETURNS TABLE(
    drug_id BIGINT,
    drug_name VARCHAR(100),
    generic_name VARCHAR(100),
    description TEXT,
    dosage_form VARCHAR(50),
    manufacturer VARCHAR(100),
    price NUMERIC(10,2)
) AS $$
BEGIN
    RETURN QUERY
    SELECT d.drug_id, d.drug_name, d.generic_name, d.description, 
           d.dosage_form, d.manufacturer, d.price
    FROM drug d
    WHERE LOWER(d.generic_name) = LOWER(p_generic_name)
    ORDER BY d.drug_id;
END;
$$ LANGUAGE plpgsql;