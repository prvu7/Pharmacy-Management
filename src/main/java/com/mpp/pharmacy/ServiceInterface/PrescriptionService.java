package com.mpp.pharmacy.ServiceInterface;

import com.mpp.pharmacy.DTO.PrescriptionDTO;
import com.mpp.pharmacy.RequestDTO.PrescriptionRequestDTO;

import java.util.List;

public interface PrescriptionService {

    PrescriptionDTO create(PrescriptionRequestDTO request);
    PrescriptionDTO getById(Long id);
    List<PrescriptionDTO> getAll();
    PrescriptionDTO update(Long id, PrescriptionRequestDTO request);
    void delete(Long id);
}