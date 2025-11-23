package com.mpp.pharmacy.Repository;

import com.mpp.pharmacy.Entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
}