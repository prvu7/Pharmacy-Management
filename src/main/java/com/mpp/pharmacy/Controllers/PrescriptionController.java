package com.mpp.pharmacy.Controllers;

import com.mpp.pharmacy.DTO.PrescriptionDTO;
import com.mpp.pharmacy.DTO.PrescriptionDetailDTO;
import com.mpp.pharmacy.RequestDTO.PrescriptionDetailRequestDTO;
import com.mpp.pharmacy.RequestDTO.PrescriptionRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PrescriptionDetailService;
import com.mpp.pharmacy.ServiceInterface.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<PrescriptionDTO> createPrescription(@RequestBody PrescriptionRequestDTO request) {
        PrescriptionDTO prescription = prescriptionService.create(request);
        return ResponseEntity.ok(prescription);
    }

    @GetMapping("/prescriptions")
    public ResponseEntity<List<PrescriptionDTO>> getAllPrescriptions() {
        List<PrescriptionDTO> prescriptions = prescriptionService.getAll();
        return ResponseEntity.ok(prescriptions);
    }

    @GetMapping("/prescriptions/{id}")
    public ResponseEntity<PrescriptionDTO> getPrescription(@PathVariable Long id) {
        PrescriptionDTO prescription = prescriptionService.getById(id);
        return ResponseEntity.ok(prescription);
    }

    @PutMapping("/prescriptions/{id}")
    public ResponseEntity <PrescriptionDTO>  updatePrescription(
            @PathVariable Long id,
            @RequestBody PrescriptionRequestDTO request
    ) {
        return ResponseEntity.ok(prescriptionService.update(id, request));
    }

    @DeleteMapping("/prescriptions/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        prescriptionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /* =====================================================
           PRESCRIPTION DETAILS CRUD
           (Composite Key: prescriptionId + drugId)
       ===================================================== */

    @PostMapping("/prescription-details")
    public ResponseEntity<PrescriptionDetailDTO> createPrescriptionDetail(
            @RequestBody PrescriptionDetailRequestDTO request
    ) {
        PrescriptionDetailDTO prescriptionDetail = prescriptionDetailService.create(request);
        return ResponseEntity.ok(prescriptionDetail);
    }

    @GetMapping("/prescription-details")
    public ResponseEntity<List<PrescriptionDetailDTO> >getAllPrescriptionDetails() {
        List< PrescriptionDetailDTO> prescriptionDetails = prescriptionDetailService.getAll();
        return ResponseEntity.ok(prescriptionDetails);
    }

    @GetMapping("/prescription-details/{prescriptionId}/{drugId}")
    public ResponseEntity<PrescriptionDetailDTO> getPrescriptionDetail(
            @PathVariable Long prescriptionId,
            @PathVariable Long drugId
    ) {
        PrescriptionDetailDTO prescriptionDetail = prescriptionDetailService.get(prescriptionId, drugId);
        return ResponseEntity.ok(prescriptionDetail);
    }

    @GetMapping("/prescriptions/{prescriptionId}/details")
    public ResponseEntity<List<PrescriptionDetailDTO>> getDetailsByPrescription(
            @PathVariable Long prescriptionId
    ) {
        List<PrescriptionDetailDTO> prescriptionDetailDTOList = prescriptionDetailService.getByPrescription(prescriptionId);
        return ResponseEntity.ok(prescriptionDetailDTOList);
    }

    @PutMapping("/prescription-details/{prescriptionId}/{drugId}")
    public ResponseEntity<PrescriptionDetailDTO> updatePrescriptionDetail(
            @PathVariable Long prescriptionId,
            @PathVariable Long drugId,
            @RequestBody PrescriptionDetailRequestDTO request
    ) {
        return ResponseEntity.ok(prescriptionDetailService.update(prescriptionId, drugId, request));
    }

    @DeleteMapping("/prescription-details/{prescriptionId}/{drugId}")
    public ResponseEntity<Void> deletePrescriptionDetail(
            @PathVariable Long prescriptionId,
            @PathVariable Long drugId
    ) {
        prescriptionDetailService.delete(prescriptionId, drugId);
        return ResponseEntity.noContent().build();
    }

  }