package com.mpp.pharmacy.Mapper;

import com.mpp.pharmacy.DTO.PurchaseDetailDTO;
import com.mpp.pharmacy.Entity.Purchase_Detail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PurchaseDetailMapper {
    PurchaseDetailMapper INSTANCE = Mappers.getMapper(PurchaseDetailMapper.class);

    @Mapping(source = "purchase.purchaseId", target = "purchaseId")
    @Mapping(source = "drug.drugId", target = "drugId")
    PurchaseDetailDTO toDTO(Purchase_Detail entity);

    @Mapping(source = "purchaseId", target = "purchase.purchaseId")
    @Mapping(source = "drugId", target = "drug.drugId")
    Purchase_Detail toEntity(PurchaseDetailDTO dto);
}

