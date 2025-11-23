package com.mpp.pharmacy.Services;


import com.mpp.pharmacy.DTO.DrugDTO;
import com.mpp.pharmacy.Entity.Drug;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Loggers.CustomLogger;
import com.mpp.pharmacy.Loggers.LogType;
import com.mpp.pharmacy.Mapper.DrugMapper;
import com.mpp.pharmacy.Repository.DrugRepository;
import com.mpp.pharmacy.RequestDTO.DrugRequestDTO;
import com.mpp.pharmacy.ServiceInterface.DrugService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DrugServiceImpl implements DrugService {

    private final DrugRepository repository;
    private final DrugMapper mapper;
    private final CustomLogger logger = CustomLogger.getInstance();

    // =====================================================================
    // CRUD operations
    // =====================================================================


    @Override
    public DrugDTO create(DrugRequestDTO request) {
        try{
            if (request == null) {
                throw new IllegalArgumentException("DrugRequestDTO cannot be null");
            }
            Drug drug = mapper.toEntity(request);
            if (drug == null) {
                throw new IllegalArgumentException("Mapped Drug entity cannot be null");
            }
            Drug saved = repository.save(drug);
            return mapper.toDTO(saved);
        } catch (DataAccessException e){
            logger.error(LogType.DATABASE_ERROR, "Database error while creating drug", e);
            throw e;
        } catch (IllegalArgumentException e){
            logger.error(LogType.VALIDATION_ERROR, "Invalid data provided for creating drug", e);
            throw e;
        } catch (Exception e) {
            logger.error(LogType.UNEXPECTED_ERROR, "Error creating drug", e);
            throw e;
        }

    }

    @Override
    public DrugDTO getById(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Invalid ID: " + id);
            }
            Drug drug = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Drug not found: " + id));
            return mapper.toDTO(drug);
        } catch (ResourceNotFoundException e){
            logger.error(LogType.INVENTORY, "Drug not found with ID: " + id, e);
            throw e;
        } catch (IllegalArgumentException e){
            logger.error(LogType.INVALID_REQUEST, "Invalid ID provided: " + id, e);
            throw e;
        } catch (Exception e){
            logger.error(LogType.UNEXPECTED_ERROR, "Error fetching drug by ID", e);
            throw e;
        }
    }

    @Override
    public List<DrugDTO> getAll() {
        try {
            List<DrugDTO> result = repository.findAll()
                    .stream()
                    .map(mapper::toDTO)
                    .toList();
            if (result.isEmpty()) {
                logger.warn(LogType.INVENTORY, "No drugs found in the database");
            }
            return result;
        } catch (DataAccessException e){
            logger.error(LogType.DATABASE_ERROR, "Database error while fetching all drugs", e);
            throw e;
        } catch (Exception e){
            logger.error(LogType.UNEXPECTED_ERROR, "Unexpected error while fetching all drugs", e);
            throw e;
        }
    }

    @Override
    public DrugDTO update(Long id, DrugRequestDTO request) {
        try{
            if (request == null) {
                throw new IllegalArgumentException("DrugRequestDTO cannot be null");
            }

            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Invalid ID: " + id);
            }

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

        } catch (IllegalArgumentException e){
            logger.error(LogType.VALIDATION_ERROR, "Error updating drug", e);
            throw e;
        } catch (DataAccessException e){
            logger.error(LogType.DATABASE_ERROR, "Database error updating drug", e);
            throw e;
        } catch (ResourceNotFoundException e){
            logger.error(LogType.INVENTORY, "Drug not found for update", e);
            throw e;
        } catch (Exception e){
            logger.error(LogType.UNEXPECTED_ERROR, "Unexpected error updating drug", e);
            throw e;
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (id == null || id <= 0) {
                logger.error(LogType.INVALID_REQUEST, "Invalid ID provided for deletion: " + id, null);
                throw new IllegalArgumentException("Invalid ID: " + id);
            }

            if (!repository.existsById(id)) {
                throw new ResourceNotFoundException("Drug not found: " + id);
            }
            repository.deleteById(id);
        } catch (IllegalArgumentException e){
            logger.error(LogType.VALIDATION_ERROR, "Error deleting drug", e);
            throw e;
        } catch (ResourceNotFoundException e) {
            logger.error(LogType.INVENTORY, "Error deleting drug", e);
            throw e;
        } catch (DataAccessException e){
            logger.error(LogType.DATABASE_ERROR, "Database error deleting drug", e);
            throw e;
        } catch (Exception e) {
            logger.error(LogType.VALIDATION_ERROR, "Unexpected error deleting drug", e);
            throw e;
        }
    }
}
