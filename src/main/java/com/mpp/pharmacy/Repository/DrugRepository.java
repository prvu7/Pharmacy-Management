// File: DrugRepositoryImpl.java
package com.mpp.pharmacy.Repository;

import com.mpp.pharmacy.Entity.Drug;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.mpp.pharmacy.Repository.Interface.IDrugRepository;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DrugRepository implements IDrugRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Drug> DRUG_ROW_MAPPER = (rs, rowNum) -> {
        Drug drug = new Drug();
        drug.setDrugId(rs.getLong("drug_id"));
        drug.setDrugName(rs.getString("drug_name"));
        drug.setGenericName(rs.getString("generic_name"));
        drug.setDescription(rs.getString("description"));
        drug.setDosageForm(rs.getString("dosage_form"));
        drug.setManufacturer(rs.getString("manufacturer"));
        drug.setPrice(rs.getBigDecimal("price"));
        return drug;
    };

    @Override
    public Drug save(Drug drug) {
        if (drug.getDrugId() == null) {
            return create(drug);
        } else {
            return update(drug);
        }
    }

    private Drug create(Drug drug) {
        String sql = "SELECT * FROM create_drug(?, ?, ?, ?, ?, ?)";
        
        List<Drug> results = jdbcTemplate.query(sql, DRUG_ROW_MAPPER,
                drug.getDrugName(),
                drug.getGenericName(),
                drug.getDescription(),
                drug.getDosageForm(),
                drug.getManufacturer(),
                drug.getPrice()
        );
        
        if (results.isEmpty()) {
            throw new RuntimeException("Failed to create drug");
        }
        
        return results.get(0);
    }

    private Drug update(Drug drug) {
        String sql = "SELECT * FROM update_drug(?, ?, ?, ?, ?, ?, ?)";
        
        List<Drug> results = jdbcTemplate.query(sql, DRUG_ROW_MAPPER,
                drug.getDrugId(),
                drug.getDrugName(),
                drug.getGenericName(),
                drug.getDescription(),
                drug.getDosageForm(),
                drug.getManufacturer(),
                drug.getPrice()
        );
        
        if (results.isEmpty()) {
            throw new RuntimeException("Failed to update drug with id: " + drug.getDrugId());
        }
        
        return results.get(0);
    }

    @Override
    public Optional<Drug> findById(Long id) {
        String sql = "SELECT * FROM get_drug_by_id(?)";
        
        List<Drug> results = jdbcTemplate.query(sql, DRUG_ROW_MAPPER, id);
        
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<Drug> findAll() {
        String sql = "SELECT * FROM get_all_drugs()";
        
        return jdbcTemplate.query(sql, DRUG_ROW_MAPPER);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "SELECT delete_drug(?)";
        
        Boolean deleted = jdbcTemplate.queryForObject(sql, Boolean.class, id);
        
        if (deleted == null || !deleted) {
            throw new RuntimeException("Failed to delete drug with id: " + id);
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT exists_drug_by_id(?)";
        
        Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, id);
        
        return exists != null && exists;
    }

    @Override
    public boolean existsByDrugNameIgnoreCase(String drugName) {
        String sql = "SELECT exists_drug_by_name(?)";
        
        Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, drugName);
        
        return exists != null && exists;
    }

    @Override
    public Optional<Drug> findByDrugNameIgnoreCase(String drugName) {
        String sql = "SELECT * FROM find_drug_by_name(?)";
        
        List<Drug> results = jdbcTemplate.query(sql, DRUG_ROW_MAPPER, drugName);
        
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<Drug> findByDrugNameContainingIgnoreCase(String searchTerm) {
        String sql = "SELECT * FROM find_drugs_by_name_containing(?)";
        
        return jdbcTemplate.query(sql, DRUG_ROW_MAPPER, searchTerm);
    }

    @Override
    public List<Drug> findByManufacturerIgnoreCase(String manufacturer) {
        String sql = "SELECT * FROM find_drugs_by_manufacturer(?)";
        
        return jdbcTemplate.query(sql, DRUG_ROW_MAPPER, manufacturer);
    }

    @Override
    public List<Drug> findByDosageFormIgnoreCase(String dosageForm) {
        String sql = "SELECT * FROM find_drugs_by_dosage_form(?)";
        
        return jdbcTemplate.query(sql, DRUG_ROW_MAPPER, dosageForm);
    }

    @Override
    public List<Drug> findByGenericNameIgnoreCase(String genericName) {
        String sql = "SELECT * FROM find_drugs_by_generic_name(?)";
        
        return jdbcTemplate.query(sql, DRUG_ROW_MAPPER, genericName);
    }
}