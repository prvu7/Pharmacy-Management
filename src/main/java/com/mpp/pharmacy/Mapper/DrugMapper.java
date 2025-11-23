package com.mpp.pharmacy.Mapper;

import com.mpp.pharmacy.DTO.DrugDTO;
import com.mpp.pharmacy.Entity.Drug;
import com.mpp.pharmacy.RequestDTO.DrugRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DrugMapper {

    DrugDTO toDTO(Drug entity);

    Drug toEntity(DrugRequestDTO dto);
}