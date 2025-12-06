package com.mpp.pharmacy.Repository;

import com.mpp.pharmacy.Entity.Person;
import com.mpp.pharmacy.Enum.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<Person> findByEmail(String email);

    Optional<Person> findByPhone(String phone);

    List<Person> findByRole(Role role);

    List<Person> findByFirstNameAndLastName(String firstName, String lastName);
}