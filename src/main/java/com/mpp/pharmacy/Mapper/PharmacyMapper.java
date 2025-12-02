package com.mpp.pharmacy.Mapper;

import com.mpp.pharmacy.DTO.PharmacyDTO;
import com.mpp.pharmacy.Entity.Pharmacy;
import com.mpp.pharmacy.RequestDTO.PharmacyRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PharmacyMapper {

    PharmacyMapper INSTANCE = Mappers.getMapper(PharmacyMapper.class);

    PharmacyDTO toDTO(Pharmacy entity);

    Pharmacy fromRequest(PharmacyRequestDTO dto);
}