package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.PurchaseDTO;
import com.mpp.pharmacy.Domain.PurchaseDomain;
import com.mpp.pharmacy.Entity.Person;
import com.mpp.pharmacy.Entity.Pharmacy;
import com.mpp.pharmacy.Entity.Purchase;
import com.mpp.pharmacy.Enum.Role;
import com.mpp.pharmacy.Enum.Sex;
import com.mpp.pharmacy.Mapper.PurchaseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {

    @Mock
    private PurchaseMapper purchaseMapper;

    @Mock
    private PurchaseDomain purchaseDomain;

    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    @Test
    void getById_ShouldReturnASpecificPurchase() {
        // Arrange
        Long purchaseId = 1L;

        Pharmacy pharmacy = Pharmacy.builder()
                .pharmacyId(10L)
                .name("HealthPlus Pharmacy")
                .build();

        Person patient = Person.builder()
                .personId(5L)
                .firstName("John")
                .lastName("Doe")
                .sex(Sex.M)
                .role(Role.patient)
                .build();

        Purchase purchase = Purchase.builder()
                .purchaseId(1L)
                .pharmacy(pharmacy)
                .patient(patient)
                .prescription(null)
                .purchaseDate(LocalDate.of(2025, 6, 15))
                .totalAmount(new BigDecimal("150.50"))
                .build();

        PurchaseDTO expectedDTO = PurchaseDTO.builder()
                .purchaseId(1L)
                .pharmacyId(10L)
                .patientId(5L)
                .prescriptionId(null)
                .purchaseDate(LocalDate.of(2025, 6, 15))
                .totalAmount(new BigDecimal("150.50"))
                .build();

        when(purchaseDomain.getById(purchaseId)).thenReturn(purchase);
        when(purchaseMapper.toDTO(purchase)).thenReturn(expectedDTO);

        // Act
        PurchaseDTO result = purchaseService.getById(purchaseId);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getPurchaseId());
        assertEquals(10L, result.getPharmacyId());
        assertEquals(5L, result.getPatientId());
        assertNull(result.getPrescriptionId());
        assertEquals(LocalDate.of(2025, 6, 15), result.getPurchaseDate());
        assertEquals(new BigDecimal("150.50"), result.getTotalAmount());
    }

    @Test
    void delete_ShouldDeletePurchase() {
        // Arrange
        Long purchaseId = 1L;

        doNothing().when(purchaseDomain).delete(purchaseId);

        // Act
        purchaseService.delete(purchaseId);

        // Assert
        verify(purchaseDomain, times(1)).delete(purchaseId);
    }
}
