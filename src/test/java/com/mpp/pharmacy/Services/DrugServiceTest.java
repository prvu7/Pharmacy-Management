package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.DrugDTO;
import com.mpp.pharmacy.RequestDTO.DrugRequestDTO;
import com.mpp.pharmacy.Repository.DrugRepository;
import com.mpp.pharmacy.Mapper.DrugMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.mpp.pharmacy.Services.DrugServiceImpl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DrugServiceTest {

    @Mock
    private DrugRepository drugRepository;

    @Mock
    private DrugMapper drugMapper;

    @InjectMocks
    private DrugServiceImpl drugService;

    private DrugRequestDTO request;
    private DrugDTO expectedResponse;

    @BeforeEach
    void setUp() {
        request = new DrugRequestDTO();
        expectedResponse = new DrugDTO();
    }

    @Test
    void createDrugTest() {
        when(drugRepository.save(any())).thenReturn(null);
        when(drugMapper.toDTO(any())).thenReturn(expectedResponse);

        DrugDTO result = drugService.create(request);

        assertNotNull(result);
        verify(drugRepository, times(1)).save(any());
    }
}
