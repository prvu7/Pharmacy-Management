package com.mpp.pharmacy.ServiceInterface;

import com.mpp.pharmacy.DTO.TreatmentDTO;
import com.mpp.pharmacy.RequestDTO.TreatmentRequestDTO;

import java.util.List;

public interface TreatmentService {
    TreatmentDTO create(TreatmentRequestDTO request);
    TreatmentDTO getById(Long id);
    List<TreatmentDTO> getAll();
    TreatmentDTO update(Long id, TreatmentRequestDTO request);
    void delete(Long id);
}