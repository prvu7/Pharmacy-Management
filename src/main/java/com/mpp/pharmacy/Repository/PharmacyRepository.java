package com.mpp.pharmacy.Repository;

import com.mpp.pharmacy.Entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByPhone(String phone);

    Optional<Pharmacy> findByNameIgnoreCase(String name);

    Optional<Pharmacy> findByPhone(String phone);

    Optional<Pharmacy> findByAddressIgnoreCase(String address);

    List<Pharmacy> findByNameContainingIgnoreCase(String searchTerm);

    List<Pharmacy> findByAddressContainingIgnoreCase(String searchTerm);
}