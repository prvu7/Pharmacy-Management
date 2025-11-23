package com.mpp.pharmacy.Mapper;

import com.mpp.pharmacy.DTO.TreatmentDTO;
import com.mpp.pharmacy.Entity.Treatment;
import com.mpp.pharmacy.RequestDTO.TreatmentRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TreatmentMapper {

    @Mapping(target = "doctorId", source = "doctor.personId")
    TreatmentDTO toDTO(Treatment entity);

    @Mapping(target = "doctor", ignore = true)
    Treatment toEntity(TreatmentRequestDTO dto);
}
