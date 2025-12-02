package com.mpp.pharmacy.Controllers;

import com.mpp.pharmacy.DTO.PersonDTO;
import com.mpp.pharmacy.RequestDTO.PersonRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<PersonDTO> create(@RequestBody PersonRequestDTO request) {
        PersonDTO person = service.create(request);
        return ResponseEntity.ok().body(person);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getById(@PathVariable Long id) {
        PersonDTO person = service.getById(id);
        return ResponseEntity.ok().body(person);
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAll() {

        List<PersonDTO> persons = service.getAll();
        return ResponseEntity.ok().body(persons);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> update(@PathVariable Long id, @RequestBody PersonRequestDTO request) {

        return ResponseEntity.ok().body(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}