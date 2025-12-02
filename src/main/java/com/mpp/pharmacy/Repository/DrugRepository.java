package com.mpp.pharmacy.Repository;
import com.mpp.pharmacy.Entity.Drug;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrugRepository extends JpaRepository<Drug, Long> {
}