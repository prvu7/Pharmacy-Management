package com.mpp.pharmacy.Mapper;

import com.mpp.pharmacy.DTO.PersonDTO;
import com.mpp.pharmacy.Entity.Person;
import com.mpp.pharmacy.RequestDTO.PersonRequestDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDTO toDTO(Person entity);
    Person toEntity(PersonRequestDTO dto);
}
