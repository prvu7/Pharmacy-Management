package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.PurchaseDTO;
import com.mpp.pharmacy.Entity.Person;
import com.mpp.pharmacy.Entity.Pharmacy;
import com.mpp.pharmacy.Entity.Prescription;
import com.mpp.pharmacy.Entity.Purchase;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Mapper.PurchaseMapper;
import com.mpp.pharmacy.Repository.PersonRepository;
import com.mpp.pharmacy.Repository.PharmacyRepository;
import com.mpp.pharmacy.Repository.PrescriptionRepository;
import com.mpp.pharmacy.Repository.PurchaseRepository;
import com.mpp.pharmacy.RequestDTO.PurchaseRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PharmacyRepository pharmacyRepository;
    private final PersonRepository personRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PurchaseMapper mapper;

    @Override
    public PurchaseDTO create(PurchaseRequestDTO request) {
        log.info("Creating new purchase");

        Pharmacy pharmacy = pharmacyRepository.findById(request.getPharmacyId())
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacy not found"));

        Person patient = personRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Prescription prescription = null;
        if (request.getPrescriptionId() != null) {
            prescription = prescriptionRepository.findById(request.getPrescriptionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Prescription not found"));
        }

        Purchase purchase = mapper.fromRequest(request);
        purchase.setPharmacy(pharmacy);
        purchase.setPatient(patient);
        purchase.setPrescription(prescription);

        return mapper.toDTO(purchaseRepository.save(purchase));
    }

    @Override
    public PurchaseDTO getById(Long id) {
        Purchase entity = purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found"));
        return mapper.toDTO(entity);
    }

    @Override
    public List<PurchaseDTO> getAll() {
        return purchaseRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<PurchaseDTO> getByPatient(Long patientId) {
        return purchaseRepository.findByPatient_PersonId(patientId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<PurchaseDTO> getByPharmacy(Long pharmacyId) {
        return purchaseRepository.findByPharmacy_PharmacyId(pharmacyId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PurchaseDTO update(Long id, PurchaseRequestDTO request) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found"));

        Pharmacy pharmacy = pharmacyRepository.findById(request.getPharmacyId())
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacy not found"));

        Person patient = personRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Prescription prescription = null;
        if (request.getPrescriptionId() != null) {
            prescription = prescriptionRepository.findById(request.getPrescriptionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Prescription not found"));
        }

        purchase.setPharmacy(pharmacy);
        purchase.setPatient(patient);
        purchase.setPrescription(prescription);
        purchase.setPurchaseDate(request.getPurchaseDate());
        purchase.setTotalAmount(request.getTotalAmount());

        return mapper.toDTO(purchaseRepository.save(purchase));
    }

    @Override
    public void delete(Long id) {
        if (!purchaseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Purchase not found");
        }
        purchaseRepository.deleteById(id);
    }
}