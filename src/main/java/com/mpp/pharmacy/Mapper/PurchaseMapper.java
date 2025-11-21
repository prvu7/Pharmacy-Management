package com.mpp.pharmacy.Mapper;

import com.mpp.pharmacy.DTO.PurchaseDTO;
import com.mpp.pharmacy.Entity.Purchase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PurchaseDetailMapper.class})
public interface PurchaseMapper {
    PurchaseMapper INSTANCE = Mappers.getMapper(PurchaseMapper.class);

    @Mapping(source = "pharmacy.pharmacyId", target = "pharmacyId")
    @Mapping(source = "patient.personId", target = "patientId")
    @Mapping(source = "prescription.prescriptionId", target = "prescriptionId")
    @Mapping(source = "details", target = "details")
    PurchaseDTO toDTO(Purchase entity);

    @Mapping(source = "pharmacyId", target = "pharmacy.pharmacyId")
    @Mapping(source = "patientId", target = "patient.personId")
    @Mapping(source = "prescriptionId", target = "prescription.prescriptionId")
    @Mapping(source = "details", target = "details")
    Purchase toEntity(PurchaseDTO dto);
}
