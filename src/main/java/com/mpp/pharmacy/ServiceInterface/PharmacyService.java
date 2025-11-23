package com.mpp.pharmacy.ServiceInterface;


import com.mpp.pharmacy.DTO.PharmacyDTO;
import com.mpp.pharmacy.RequestDTO.PharmacyRequestDTO;

import java.util.List;

public interface PharmacyService {

    PharmacyDTO create(PharmacyRequestDTO request);

    PharmacyDTO getById(Long id);

    List<PharmacyDTO> getAll();

    PharmacyDTO update(Long id, PharmacyRequestDTO request);

    void delete(Long id);
}