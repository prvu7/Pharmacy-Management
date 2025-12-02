package com.mpp.pharmacy.ServiceInterface;

import com.mpp.pharmacy.DTO.DrugDTO;
import com.mpp.pharmacy.RequestDTO.DrugRequestDTO;

import java.util.List;

import java.util.List;

public interface DrugService {

    DrugDTO create(DrugRequestDTO request);

    DrugDTO getById(Long id);

    List<DrugDTO> getAll();

    DrugDTO update(Long id, DrugRequestDTO request);

    void delete(Long id);
}