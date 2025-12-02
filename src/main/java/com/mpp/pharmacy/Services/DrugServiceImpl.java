package com.mpp.pharmacy.Services;


import com.mpp.pharmacy.DTO.DrugDTO;
import com.mpp.pharmacy.Entity.Drug;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Mapper.DrugMapper;
import com.mpp.pharmacy.Repository.DrugRepository;
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

    private final DrugRepository repository;
    private final DrugMapper mapper;
  
    // =====================================================================
    // CRUD operations
    // =====================================================================

    @Override
    public DrugDTO create(DrugRequestDTO request) {
        Drug drug = mapper.toEntity(request);
        Drug saved = repository.save(drug);
        return mapper.toDTO(saved);
    }

    @Override
    public DrugDTO getById(Long id) {
        Drug drug = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drug not found: " + id));
        return mapper.toDTO(drug);
    }

    @Override
    public List<DrugDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public DrugDTO update(Long id, DrugRequestDTO request) {
        Drug existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drug not found: " + id));

        existing.setDrugName(request.getDrugName());
        existing.setGenericName(request.getGenericName());
        existing.setDescription(request.getDescription());
        existing.setDosageForm(request.getDosageForm());
        existing.setManufacturer(request.getManufacturer());
        existing.setPrice(request.getPrice());

        Drug updated = repository.save(existing);

        return mapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Drug not found: " + id);
        }
        repository.deleteById(id);
    }
}
