package com.inghubs.casestudy;

import com.inghubs.casestudy.model.Asset;
import com.inghubs.casestudy.repository.AssetRepository;
import com.inghubs.casestudy.service.DepositWithdrawService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DepositWithdrawServiceTest {

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private DepositWithdrawService depositWithdrawService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void depositMoney_shouldDepositAmount_whenValidCustomerId() {
        Asset tryAsset = new Asset(1L, 1L, "TRY", 10000.0, 10000.0);

        when(assetRepository.findByCustomerId(1L)).thenReturn(Collections.singletonList(tryAsset));

        String result = depositWithdrawService.depositMoney(1L, 500.0);

        assertEquals("Deposit successful", result);
        assertEquals(10500.0, tryAsset.getSize());
        verify(assetRepository, times(1)).save(tryAsset);
    }

    @Test
    void withdrawMoney_shouldWithdrawAmount_whenCustomerHasEnoughFunds() {
        Asset tryAsset = new Asset(1L, 1L, "TRY", 10000.0, 10000.0);

        when(assetRepository.findByCustomerId(1L)).thenReturn(Collections.singletonList(tryAsset));

        String result = depositWithdrawService.withdrawMoney(1L, 500.0, "TR123456789");

        assertEquals("Withdraw successful", result);
        assertEquals(9500.0, tryAsset.getUsableSize());
        verify(assetRepository, times(1)).save(tryAsset);
    }
}

