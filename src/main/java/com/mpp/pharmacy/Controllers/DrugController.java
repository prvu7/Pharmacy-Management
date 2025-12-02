package com.mpp.pharmacy.Controllers;

import com.mpp.pharmacy.DTO.DrugDTO;
import com.mpp.pharmacy.RequestDTO.DrugRequestDTO;
import com.mpp.pharmacy.ServiceInterface.DrugService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drugs")
@RequiredArgsConstructor
public class DrugController {

    private final DrugService drugService;

    @PostMapping
    public ResponseEntity<DrugDTO> create(@RequestBody DrugRequestDTO request) {
        DrugDTO created = drugService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<DrugDTO>> getAllDrugs() {
        List<DrugDTO> drugs = drugService.getAll();
        return ResponseEntity.ok(drugs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DrugDTO> getById(@PathVariable Long id) {
        DrugDTO dto = drugService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DrugDTO> updateDrug(
            @PathVariable Long id,
            @RequestBody DrugRequestDTO request
    ) {
        return ResponseEntity.ok(drugService.update(id, request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrug(@PathVariable Long id) {
        drugService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}