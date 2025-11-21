package com.mpp.pharmacy.Mapper;

import com.mpp.pharmacy.DTO.TreatmentDTO;
import com.mpp.pharmacy.Entity.Treatment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TreatmentMapper {
    TreatmentMapper INSTANCE = Mappers.getMapper(TreatmentMapper.class);

    @Mapping(source = "doctor.personId", target = "doctorId")
    TreatmentDTO toDTO(Treatment entity);

    @Mapping(source = "doctorId", target = "doctor.personId")
    Treatment toEntity(TreatmentDTO dto);
}