package com.mpp.pharmacy.Mapper;

import com.mpp.pharmacy.DTO.PrescriptionDetailDTO;
import com.mpp.pharmacy.Entity.Prescription_Detail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PrescriptionDetailMapper {
    PrescriptionDetailMapper INSTANCE = Mappers.getMapper(PrescriptionDetailMapper.class);

    @Mapping(source = "prescription.prescriptionId", target = "prescriptionId")
    @Mapping(source = "drug.drugId", target = "drugId")
    PrescriptionDetailDTO toDTO(Prescription_Detail entity);

    @Mapping(source = "prescriptionId", target = "prescription.prescriptionId")
    @Mapping(source = "drugId", target = "drug.drugId")
    Prescription_Detail toEntity(PrescriptionDetailDTO dto);
}
