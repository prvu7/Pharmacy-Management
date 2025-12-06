//package com.mpp.pharmacy.Controllers;
//
//import com.mpp.pharmacy.DTO.TreatmentDTO;
//import com.mpp.pharmacy.RequestDTO.TreatmentRequestDTO;
//import com.mpp.pharmacy.ServiceInterface.TreatmentService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/treatment")
//public class TreatmentController {
//    private final TreatmentService treatmentService;
//
//    public TreatmentController(TreatmentService treatmentService) {
//        this.treatmentService = treatmentService;
//    }
//
//    @PostMapping
//    public ResponseEntity<TreatmentDTO> create(@RequestBody TreatmentRequestDTO request) {
//        TreatmentDTO treatment = treatmentService.create(request);
//        return ResponseEntity.ok().body(treatment);
//    }
//
//    @GetMapping
//    public ResponseEntity<TreatmentDTO> getByTreatmentId(@PathVariable Long id) {
//        TreatmentDTO treatment = treatmentService.getById(id);
//        return ResponseEntity.ok().body(treatment);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<TreatmentDTO>> getTreatments() {
//        List<TreatmentDTO> treatments = treatmentService.getAll();
//        return ResponseEntity.ok().body(treatments);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<TreatmentDTO> update(@PathVariable Long id, @RequestBody TreatmentRequestDTO request) {
//        return ResponseEntity.ok(treatmentService.update(id, request));
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        treatmentService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
//}
