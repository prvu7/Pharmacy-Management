package com.mpp.pharmacy.ServiceInterface;

import com.mpp.pharmacy.DTO.PersonDTO;
import com.mpp.pharmacy.Entity.Person;
import com.mpp.pharmacy.RequestDTO.PersonRequestDTO;

import java.util.List;

public interface PersonService {
    PersonDTO create(PersonRequestDTO request);

    PersonDTO getById(Long id);

    List<PersonDTO> getAll();

    PersonDTO update(Long id, PersonRequestDTO request);

    void delete(Long id);
}
