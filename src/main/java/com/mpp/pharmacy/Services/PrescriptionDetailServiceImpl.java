package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.PrescriptionDetailDTO;
import com.mpp.pharmacy.Entity.Drug;
import com.mpp.pharmacy.Entity.Prescription;
import com.mpp.pharmacy.Entity.PrescriptionDetailId;
import com.mpp.pharmacy.Entity.Prescription_Detail;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Mapper.PrescriptionDetailMapper;
import com.mpp.pharmacy.Repository.DrugRepository;
import com.mpp.pharmacy.Repository.PrescriptionDetailRepository;
import com.mpp.pharmacy.Repository.PrescriptionRepository;
import com.mpp.pharmacy.RequestDTO.PrescriptionDetailRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PrescriptionDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrescriptionDetailServiceImpl implements PrescriptionDetailService {

    private final PrescriptionDetailRepository repository;
    private final PrescriptionRepository prescriptionRepository;
    private final DrugRepository drugRepository;
    private final PrescriptionDetailMapper mapper;

    @Override
    public PrescriptionDetailDTO create(PrescriptionDetailRequestDTO request) {
        Prescription prescription = prescriptionRepository.findById(request.getPrescriptionId())
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found"));

        Drug drug = drugRepository.findById(request.getDrugId())
                .orElseThrow(() -> new ResourceNotFoundException("Drug not found"));

        Prescription_Detail entity = mapper.fromRequest(request);
        entity.setPrescription(prescription);
        entity.setDrug(drug);

        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public PrescriptionDetailDTO get(Long prescriptionId, Long drugId) {
        PrescriptionDetailId id = new PrescriptionDetailId(prescriptionId, drugId);

        Prescription_Detail entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription detail not found"));

        return mapper.toDTO(entity);
    }

    @Override
    public List<PrescriptionDetailDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<PrescriptionDetailDTO> getByPrescription(Long prescriptionId) {
        return repository.findByPrescription_PrescriptionId(prescriptionId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PrescriptionDetailDTO update(Long prescriptionId, Long drugId, PrescriptionDetailRequestDTO request) {
        PrescriptionDetailId id = new PrescriptionDetailId(prescriptionId, drugId);

        Prescription_Detail entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription detail not found"));

        entity.setDosage(request.getDosage());
        entity.setDurationDays(request.getDurationDays());
        entity.setQuantity(request.getQuantity());

        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Long prescriptionId, Long drugId) {
        PrescriptionDetailId id = new PrescriptionDetailId(prescriptionId, drugId);

        if (!repository.existsById(id))
            throw new ResourceNotFoundException("Prescription detail not found");

        repository.deleteById(id);
    }
}