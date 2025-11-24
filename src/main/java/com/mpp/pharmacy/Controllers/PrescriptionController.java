package com.mpp.pharmacy.Controllers;

import com.mpp.pharmacy.DTO.PrescriptionDTO;
import com.mpp.pharmacy.DTO.PrescriptionDetailDTO;
import com.mpp.pharmacy.RequestDTO.PrescriptionDetailRequestDTO;
import com.mpp.pharmacy.RequestDTO.PrescriptionRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PrescriptionDetailService;
import com.mpp.pharmacy.ServiceInterface.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final PrescriptionDetailService prescriptionDetailService;

    /* =====================================================
         PRESCRIPTIONS CRUD
       ===================================================== */

    @PostMapping("/prescriptions")
    public PrescriptionDTO createPrescription(@RequestBody PrescriptionRequestDTO request) {
        return prescriptionService.create(request);
    }

    @GetMapping("/prescriptions")
    public List<PrescriptionDTO> getAllPrescriptions() {
        return prescriptionService.getAll();
    }

    @GetMapping("/prescriptions/{id}")
    public PrescriptionDTO getPrescription(@PathVariable Long id) {
        return prescriptionService.getById(id);
    }

    @PutMapping("/prescriptions/{id}")
    public PrescriptionDTO updatePrescription(
            @PathVariable Long id,
            @RequestBody PrescriptionRequestDTO request
    ) {
        return prescriptionService.update(id, request);
    }

    @DeleteMapping("/prescriptions/{id}")
    public void deletePrescription(@PathVariable Long id) {
        prescriptionService.delete(id);
    }

    /* =====================================================
           PRESCRIPTION DETAILS CRUD
           (Composite Key: prescriptionId + drugId)
       ===================================================== */

    @PostMapping("/prescription-details")
    public PrescriptionDetailDTO createPrescriptionDetail(
            @RequestBody PrescriptionDetailRequestDTO request
    ) {
        return prescriptionDetailService.create(request);
    }

    @GetMapping("/prescription-details")
    public List<PrescriptionDetailDTO> getAllPrescriptionDetails() {
        return prescriptionDetailService.getAll();
    }

    @GetMapping("/prescription-details/{prescriptionId}/{drugId}")
    public PrescriptionDetailDTO getPrescriptionDetail(
            @PathVariable Long prescriptionId,
            @PathVariable Long drugId
    ) {
        return prescriptionDetailService.get(prescriptionId, drugId);
    }

    @GetMapping("/prescriptions/{prescriptionId}/details")
    public List<PrescriptionDetailDTO> getDetailsByPrescription(
            @PathVariable Long prescriptionId
    ) {
        return prescriptionDetailService.getByPrescription(prescriptionId);
    }

    @PutMapping("/prescription-details/{prescriptionId}/{drugId}")
    public PrescriptionDetailDTO updatePrescriptionDetail(
            @PathVariable Long prescriptionId,
            @PathVariable Long drugId,
            @RequestBody PrescriptionDetailRequestDTO request
    ) {
        return prescriptionDetailService.update(prescriptionId, drugId, request);
    }

    @DeleteMapping("/prescription-details/{prescriptionId}/{drugId}")
    public void deletePrescriptionDetail(
            @PathVariable Long prescriptionId,
            @PathVariable Long drugId
    ) {
        prescriptionDetailService.delete(prescriptionId, drugId);
    }

  }