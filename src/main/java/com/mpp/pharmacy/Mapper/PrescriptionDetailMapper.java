package com.mpp.pharmacy.Mapper;

import com.mpp.pharmacy.DTO.PrescriptionDetailDTO;
import com.mpp.pharmacy.Entity.Prescription_Detail;
import com.mpp.pharmacy.RequestDTO.PrescriptionDetailRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PrescriptionDetailMapper {

    PrescriptionDetailMapper INSTANCE = Mappers.getMapper(PrescriptionDetailMapper.class);

    @Mapping(source = "prescription.prescriptionId", target = "prescriptionId")
    @Mapping(source = "drug.drugId", target = "drugId")
    PrescriptionDetailDTO toDTO(Prescription_Detail entity);

    @Mapping(target = "prescription", ignore = true)
    @Mapping(target = "drug", ignore = true)
    Prescription_Detail fromRequest(PrescriptionDetailRequestDTO dto);
}
