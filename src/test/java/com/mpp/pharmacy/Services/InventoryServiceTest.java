package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.InventoryDTO;
import com.mpp.pharmacy.Entity.Drug;
import com.mpp.pharmacy.Entity.Inventory;
import com.mpp.pharmacy.Entity.Pharmacy;
import com.mpp.pharmacy.RequestDTO.InventoryRequestDTO;
import com.mpp.pharmacy.Domain.InventoryDomain;
import com.mpp.pharmacy.Mapper.InventoryMapper;
import com.mpp.pharmacy.RequestDTO.InventoryRequestDTO;
import com.mpp.pharmacy.Domain.InventoryDomain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    private InventoryDomain inventoryDomain;

    @Mock
    private InventoryMapper mapper;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private InventoryRequestDTO request;
    private Inventory inventory;
    private InventoryDTO inventoryDTO;
    private Pharmacy pharmacy;
    private Drug drug;

    @BeforeEach
    void setUp() {
        pharmacy = Pharmacy.builder()
                .pharmacyId(1L)
                .name("Central Pharmacy")
                .build();

        drug = Drug.builder()
                .drugId(2L)
                .drugName("Aspirin")
                .build();

        request = InventoryRequestDTO.builder()
                .pharmacyId(1L)
                .drugId(2L)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2026, 12, 31))
                .build();

        inventory = Inventory.builder()
                .inventoryId(1L)
                .pharmacy(pharmacy)
                .drug(drug)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2026, 12, 31))
                .build();

        inventoryDTO = InventoryDTO.builder()
                .inventoryId(1L)
                .pharmacyId(1L)
                .drugId(2L)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2026, 12, 31))
                .build();
    }

    @Test
    void create_ShouldReturnCreatedInventory() {
        // Arrange
        when(inventoryDomain.create(request)).thenReturn(inventory);
        when(mapper.toDTO(inventory)).thenReturn(inventoryDTO);

        // Act
        InventoryDTO result = inventoryService.create(request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getInventoryId());
        assertEquals(1L, result.getPharmacyId());
        assertEquals(2L, result.getDrugId());
        assertEquals(100, result.getQuantityInStock());
        assertEquals(LocalDate.of(2026, 12, 31), result.getExpiryDate());
        verify(inventoryDomain, times(1)).create(request);
        verify(mapper, times(1)).toDTO(inventory);
    }

    @Test
    void create_WithNullRequest_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.create(null)
        );

        assertEquals("Request cannot be null", exception.getMessage());
        verify(inventoryDomain, never()).create(any());
    }

    @Test
    void getById_ShouldReturnInventory() {
        // Arrange
        Long inventoryId = 1L;
        when(inventoryDomain.getById(inventoryId)).thenReturn(inventory);
        when(mapper.toDTO(inventory)).thenReturn(inventoryDTO);

        // Act
        InventoryDTO result = inventoryService.getById(inventoryId);

        // Assert
        assertNotNull(result);
        assertEquals(inventoryId, result.getInventoryId());
        assertEquals(1L, result.getPharmacyId());
        assertEquals(2L, result.getDrugId());
        assertEquals(100, result.getQuantityInStock());
        verify(inventoryDomain, times(1)).getById(inventoryId);
        verify(mapper, times(1)).toDTO(inventory);
    }

    @Test
    void getById_WithNullId_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.getById(null)
        );

        assertEquals("ID cannot be null", exception.getMessage());
        verify(inventoryDomain, never()).getById(any());
    }

    @Test
    void getAll_ShouldReturnAllInventories() {
        // Arrange
        Inventory inventory2 = Inventory.builder()
                .inventoryId(2L)
                .pharmacy(pharmacy)
                .drug(drug)
                .quantityInStock(50)
                .expiryDate(LocalDate.of(2026, 11, 30))
                .build();

        InventoryDTO dto2 = InventoryDTO.builder()
                .inventoryId(2L)
                .pharmacyId(1L)
                .drugId(2L)
                .quantityInStock(50)
                .expiryDate(LocalDate.of(2026, 11, 30))
                .build();

        when(inventoryDomain.getAll()).thenReturn(List.of(inventory, inventory2));
        when(mapper.toDTO(inventory)).thenReturn(inventoryDTO);
        when(mapper.toDTO(inventory2)).thenReturn(dto2);

        // Act
        List<InventoryDTO> result = inventoryService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getInventoryId());
        assertEquals(2L, result.get(1).getInventoryId());
        verify(inventoryDomain, times(1)).getAll();
        verify(mapper, times(2)).toDTO(any(Inventory.class));
    }

    @Test
    void getByPharmacy_ShouldReturnInventoriesForPharmacy() {
        // Arrange
        Long pharmacyId = 1L;

        Inventory inventory2 = Inventory.builder()
                .inventoryId(2L)
                .pharmacy(pharmacy)
                .drug(drug)
                .quantityInStock(50)
                .expiryDate(LocalDate.of(2026, 11, 30))
                .build();

        InventoryDTO dto2 = InventoryDTO.builder()
                .inventoryId(2L)
                .pharmacyId(pharmacyId)
                .drugId(2L)
                .quantityInStock(50)
                .expiryDate(LocalDate.of(2026, 11, 30))
                .build();

        when(inventoryDomain.getByPharmacy(pharmacyId)).thenReturn(List.of(inventory, inventory2));
        when(mapper.toDTO(inventory)).thenReturn(inventoryDTO);
        when(mapper.toDTO(inventory2)).thenReturn(dto2);

        // Act
        List<InventoryDTO> result = inventoryService.getByPharmacy(pharmacyId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(pharmacyId, result.get(0).getPharmacyId());
        assertEquals(pharmacyId, result.get(1).getPharmacyId());
        verify(inventoryDomain, times(1)).getByPharmacy(pharmacyId);
        verify(mapper, times(2)).toDTO(any(Inventory.class));
    }

    @Test
    void getByPharmacy_WithNullId_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.getByPharmacy(null)
        );

        assertEquals("Pharmacy ID cannot be null", exception.getMessage());
        verify(inventoryDomain, never()).getByPharmacy(any());
    }

    @Test
    void getByDrug_ShouldReturnInventoriesForDrug() {
        // Arrange
        Long drugId = 2L;

        Pharmacy pharmacy2 = Pharmacy.builder()
                .pharmacyId(2L)
                .name("North Pharmacy")
                .build();

        Inventory inventory2 = Inventory.builder()
                .inventoryId(2L)
                .pharmacy(pharmacy2)
                .drug(drug)
                .quantityInStock(75)
                .expiryDate(LocalDate.of(2026, 11, 30))
                .build();

        InventoryDTO dto2 = InventoryDTO.builder()
                .inventoryId(2L)
                .pharmacyId(2L)
                .drugId(drugId)
                .quantityInStock(75)
                .expiryDate(LocalDate.of(2026, 11, 30))
                .build();

        when(inventoryDomain.getByDrug(drugId)).thenReturn(List.of(inventory, inventory2));
        when(mapper.toDTO(inventory)).thenReturn(inventoryDTO);
        when(mapper.toDTO(inventory2)).thenReturn(dto2);

        // Act
        List<InventoryDTO> result = inventoryService.getByDrug(drugId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(drugId, result.get(0).getDrugId());
        assertEquals(drugId, result.get(1).getDrugId());
        verify(inventoryDomain, times(1)).getByDrug(drugId);
        verify(mapper, times(2)).toDTO(any(Inventory.class));
    }

    @Test
    void getByDrug_WithNullId_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.getByDrug(null)
        );

        assertEquals("Drug ID cannot be null", exception.getMessage());
        verify(inventoryDomain, never()).getByDrug(any());
    }

    @Test
    void update_ShouldReturnUpdatedInventory() {
        // Arrange
        Long inventoryId = 1L;

        InventoryRequestDTO updateRequest = InventoryRequestDTO.builder()
                .pharmacyId(1L)
                .drugId(2L)
                .quantityInStock(150)
                .expiryDate(LocalDate.of(2027, 6, 30))
                .build();

        Inventory updatedInventory = Inventory.builder()
                .inventoryId(inventoryId)
                .pharmacy(pharmacy)
                .drug(drug)
                .quantityInStock(150)
                .expiryDate(LocalDate.of(2027, 6, 30))
                .build();

        InventoryDTO updatedDTO = InventoryDTO.builder()
                .inventoryId(inventoryId)
                .pharmacyId(1L)
                .drugId(2L)
                .quantityInStock(150)
                .expiryDate(LocalDate.of(2027, 6, 30))
                .build();

        when(inventoryDomain.update(inventoryId, updateRequest)).thenReturn(updatedInventory);
        when(mapper.toDTO(updatedInventory)).thenReturn(updatedDTO);

        // Act
        InventoryDTO result = inventoryService.update(inventoryId, updateRequest);

        // Assert
        assertNotNull(result);
        assertEquals(inventoryId, result.getInventoryId());
        assertEquals(150, result.getQuantityInStock());
        assertEquals(LocalDate.of(2027, 6, 30), result.getExpiryDate());
        verify(inventoryDomain, times(1)).update(inventoryId, updateRequest);
        verify(mapper, times(1)).toDTO(updatedInventory);
    }

    @Test
    void update_WithNullId_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.update(null, request)
        );

        assertEquals("ID cannot be null", exception.getMessage());
        verify(inventoryDomain, never()).update(any(), any());
    }

    @Test
    void update_WithNullRequest_ShouldThrowException() {
        // Arrange
        Long inventoryId = 1L;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.update(inventoryId, null)
        );

        assertEquals("Request cannot be null", exception.getMessage());
        verify(inventoryDomain, never()).update(any(), any());
    }

    @Test
    void delete_ShouldDeleteInventory() {
        // Arrange
        Long inventoryId = 1L;
        doNothing().when(inventoryDomain).delete(inventoryId);

        // Act
        inventoryService.delete(inventoryId);

        // Assert
        verify(inventoryDomain, times(1)).delete(inventoryId);
    }

    @Test
    void delete_WithNullId_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.delete(null)
        );

        assertEquals("ID cannot be null", exception.getMessage());
        verify(inventoryDomain, never()).delete(any());
    }
}