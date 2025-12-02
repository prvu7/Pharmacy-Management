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

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;
    private final PersonMapper mapper;

    // =====================================================================
    // CRUD operations
    // =====================================================================


    @Override
    public PersonDTO create(PersonRequestDTO request) {
        Person person = mapper.toEntity(request);
        Person saved = repository.save(person);
        return mapper.toDTO(saved);
    }

    @Override
    public PersonDTO getById(Long id) {
        Person person = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found: " + id));
        return mapper.toDTO(person);
    }

    @Override
    public List<PersonDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PersonDTO update(Long id, PersonRequestDTO request) {
        Person existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found: " + id));

        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setSex(request.getSex());
        existing.setDateOfBirth(request.getDateOfBirth());
        existing.setPhone(request.getPhone());
        existing.setEmail(request.getEmail());
        existing.setAddress(request.getAddress());
        existing.setRole(request.getRole());

        Person updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Person not found: " + id);
        }
        repository.deleteById(id);
    }
}

