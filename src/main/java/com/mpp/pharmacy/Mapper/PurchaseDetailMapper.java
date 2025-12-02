package com.mpp.pharmacy.Mapper;

import com.mpp.pharmacy.DTO.PurchaseDetailDTO;
import com.mpp.pharmacy.Entity.Purchase_Detail;
import com.mpp.pharmacy.RequestDTO.PurchaseDetailRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface PurchaseDetailMapper {

    PurchaseDetailMapper INSTANCE = Mappers.getMapper(PurchaseDetailMapper.class);

    @Mapping(source = "purchase.purchaseId", target = "purchaseId")
    @Mapping(source = "drug.drugId", target = "drugId")
    PurchaseDetailDTO toDTO(Purchase_Detail entity);

    @Mapping(target = "purchase", ignore = true)
    @Mapping(target = "drug", ignore = true)
    Purchase_Detail fromRequest(PurchaseDetailRequestDTO dto);
}

