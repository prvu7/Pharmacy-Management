package com.mpp.pharmacy.Repository;
import com.mpp.pharmacy.Entity.Drug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DrugRepository extends JpaRepository<Drug, Long> {

    boolean existsByDrugNameIgnoreCase(String drugName);

    Optional<Drug> findByDrugNameIgnoreCase(String drugName);

    List<Drug> findByDrugNameContainingIgnoreCase(String searchTerm);

    List<Drug> findByManufacturerIgnoreCase(String manufacturer);

    List<Drug> findByDosageFormIgnoreCase(String dosageForm);

    List<Drug> findByGenericNameIgnoreCase(String genericName);
}