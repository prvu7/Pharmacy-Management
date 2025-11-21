package com.mpp.pharmacy.Mapper;

import com.mpp.pharmacy.DTO.PersonDTO;
import com.mpp.pharmacy.Entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDTO toDTO(Person entity);
    Person toEntity(PersonDTO dto);
}
