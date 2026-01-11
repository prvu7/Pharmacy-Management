package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.InventoryDTO;
import com.mpp.pharmacy.Entity.Drug;
import com.mpp.pharmacy.Entity.Inventory;
import com.mpp.pharmacy.Entity.Pharmacy;
import com.mpp.pharmacy.RequestDTO.InventoryRequestDTO;
import com.mpp.pharmacy.Domain.InventoryDomain;
import com.mpp.pharmacy.Mapper.InventoryMapper;
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

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    private InventoryDomain inventoryDomain;

    @Mock
    private InventoryMapper inventoryMapper;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private InventoryRequestDTO request;
    private Inventory inventoryEntity;
    private InventoryDTO inventoryDTO;
    private Pharmacy pharmacy;
    private Drug drug;

    @BeforeEach
    void setUp() {
        request = InventoryRequestDTO.builder()
                .pharmacyId(10L)
                .drugId(20L)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2025, 12, 31))
                .build();

        pharmacy = Pharmacy.builder()
                .pharmacyId(10L)
                .name("Test Pharmacy")
                .build();

        drug = Drug.builder()
                .drugId(20L)
                .drugName("Test Drug")
                .build();

        inventoryEntity = Inventory.builder()
                .inventoryId(1L)
                .pharmacy(pharmacy)
                .drug(drug)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2025, 12, 31))
                .build();

        inventoryDTO = InventoryDTO.builder()
                .inventoryId(1L)
                .pharmacyId(10L)
                .drugId(20L)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2025, 12, 31))
                .build();
    }

    @Test
    void createInventory_ShouldReturnInventoryDTO() {
        when(inventoryDomain.create(request)).thenReturn(inventoryEntity);
        when(inventoryMapper.toDTO(inventoryEntity)).thenReturn(inventoryDTO);

        InventoryDTO result = inventoryService.create(request);

        assertNotNull(result);
        assertEquals(inventoryDTO.getInventoryId(), result.getInventoryId());
        assertEquals(inventoryDTO.getPharmacyId(), result.getPharmacyId());
        assertEquals(inventoryDTO.getDrugId(), result.getDrugId());
        assertEquals(inventoryDTO.getQuantityInStock(), result.getQuantityInStock());
        verify(inventoryDomain, times(1)).create(request);
        verify(inventoryMapper, times(1)).toDTO(inventoryEntity);
    }

    @Test
    void createInventory_WithNullRequest_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.create(null)
        );

        assertEquals("Request cannot be null", exception.getMessage());
        verify(inventoryDomain, never()).create(any());
    }

    @Test
    void getById_ShouldReturnInventoryDTO() {
        Long inventoryId = 1L;
        when(inventoryDomain.getById(inventoryId)).thenReturn(inventoryEntity);
        when(inventoryMapper.toDTO(inventoryEntity)).thenReturn(inventoryDTO);

        InventoryDTO result = inventoryService.getById(inventoryId);

        assertNotNull(result);
        assertEquals(inventoryDTO.getInventoryId(), result.getInventoryId());
        assertEquals(inventoryDTO.getPharmacyId(), result.getPharmacyId());
        assertEquals(inventoryDTO.getDrugId(), result.getDrugId());
        verify(inventoryDomain, times(1)).getById(inventoryId);
        verify(inventoryMapper, times(1)).toDTO(inventoryEntity);
    }

    @Test
    void getById_WithNullId_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.getById(null)
        );

        assertEquals("ID cannot be null", exception.getMessage());
        verify(inventoryDomain, never()).getById(any());
    }

    @Test
    void getAll_ShouldReturnListOfInventoryDTOs() {
        Pharmacy pharmacy2 = Pharmacy.builder()
                .pharmacyId(15L)
                .name("Second Pharmacy")
                .build();

        Drug drug2 = Drug.builder()
                .drugId(25L)
                .drugName("Second Drug")
                .build();

        Inventory inventory2 = Inventory.builder()
                .inventoryId(2L)
                .pharmacy(pharmacy2)
                .drug(drug2)
                .quantityInStock(50)
                .expiryDate(LocalDate.of(2026, 6, 30))
                .build();

        InventoryDTO inventoryDTO2 = InventoryDTO.builder()
                .inventoryId(2L)
                .pharmacyId(15L)
                .drugId(25L)
                .quantityInStock(50)
                .expiryDate(LocalDate.of(2026, 6, 30))
                .build();

        List<Inventory> inventories = List.of(inventoryEntity, inventory2);
        when(inventoryDomain.getAll()).thenReturn(inventories);
        when(inventoryMapper.toDTO(inventoryEntity)).thenReturn(inventoryDTO);
        when(inventoryMapper.toDTO(inventory2)).thenReturn(inventoryDTO2);

        List<InventoryDTO> result = inventoryService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(inventoryDTO.getInventoryId(), result.get(0).getInventoryId());
        assertEquals(inventoryDTO2.getInventoryId(), result.get(1).getInventoryId());
        verify(inventoryDomain, times(1)).getAll();
        verify(inventoryMapper, times(2)).toDTO(any(Inventory.class));
    }

    @Test
    void getByPharmacy_ShouldReturnListOfInventoryDTOs() {
        Long pharmacyId = 10L;

        Drug drug2 = Drug.builder()
                .drugId(30L)
                .drugName("Third Drug")
                .build();

        Inventory inventory2 = Inventory.builder()
                .inventoryId(3L)
                .pharmacy(pharmacy)
                .drug(drug2)
                .quantityInStock(75)
                .expiryDate(LocalDate.of(2026, 3, 15))
                .build();

        InventoryDTO inventoryDTO2 = InventoryDTO.builder()
                .inventoryId(3L)
                .pharmacyId(pharmacyId)
                .drugId(30L)
                .quantityInStock(75)
                .expiryDate(LocalDate.of(2026, 3, 15))
                .build();

        List<Inventory> inventories = List.of(inventoryEntity, inventory2);
        when(inventoryDomain.getByPharmacy(pharmacyId)).thenReturn(inventories);
        when(inventoryMapper.toDTO(inventoryEntity)).thenReturn(inventoryDTO);
        when(inventoryMapper.toDTO(inventory2)).thenReturn(inventoryDTO2);

        List<InventoryDTO> result = inventoryService.getByPharmacy(pharmacyId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(pharmacyId, result.get(0).getPharmacyId());
        assertEquals(pharmacyId, result.get(1).getPharmacyId());
        verify(inventoryDomain, times(1)).getByPharmacy(pharmacyId);
        verify(inventoryMapper, times(2)).toDTO(any(Inventory.class));
    }

    @Test
    void getByPharmacy_WithNullPharmacyId_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.getByPharmacy(null)
        );

        assertEquals("Pharmacy ID cannot be null", exception.getMessage());
        verify(inventoryDomain, never()).getByPharmacy(any());
    }

    @Test
    void getByDrug_ShouldReturnListOfInventoryDTOs() {
        Long drugId = 20L;

        Pharmacy pharmacy2 = Pharmacy.builder()
                .pharmacyId(15L)
                .name("Second Pharmacy")
                .build();

        Inventory inventory2 = Inventory.builder()
                .inventoryId(4L)
                .pharmacy(pharmacy2)
                .drug(drug)
                .quantityInStock(60)
                .expiryDate(LocalDate.of(2026, 8, 20))
                .build();

        InventoryDTO inventoryDTO2 = InventoryDTO.builder()
                .inventoryId(4L)
                .pharmacyId(15L)
                .drugId(drugId)
                .quantityInStock(60)
                .expiryDate(LocalDate.of(2026, 8, 20))
                .build();

        List<Inventory> inventories = List.of(inventoryEntity, inventory2);
        when(inventoryDomain.getByDrug(drugId)).thenReturn(inventories);
        when(inventoryMapper.toDTO(inventoryEntity)).thenReturn(inventoryDTO);
        when(inventoryMapper.toDTO(inventory2)).thenReturn(inventoryDTO2);

        List<InventoryDTO> result = inventoryService.getByDrug(drugId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(drugId, result.get(0).getDrugId());
        assertEquals(drugId, result.get(1).getDrugId());
        verify(inventoryDomain, times(1)).getByDrug(drugId);
        verify(inventoryMapper, times(2)).toDTO(any(Inventory.class));
    }

    @Test
    void getByDrug_WithNullDrugId_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.getByDrug(null)
        );

        assertEquals("Drug ID cannot be null", exception.getMessage());
        verify(inventoryDomain, never()).getByDrug(any());
    }

    @Test
    void update_ShouldReturnUpdatedInventoryDTO() {
        Long inventoryId = 1L;

        InventoryRequestDTO updateRequest = InventoryRequestDTO.builder()
                .pharmacyId(10L)
                .drugId(20L)
                .quantityInStock(150)
                .expiryDate(LocalDate.of(2025, 12, 31))
                .build();

        Inventory updatedEntity = Inventory.builder()
                .inventoryId(inventoryId)
                .pharmacy(pharmacy)
                .drug(drug)
                .quantityInStock(150)
                .expiryDate(LocalDate.of(2025, 12, 31))
                .build();

        InventoryDTO updatedDTO = InventoryDTO.builder()
                .inventoryId(inventoryId)
                .pharmacyId(10L)
                .drugId(20L)
                .quantityInStock(150)
                .expiryDate(LocalDate.of(2025, 12, 31))
                .build();

        when(inventoryDomain.update(inventoryId, updateRequest)).thenReturn(updatedEntity);
        when(inventoryMapper.toDTO(updatedEntity)).thenReturn(updatedDTO);

        InventoryDTO result = inventoryService.update(inventoryId, updateRequest);

        assertNotNull(result);
        assertEquals(inventoryId, result.getInventoryId());
        assertEquals(150, result.getQuantityInStock());
        verify(inventoryDomain, times(1)).update(inventoryId, updateRequest);
        verify(inventoryMapper, times(1)).toDTO(updatedEntity);
    }

    @Test
    void update_WithNullId_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.update(null, request)
        );

        assertEquals("ID cannot be null", exception.getMessage());
        verify(inventoryDomain, never()).update(any(), any());
    }

    @Test
    void update_WithNullRequest_ShouldThrowException() {
        Long inventoryId = 1L;
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.update(inventoryId, null)
        );

        assertEquals("Request cannot be null", exception.getMessage());
        verify(inventoryDomain, never()).update(any(), any());
    }

    @Test
    void delete_ShouldCallDomainDelete() {
        Long inventoryId = 1L;
        doNothing().when(inventoryDomain).delete(inventoryId);

        inventoryService.delete(inventoryId);

        verify(inventoryDomain, times(1)).delete(inventoryId);
    }

    @Test
    void delete_WithNullId_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.delete(null)
        );

        assertEquals("ID cannot be null", exception.getMessage());
        verify(inventoryDomain, never()).delete(any());
    }
}