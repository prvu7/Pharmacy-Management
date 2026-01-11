package com.mpp.pharmacy.Repository.Interface;

import com.mpp.pharmacy.Entity.Drug;
import java.util.Optional;
import java.util.List;

public interface IDrugRepository {
    Drug save(Drug drug);
    Optional<Drug> findById(Long id);
    List<Drug> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByDrugNameIgnoreCase(String drugName);
    Optional<Drug> findByDrugNameIgnoreCase(String drugName);
    List<Drug> findByDrugNameContainingIgnoreCase(String searchTerm);
    List<Drug> findByManufacturerIgnoreCase(String manufacturer);
    List<Drug> findByDosageFormIgnoreCase(String dosageForm);
    List<Drug> findByGenericNameIgnoreCase(String genericName);
}
