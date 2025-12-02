package com.mpp.pharmacy.Services;
import com.mpp.pharmacy.DTO.PharmacyDTO;
import com.mpp.pharmacy.Entity.Pharmacy;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Mapper.PharmacyMapper;
import com.mpp.pharmacy.Repository.PharmacyRepository;
import com.mpp.pharmacy.RequestDTO.PharmacyRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PharmacyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository repository;
    private final PharmacyMapper mapper;

    @Override
    public PharmacyDTO create(PharmacyRequestDTO request) {
        Pharmacy pharmacy = mapper.fromRequest(request);
        return mapper.toDTO(repository.save(pharmacy));
    }

    @Override
    public PharmacyDTO getById(Long id) {
        Pharmacy pharmacy = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacy not found: " + id));
        return mapper.toDTO(pharmacy);
    }

    @Override
    public List<PharmacyDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PharmacyDTO update(Long id, PharmacyRequestDTO request) {
        Pharmacy pharmacy = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacy not found: " + id));

        pharmacy.setName(request.getName());
        pharmacy.setAddress(request.getAddress());
        pharmacy.setPhone(request.getPhone());

        return mapper.toDTO(repository.save(pharmacy));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Pharmacy not found: " + id);
        }
        repository.deleteById(id);
    }
}