package com.mpp.pharmacy.Controllers;

import com.mpp.pharmacy.DTO.TreatmentDTO;
import com.mpp.pharmacy.RequestDTO.TreatmentRequestDTO;
import com.mpp.pharmacy.ServiceInterface.TreatmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/treatment")
public class TreatmentController {
    private final TreatmentService treatmentService;

    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @PostMapping
    public TreatmentDTO create(@RequestBody TreatmentRequestDTO request) {
        return treatmentService.create(request);
    }

    @GetMapping
    public TreatmentDTO getByTreatmentId(@PathVariable Long id) {
        return treatmentService.getById(id);
    }

    @GetMapping
    public List<TreatmentDTO> getTreatments() {
        return treatmentService.getAll();
    }

    @PutMapping("/{id}")
    public TreatmentDTO update(@PathVariable Long id, @RequestBody TreatmentRequestDTO request) {
        return treatmentService.update(id, request);
    }

    @DeleteMapping
    public void delete(@PathVariable Long id) {
        treatmentService.delete(id);
    }
}
