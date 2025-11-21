package com.mpp.pharmacy.Mapper;

import com.mpp.pharmacy.DTO.InventoryDTO;
import com.mpp.pharmacy.Entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InventoryMapper {
    InventoryMapper INSTANCE = Mappers.getMapper(InventoryMapper.class);

    @Mapping(source = "pharmacy.pharmacyId", target = "pharmacyId")
    @Mapping(source = "drug.drugId", target = "drugId")
    InventoryDTO toDTO(Inventory entity);

    @Mapping(source = "pharmacyId", target = "pharmacy.pharmacyId")
    @Mapping(source = "drugId", target = "drug.drugId")
    Inventory toEntity(InventoryDTO dto);
}
