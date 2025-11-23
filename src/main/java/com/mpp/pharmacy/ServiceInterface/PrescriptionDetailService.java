package com.mpp.pharmacy.ServiceInterface;

import com.mpp.pharmacy.DTO.PrescriptionDetailDTO;
import com.mpp.pharmacy.RequestDTO.PrescriptionDetailRequestDTO;

import java.util.List;

public interface PrescriptionDetailService {

    PrescriptionDetailDTO create(PrescriptionDetailRequestDTO request);

    PrescriptionDetailDTO get(Long prescriptionId, Long drugId);

    List<PrescriptionDetailDTO> getAll();

    List<PrescriptionDetailDTO> getByPrescription(Long prescriptionId);

    PrescriptionDetailDTO update(Long prescriptionId, Long drugId, PrescriptionDetailRequestDTO request);

    void delete(Long prescriptionId, Long drugId);
}