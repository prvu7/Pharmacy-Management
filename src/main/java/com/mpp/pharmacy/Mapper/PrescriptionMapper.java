package com.mpp.pharmacy.Mapper;

import com.mpp.pharmacy.DTO.PrescriptionDTO;
import com.mpp.pharmacy.Entity.Prescription;
import com.mpp.pharmacy.RequestDTO.PrescriptionRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PrescriptionMapper {

    @Mapping(source = "patient.personId", target = "patientId")
    @Mapping(source = "doctor.personId", target = "doctorId")
    @Mapping(source = "treatment.treatmentId", target = "treatmentId")
    PrescriptionDTO toDTO(Prescription entity);

    @Mapping(source = "patientId", target = "patient.personId")
    @Mapping(source = "doctorId", target = "doctor.personId")
    @Mapping(source = "treatmentId", target = "treatment.treatmentId")
    Prescription toEntity(PrescriptionRequestDTO dto);
}