package com.mpp.pharmacy.Controllers;

import com.mpp.pharmacy.DTO.PersonDTO;
import com.mpp.pharmacy.RequestDTO.PersonRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")

public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @PostMapping
    public PersonDTO create(@RequestBody PersonRequestDTO request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public PersonDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<PersonDTO> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public PersonDTO update(@PathVariable Long id, @RequestBody PersonRequestDTO request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}