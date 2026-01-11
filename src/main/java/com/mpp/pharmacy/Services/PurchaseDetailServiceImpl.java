package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.PurchaseDetailDTO;
import com.mpp.pharmacy.Domain.PurchaseDetailDomain;
import com.mpp.pharmacy.Entity.Purchase_Detail;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import com.mpp.pharmacy.Mapper.PurchaseDetailMapper;
import com.mpp.pharmacy.Repository.PurchaseDetailRepository;
import com.mpp.pharmacy.RequestDTO.PurchaseDetailRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PurchaseDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PurchaseDetailServiceImpl implements PurchaseDetailService {

    private final PurchaseDetailMapper mapper;
    private final PurchaseDetailDomain purchaseDetailDomain;

    @Override
    public PurchaseDetailDTO create(PurchaseDetailRequestDTO request) {
        if (request == null) {
            throw new InvalidRequestException("Purchase detail request cannot be null");
        }

        Purchase_Detail created = purchaseDetailDomain.create(request);
        return mapper.toDTO(created);
    }

    @Override
    public PurchaseDetailDTO getById(Long purchaseId, Long drugId) {
        if (purchaseId == null) {
            throw new InvalidRequestException("Purchase ID cannot be null");
        }
        if (drugId == null) {
            throw new InvalidRequestException("Drug ID cannot be null");
        }

        Purchase_Detail detail = purchaseDetailDomain.getById(purchaseId, drugId);
        return mapper.toDTO(detail);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseDetailDTO> getAll() {
        return purchaseDetailDomain.getAll().stream()
            .map(mapper::toDTO)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseDetailDTO> getByPurchase(Long purchaseId) {
        if (purchaseId == null) {
            throw new InvalidRequestException("Purchase ID cannot be null");
        }

        return purchaseDetailDomain.getByPurchase(purchaseId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseDetailDTO> getByDrug(Long drugId) {
        if (drugId == null) {
            throw new InvalidRequestException("Drug ID cannot be null");
        }

        return purchaseDetailDomain.getByDrug(drugId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PurchaseDetailDTO update(Long purchaseId, Long drugId, PurchaseDetailRequestDTO request) {
        if (purchaseId == null) {
            throw new InvalidRequestException("Purchase ID cannot be null");
        }
        if (drugId == null) {
            throw new InvalidRequestException("Drug ID cannot be null");
        }
        if (request == null) {
            throw new InvalidRequestException("Purchase detail request cannot be null");
        }

        Purchase_Detail updated = purchaseDetailDomain.update(purchaseId, drugId, request);
        return mapper.toDTO(updated);
    }

    @Override
    public void delete(Long purchaseId, Long drugId) {
        if (purchaseId == null) {
            throw new InvalidRequestException("Purchase ID cannot be null");
        }
        if (drugId == null) {
            throw new InvalidRequestException("Drug ID cannot be null");
        }

        purchaseDetailDomain.delete(purchaseId, drugId);
    }
}