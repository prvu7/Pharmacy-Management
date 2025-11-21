package com.mpp.pharmacy.Mapper;

import com.mpp.pharmacy.DTO.DrugDTO;
import com.mpp.pharmacy.Entity.Drug;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DrugMapper {
    DrugMapper INSTANCE = Mappers.getMapper(DrugMapper.class);

    DrugDTO toDTO(Drug entity);
    Drug toEntity(DrugDTO dto);
}