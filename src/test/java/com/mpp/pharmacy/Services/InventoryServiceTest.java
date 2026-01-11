package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.InventoryDTO;
import com.mpp.pharmacy.Entity.Drug;
import com.mpp.pharmacy.Entity.Inventory;
import com.mpp.pharmacy.Entity.Pharmacy;
import com.mpp.pharmacy.Mapper.InventoryMapper;
import com.mpp.pharmacy.Repository.DrugRepository;
import com.mpp.pharmacy.Repository.InventoryRepository;
import com.mpp.pharmacy.Repository.PharmacyRepository;
import com.mpp.pharmacy.RequestDTO.InventoryRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private PharmacyRepository pharmacyRepository;

    @Mock
    private DrugRepository drugRepository;

    @Mock
    private InventoryMapper mapper;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @Test
    void create_ShouldReturnCreatedInventory() {
        // Arrange
        InventoryRequestDTO request = InventoryRequestDTO.builder()
                .pharmacyId(1L)
                .drugId(2L)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2026, 12, 31))
                .build();

        Pharmacy pharmacy = Pharmacy.builder()
                .pharmacyId(1L)
                .name("Central Pharmacy")
                .build();

        Drug drug = Drug.builder()
                .drugId(2L)
                .drugName("Aspirin")
                .build();

        Inventory inventory = Inventory.builder()
                .pharmacy(pharmacy)
                .drug(drug)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2026, 12, 31))
                .build();

        Inventory savedInventory = Inventory.builder()
                .inventoryId(1L)
                .pharmacy(pharmacy)
                .drug(drug)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2026, 12, 31))
                .build();

        InventoryDTO expectedDTO = InventoryDTO.builder()
                .inventoryId(1L)
                .pharmacyId(1L)
                .drugId(2L)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2026, 12, 31))
                .build();

        when(pharmacyRepository.findById(1L)).thenReturn(Optional.of(pharmacy));
        when(drugRepository.findById(2L)).thenReturn(Optional.of(drug));
        when(mapper.fromRequest(any(InventoryRequestDTO.class))).thenReturn(inventory);
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(savedInventory);
        when(mapper.toDTO(any(Inventory.class))).thenReturn(expectedDTO);

        // Act
        InventoryDTO result = inventoryService.create(request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getInventoryId());
        assertEquals(1L, result.getPharmacyId());
        assertEquals(2L, result.getDrugId());
        assertEquals(100, result.getQuantityInStock());
        assertEquals(LocalDate.of(2026, 12, 31), result.getExpiryDate());
    }

    @Test
    void getById_ShouldReturnInventory() {
        // Arrange
        Long inventoryId = 1L;

        Pharmacy pharmacy = Pharmacy.builder()
                .pharmacyId(1L)
                .name("Central Pharmacy")
                .build();

        Drug drug = Drug.builder()
                .drugId(2L)
                .drugName("Aspirin")
                .build();

        Inventory inventory = Inventory.builder()
                .inventoryId(inventoryId)
                .pharmacy(pharmacy)
                .drug(drug)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2026, 12, 31))
                .build();

        InventoryDTO expectedDTO = InventoryDTO.builder()
                .inventoryId(inventoryId)
                .pharmacyId(1L)
                .drugId(2L)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2026, 12, 31))
                .build();

        when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.of(inventory));
        when(mapper.toDTO(any(Inventory.class))).thenReturn(expectedDTO);

        // Act
        InventoryDTO result = inventoryService.getById(inventoryId);

        // Assert
        assertNotNull(result);
        assertEquals(inventoryId, result.getInventoryId());
        assertEquals(1L, result.getPharmacyId());
        assertEquals(2L, result.getDrugId());
        assertEquals(100, result.getQuantityInStock());
    }

    @Test
    void getAll_ShouldReturnAllInventories() {
        // Arrange
        Pharmacy pharmacy = Pharmacy.builder()
                .pharmacyId(1L)
                .name("Central Pharmacy")
                .build();

        Drug drug1 = Drug.builder()
                .drugId(2L)
                .drugName("Aspirin")
                .build();

        Drug drug2 = Drug.builder()
                .drugId(3L)
                .drugName("Ibuprofen")
                .build();

        Inventory inventory1 = Inventory.builder()
                .inventoryId(1L)
                .pharmacy(pharmacy)
                .drug(drug1)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2026, 12, 31))
                .build();

        Inventory inventory2 = Inventory.builder()
                .inventoryId(2L)
                .pharmacy(pharmacy)
                .drug(drug2)
                .quantityInStock(50)
                .expiryDate(LocalDate.of(2026, 11, 30))
                .build();

        InventoryDTO dto1 = InventoryDTO.builder()
                .inventoryId(1L)
                .pharmacyId(1L)
                .drugId(2L)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2026, 12, 31))
                .build();

        InventoryDTO dto2 = InventoryDTO.builder()
                .inventoryId(2L)
                .pharmacyId(1L)
                .drugId(3L)
                .quantityInStock(50)
                .expiryDate(LocalDate.of(2026, 11, 30))
                .build();

        when(inventoryRepository.findAll()).thenReturn(List.of(inventory1, inventory2));
        when(mapper.toDTO(inventory1)).thenReturn(dto1);
        when(mapper.toDTO(inventory2)).thenReturn(dto2);

        // Act
        List<InventoryDTO> result = inventoryService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getInventoryId());
        assertEquals(2L, result.get(1).getInventoryId());
    }

    @Test
    void getByPharmacy_ShouldReturnInventoriesForPharmacy() {
        // Arrange
        Long pharmacyId = 1L;

        Pharmacy pharmacy = Pharmacy.builder()
                .pharmacyId(pharmacyId)
                .name("Central Pharmacy")
                .build();

        Drug drug1 = Drug.builder()
                .drugId(2L)
                .drugName("Aspirin")
                .build();

        Drug drug2 = Drug.builder()
                .drugId(3L)
                .drugName("Ibuprofen")
                .build();

        Inventory inventory1 = Inventory.builder()
                .inventoryId(1L)
                .pharmacy(pharmacy)
                .drug(drug1)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2026, 12, 31))
                .build();

        Inventory inventory2 = Inventory.builder()
                .inventoryId(2L)
                .pharmacy(pharmacy)
                .drug(drug2)
                .quantityInStock(50)
                .expiryDate(LocalDate.of(2026, 11, 30))
                .build();

        InventoryDTO dto1 = InventoryDTO.builder()
                .inventoryId(1L)
                .pharmacyId(pharmacyId)
                .drugId(2L)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2026, 12, 31))
                .build();

        InventoryDTO dto2 = InventoryDTO.builder()
                .inventoryId(2L)
                .pharmacyId(pharmacyId)
                .drugId(3L)
                .quantityInStock(50)
                .expiryDate(LocalDate.of(2026, 11, 30))
                .build();

        when(inventoryRepository.findByPharmacy_PharmacyId(pharmacyId)).thenReturn(List.of(inventory1, inventory2));
        when(mapper.toDTO(inventory1)).thenReturn(dto1);
        when(mapper.toDTO(inventory2)).thenReturn(dto2);

        // Act
        List<InventoryDTO> result = inventoryService.getByPharmacy(pharmacyId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(pharmacyId, result.get(0).getPharmacyId());
        assertEquals(pharmacyId, result.get(1).getPharmacyId());
    }

    @Test
    void getByDrug_ShouldReturnInventoriesForDrug() {
        // Arrange
        Long drugId = 2L;

        Pharmacy pharmacy1 = Pharmacy.builder()
                .pharmacyId(1L)
                .name("Central Pharmacy")
                .build();

        Pharmacy pharmacy2 = Pharmacy.builder()
                .pharmacyId(2L)
                .name("North Pharmacy")
                .build();

        Drug drug = Drug.builder()
                .drugId(drugId)
                .drugName("Aspirin")
                .build();

        Inventory inventory1 = Inventory.builder()
                .inventoryId(1L)
                .pharmacy(pharmacy1)
                .drug(drug)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2026, 12, 31))
                .build();

        Inventory inventory2 = Inventory.builder()
                .inventoryId(2L)
                .pharmacy(pharmacy2)
                .drug(drug)
                .quantityInStock(75)
                .expiryDate(LocalDate.of(2026, 11, 30))
                .build();

        InventoryDTO dto1 = InventoryDTO.builder()
                .inventoryId(1L)
                .pharmacyId(1L)
                .drugId(drugId)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2026, 12, 31))
                .build();

        InventoryDTO dto2 = InventoryDTO.builder()
                .inventoryId(2L)
                .pharmacyId(2L)
                .drugId(drugId)
                .quantityInStock(75)
                .expiryDate(LocalDate.of(2026, 11, 30))
                .build();

        when(inventoryRepository.findByDrug_DrugId(drugId)).thenReturn(List.of(inventory1, inventory2));
        when(mapper.toDTO(inventory1)).thenReturn(dto1);
        when(mapper.toDTO(inventory2)).thenReturn(dto2);

        // Act
        List<InventoryDTO> result = inventoryService.getByDrug(drugId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(drugId, result.get(0).getDrugId());
        assertEquals(drugId, result.get(1).getDrugId());
    }

    @Test
    void update_ShouldReturnUpdatedInventory() {
        // Arrange
        Long inventoryId = 1L;

        InventoryRequestDTO request = InventoryRequestDTO.builder()
                .pharmacyId(1L)
                .drugId(2L)
                .quantityInStock(150)
                .expiryDate(LocalDate.of(2027, 6, 30))
                .build();

        Pharmacy pharmacy = Pharmacy.builder()
                .pharmacyId(1L)
                .name("Central Pharmacy")
                .build();

        Drug drug = Drug.builder()
                .drugId(2L)
                .drugName("Aspirin")
                .build();

        Inventory existingInventory = Inventory.builder()
                .inventoryId(inventoryId)
                .pharmacy(pharmacy)
                .drug(drug)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2026, 12, 31))
                .build();

        Inventory updatedInventory = Inventory.builder()
                .inventoryId(inventoryId)
                .pharmacy(pharmacy)
                .drug(drug)
                .quantityInStock(150)
                .expiryDate(LocalDate.of(2027, 6, 30))
                .build();

        InventoryDTO expectedDTO = InventoryDTO.builder()
                .inventoryId(inventoryId)
                .pharmacyId(1L)
                .drugId(2L)
                .quantityInStock(150)
                .expiryDate(LocalDate.of(2027, 6, 30))
                .build();

        when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.of(existingInventory));
        when(pharmacyRepository.findById(1L)).thenReturn(Optional.of(pharmacy));
        when(drugRepository.findById(2L)).thenReturn(Optional.of(drug));
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(updatedInventory);
        when(mapper.toDTO(any(Inventory.class))).thenReturn(expectedDTO);

        // Act
        InventoryDTO result = inventoryService.update(inventoryId, request);

        // Assert
        assertNotNull(result);
        assertEquals(inventoryId, result.getInventoryId());
        assertEquals(150, result.getQuantityInStock());
        assertEquals(LocalDate.of(2027, 6, 30), result.getExpiryDate());
    }

    @Test
    void delete_ShouldDeleteInventory() {
        // Arrange
        Long inventoryId = 1L;

        when(inventoryRepository.existsById(inventoryId)).thenReturn(true);
        doNothing().when(inventoryRepository).deleteById(inventoryId);

        // Act
        inventoryService.delete(inventoryId);

        // Assert
        verify(inventoryRepository, times(1)).existsById(inventoryId);
        verify(inventoryRepository, times(1)).deleteById(inventoryId);
    }
}
