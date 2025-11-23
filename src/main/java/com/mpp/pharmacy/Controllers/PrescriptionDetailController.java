package com.mpp.pharmacy.Controllers;

import com.mpp.pharmacy.DTO.PrescriptionDetailDTO;
import com.mpp.pharmacy.RequestDTO.PrescriptionDetailRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PrescriptionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescription-details")
@RequiredArgsConstructor
public class PrescriptionDetailController {

    private final PrescriptionDetailService service;

    @PostMapping
    public PrescriptionDetailDTO create(@RequestBody PrescriptionDetailRequestDTO request) {
        return service.create(request);
    }

    @GetMapping
    public List<PrescriptionDetailDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{prescriptionId}/{drugId}")
    public PrescriptionDetailDTO get(
            @PathVariable Long prescriptionId,
            @PathVariable Long drugId) {
        return service.get(prescriptionId, drugId);
    }

    @PutMapping("/{prescriptionId}/{drugId}")
    public PrescriptionDetailDTO update(
            @PathVariable Long prescriptionId,
            @PathVariable Long drugId,
            @RequestBody PrescriptionDetailRequestDTO request) {
        return service.update(prescriptionId, drugId, request);
    }

    @DeleteMapping("/{prescriptionId}/{drugId}")
    public void delete(
            @PathVariable Long prescriptionId,
            @PathVariable Long drugId) {
        service.delete(prescriptionId, drugId);
    }
}