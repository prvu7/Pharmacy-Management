package com.mpp.pharmacy.Services;


import com.mpp.pharmacy.DTO.DrugDTO;
import com.mpp.pharmacy.Domain.DrugDomain;
import com.mpp.pharmacy.Entity.Drug;
import com.mpp.pharmacy.Mapper.DrugMapper;
import com.mpp.pharmacy.Repository.DrugRepository;
import com.mpp.pharmacy.RequestDTO.DrugRequestDTO;
import com.mpp.pharmacy.ServiceInterface.DrugService;
import com.mpp.pharmacy.Validators.DrugValidator;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DrugServiceImpl implements DrugService {

    private final DrugRepository repository;
    private final DrugMapper mapper;
    private final DrugValidator validator;
    private final DrugDomain drugDomain;

    // =====================================================================
    // CRUD operations
    // =====================================================================


    @Override
    public DrugDTO create(DrugRequestDTO request) {

        if(request!= null)
        {
            Drug drug = mapper.toEntity(request);
            validator.validateForCreation(drug);
            Drug created = drugDomain.create(request);

            return mapper.toDTO(created);
        }
       return null;
    }

    @Override
    public DrugDTO update(Long id, DrugRequestDTO request) {
        if (id != null && request != null) {
            Drug drug = mapper.toEntity(request);
            drug.setDrugId(id);
            validator.validateForUpdate(drug);
            Drug updated = drugDomain.update(id, request);

            return mapper.toDTO(updated);
        }
        return null;
    }

    @Override
    public DrugDTO getById(Long id) {
        if (id!=null) {
            Drug drug = drugDomain.getById(id);
            return mapper.toDTO(drug);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DrugDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if(id!=null){
            drugDomain.delete(id);
        }
    }
}