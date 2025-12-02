package com.mpp.pharmacy.Repository;
import com.mpp.pharmacy.Entity.Drug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DrugRepository extends JpaRepository<Drug, Long> {
    /**
     * Check if a drug with the given name exists (case-insensitive)
     * Used for duplicate checking during create/update
     */
    boolean existsByDrugNameIgnoreCase(String drugName);

    /**
     * Find drug by exact name (case-insensitive)
     * Useful for searching specific drugs
     */
    Optional<Drug> findByDrugNameIgnoreCase(String drugName);

    /**
     * Search drugs by name containing the search term (case-insensitive)
     * Useful for autocomplete/search functionality
     */
    List<Drug> findByDrugNameContainingIgnoreCase(String searchTerm);

    /**
     * Find drugs by manufacturer (case-insensitive)
     * Useful for filtering by manufacturer
     */
    List<Drug> findByManufacturerIgnoreCase(String manufacturer);

    /**
     * Find drugs by dosage form (case-insensitive)
     * Useful for filtering by form (tablet, capsule, etc.)
     */
    List<Drug> findByDosageFormIgnoreCase(String dosageForm);

    /**
     * Find drugs by generic name (case-insensitive)
     * Useful for finding alternatives
     */
    List<Drug> findByGenericNameIgnoreCase(String genericName);
}