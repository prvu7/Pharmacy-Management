package com.mpp.pharmacy.Services;
import com.mpp.pharmacy.DTO.PersonDTO;
import com.mpp.pharmacy.Domain.PersonDomain;
import com.mpp.pharmacy.Entity.Person;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import com.mpp.pharmacy.Mapper.PersonMapper;
import com.mpp.pharmacy.Repository.PersonRepository;
import com.mpp.pharmacy.RequestDTO.PersonRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PersonService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;
    private final PersonMapper mapper;
    private final PersonDomain personDomain;

    // =====================================================================
    // CRUD operations
    // =====================================================================


    @Override
    public PersonDTO create(PersonRequestDTO request) {
        log.info("Service: Creating person");

        // Validate request is not null
        if (request == null) {
            throw new InvalidRequestException("Person request cannot be null");
        }

        Person created = personDomain.create(request);
        return mapper.toDTO(created);
    }

    @Override
    public PersonDTO getById(Long id) {
        log.debug("Service: Fetching person with id: {}", id);

        // Validate ID is not null
        if (id == null) {
            throw new InvalidRequestException("Person ID cannot be null");
        }

        Person person = personDomain.getById(id);
        return mapper.toDTO(person);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonDTO> getAll() {
        log.debug("Service: Fetching all persons");
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PersonDTO update(Long id, PersonRequestDTO request) {
        log.info("Service: Updating person with id: {}", id);

        // Validate inputs are not null
        if (id == null) {
            throw new InvalidRequestException("Person ID cannot be null");
        }
        if (request == null) {
            throw new InvalidRequestException("Person request cannot be null");
        }

        Person updated = personDomain.update(id, request);
        return mapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        log.info("Service: Deleting person with id: {}", id);

        // Validate ID is not null
        if (id == null) {
            throw new InvalidRequestException("Person ID cannot be null");
        }

        personDomain.delete(id);
    }
}

