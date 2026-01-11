package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.PrescriptionDetailDTO;
import com.mpp.pharmacy.Domain.PrescriptionDetailDomain;
import com.mpp.pharmacy.Entity.Prescription_Detail;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import com.mpp.pharmacy.Mapper.PrescriptionDetailMapper;
import com.mpp.pharmacy.Repository.PrescriptionDetailRepository;
import com.mpp.pharmacy.RequestDTO.PrescriptionDetailRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PrescriptionDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PrescriptionDetailServiceImpl implements PrescriptionDetailService {

    private final PrescriptionDetailMapper mapper;
    private final PrescriptionDetailDomain prescriptionDetailDomain;

    @Override
    public PrescriptionDetailDTO create(PrescriptionDetailRequestDTO request) {
        if (request == null) {
            throw new InvalidRequestException("Prescription detail request cannot be null");
        }

        Prescription_Detail created = prescriptionDetailDomain.create(request);
        return mapper.toDTO(created);
    }

    @Override
    public PrescriptionDetailDTO get(Long prescriptionId, Long drugId) {
        if (prescriptionId == null) {
            throw new InvalidRequestException("Prescription ID cannot be null");
        }
        if (drugId == null) {
            throw new InvalidRequestException("Drug ID cannot be null");
        }

        Prescription_Detail detail = prescriptionDetailDomain.get(prescriptionId, drugId);
        return mapper.toDTO(detail);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionDetailDTO> getAll() {
        return prescriptionDetailDomain.getAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionDetailDTO> getByPrescription(Long prescriptionId) {
        if (prescriptionId == null) {
            throw new InvalidRequestException("Prescription ID cannot be null");
        }

        return prescriptionDetailDomain.getByPrescription(prescriptionId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PrescriptionDetailDTO update(Long prescriptionId, Long drugId, PrescriptionDetailRequestDTO request) {
        if (prescriptionId == null) {
            throw new InvalidRequestException("Prescription ID cannot be null");
        }
        if (drugId == null) {
            throw new InvalidRequestException("Drug ID cannot be null");
        }
        if (request == null) {
            throw new InvalidRequestException("Prescription detail request cannot be null");
        }

        Prescription_Detail updated = prescriptionDetailDomain.update(prescriptionId, drugId, request);
        return mapper.toDTO(updated);
    }

    @Override
    public void delete(Long prescriptionId, Long drugId) {
        if (prescriptionId == null) {
            throw new InvalidRequestException("Prescription ID cannot be null");
        }
        if (drugId == null) {
            throw new InvalidRequestException("Drug ID cannot be null");
        }

        prescriptionDetailDomain.delete(prescriptionId, drugId);
    }
}