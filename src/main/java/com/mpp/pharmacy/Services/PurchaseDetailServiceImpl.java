package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.PurchaseDetailDTO;
import com.mpp.pharmacy.Entity.Drug;
import com.mpp.pharmacy.Entity.Purchase;
import com.mpp.pharmacy.Entity.PurchaseDetailId;
import com.mpp.pharmacy.Entity.Purchase_Detail;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Mapper.PurchaseDetailMapper;
import com.mpp.pharmacy.Repository.DrugRepository;
import com.mpp.pharmacy.Repository.PurchaseDetailRepository;
import com.mpp.pharmacy.Repository.PurchaseRepository;
import com.mpp.pharmacy.RequestDTO.PurchaseDetailRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PurchaseDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseDetailServiceImpl implements PurchaseDetailService {

    private final PurchaseDetailRepository repository;
    private final PurchaseRepository purchaseRepository;
    private final DrugRepository drugRepository;
    private final PurchaseDetailMapper mapper;

    @Override
    public PurchaseDetailDTO create(PurchaseDetailRequestDTO request) {
        log.info("Creating new purchase detail");

        Purchase purchase = purchaseRepository.findById(request.getPurchaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found"));

        Drug drug = drugRepository.findById(request.getDrugId())
                .orElseThrow(() -> new ResourceNotFoundException("Drug not found"));

        Purchase_Detail detail = mapper.fromRequest(request);
        detail.setPurchase(purchase);
        detail.setDrug(drug);

        return mapper.toDTO(repository.save(detail));
    }

    @Override
    public PurchaseDetailDTO getById(Long purchaseId, Long drugId) {
        PurchaseDetailId id = new PurchaseDetailId(purchaseId, drugId);
        Purchase_Detail detail = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase detail not found"));
        return mapper.toDTO(detail);
    }

    @Override
    public List<PurchaseDetailDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<PurchaseDetailDTO> getByPurchase(Long purchaseId) {
        return repository.findByPurchase_PurchaseId(purchaseId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<PurchaseDetailDTO> getByDrug(Long drugId) {
        return repository.findByDrug_DrugId(drugId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PurchaseDetailDTO update(Long purchaseId, Long drugId, PurchaseDetailRequestDTO request) {
        PurchaseDetailId id = new PurchaseDetailId(purchaseId, drugId);
        Purchase_Detail detail = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase detail not found"));

        Purchase purchase = purchaseRepository.findById(request.getPurchaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found"));

        Drug drug = drugRepository.findById(request.getDrugId())
                .orElseThrow(() -> new ResourceNotFoundException("Drug not found"));

        detail.setPurchase(purchase);
        detail.setDrug(drug);
        detail.setQuantity(request.getQuantity());
        detail.setUnitPrice(request.getUnitPrice());

        return mapper.toDTO(repository.save(detail));
    }

    @Override
    public void delete(Long purchaseId, Long drugId) {
        PurchaseDetailId id = new PurchaseDetailId(purchaseId, drugId);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Purchase detail not found");
        }
        repository.deleteById(id);
    }
}