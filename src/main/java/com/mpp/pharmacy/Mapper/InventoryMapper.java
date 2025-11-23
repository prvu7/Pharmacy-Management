package com.mpp.pharmacy.Mapper;

import com.mpp.pharmacy.DTO.InventoryDTO;
import com.mpp.pharmacy.Entity.Inventory;
import com.mpp.pharmacy.RequestDTO.InventoryRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    InventoryMapper INSTANCE = Mappers.getMapper(InventoryMapper.class);

    @Mapping(source = "pharmacy.pharmacyId", target = "pharmacyId")
    @Mapping(source = "drug.drugId", target = "drugId")
    InventoryDTO toDTO(Inventory entity);

    @Mapping(target = "pharmacy", ignore = true)
    @Mapping(target = "drug", ignore = true)
    Inventory fromRequest(InventoryRequestDTO dto);
}