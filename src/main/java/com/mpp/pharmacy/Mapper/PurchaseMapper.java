package com.mpp.pharmacy.Mapper;

import com.mpp.pharmacy.DTO.PurchaseDTO;
import com.mpp.pharmacy.Entity.Purchase;
import com.mpp.pharmacy.RequestDTO.PurchaseRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {

    PurchaseMapper INSTANCE = Mappers.getMapper(PurchaseMapper.class);

    @Mapping(source = "pharmacy.pharmacyId", target = "pharmacyId")
    @Mapping(source = "patient.personId", target = "patientId")
    @Mapping(source = "prescription.prescriptionId", target = "prescriptionId")
    PurchaseDTO toDTO(Purchase entity);

    @Mapping(target = "pharmacy", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "prescription", ignore = true)
    Purchase fromRequest(PurchaseRequestDTO dto);
}