package com.inghubs.casestudy;

import com.inghubs.casestudy.model.Asset;
import com.inghubs.casestudy.repository.AssetRepository;
import com.inghubs.casestudy.service.AssetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AssetServiceTest {

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private AssetService assetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listAssets_shouldReturnAllAssetsForCustomer_whenValidCustomerId() {
        Asset tryAsset = new Asset(1L, 1L, "TRY", 10000.0, 10000.0);
        Asset stockAsset = new Asset(1L, 1L, "AAPL", 50.0, 50.0);

        when(assetRepository.findByCustomerId(1L)).thenReturn(Arrays.asList(tryAsset, stockAsset));

        List<Asset> assets = assetService.listAssets(1L);

        assertEquals(2, assets.size());
        assertEquals("TRY", assets.get(0).getAssetName());
        verify(assetRepository, times(1)).findByCustomerId(1L);
    }
}

