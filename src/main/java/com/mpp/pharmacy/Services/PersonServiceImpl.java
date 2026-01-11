package com.mpp.pharmacy.Services;
import com.mpp.pharmacy.DTO.PersonDTO;
import com.mpp.pharmacy.Entity.Person;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Mapper.PersonMapper;
import com.mpp.pharmacy.Repository.PersonRepository;
import com.mpp.pharmacy.RequestDTO.PersonRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.mpp.pharmacy.Domain.PersonDomain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonDomain domain;
    private final PersonMapper mapper;

    // =====================================================================
    // CRUD operations
    // =====================================================================


    @Override
    public PersonDTO create(PersonRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException("Request data cannot be null");
        }

        Person person = domain.create(request);

        return mapper.toDTO(person);
    }

    @Override
    public PersonDTO getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        Person person = domain.getById(id);

        return mapper.toDTO(person);
    }

    @Override
    public List<PersonDTO> getAll() {
        return domain.getAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PersonDTO update(Long id, PersonRequestDTO request) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (request == null) {
            throw new IllegalArgumentException("Request data cannot be null");
        }

        Person updatedPerson = domain.update(id, request);

        return mapper.toDTO(updatedPerson);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        domain.delete(id);
    }
}

