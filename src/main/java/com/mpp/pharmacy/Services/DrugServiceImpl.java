package com.mpp.pharmacy.Services;


import com.mpp.pharmacy.DTO.DrugDTO;
import com.mpp.pharmacy.Entity.Drug;
import com.mpp.pharmacy.Mapper.DrugMapper;
import com.mpp.pharmacy.Domain.DrugDomain;
import com.mpp.pharmacy.RequestDTO.DrugRequestDTO;
import com.mpp.pharmacy.ServiceInterface.DrugService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DrugServiceImpl implements DrugService {

    private final DrugDomain domain;
    private final DrugMapper mapper;

    // =====================================================================
    // CRUD operations
    // =====================================================================


    @Override
    public DrugDTO create(DrugRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        Drug response = domain.create(request);
        
        return mapper.toDTO(response);
    }

    @Override
    public DrugDTO getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        Drug response = domain.getById(id);

        return mapper.toDTO(response);
    }

    @Override
    public List<DrugDTO> getAll() {
        return domain.getAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public DrugDTO update(Long id, DrugRequestDTO request) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        Drug response =  domain.update(id, request);

        return mapper.toDTO(response);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        domain.delete(id);
    }
}
