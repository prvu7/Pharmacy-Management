package com.mpp.pharmacy.Mapper;

import com.mpp.pharmacy.DTO.PrescriptionDTO;
import com.mpp.pharmacy.Entity.Prescription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PrescriptionDetailMapper.class})
public interface PrescriptionMapper {
    PrescriptionMapper INSTANCE = Mappers.getMapper(PrescriptionMapper.class);

    @Mapping(source = "patient.personId", target = "patientId")
    @Mapping(source = "doctor.personId", target = "doctorId")
    @Mapping(source = "treatment.treatmentId", target = "treatmentId")
    @Mapping(source = "details", target = "details")
    PrescriptionDTO toDTO(Prescription entity);

    @Mapping(source = "patientId", target = "patient.personId")
    @Mapping(source = "doctorId", target = "doctor.personId")
    @Mapping(source = "treatmentId", target = "treatment.treatmentId")
    @Mapping(source = "details", target = "details")
    Prescription toEntity(PrescriptionDTO dto);
}
