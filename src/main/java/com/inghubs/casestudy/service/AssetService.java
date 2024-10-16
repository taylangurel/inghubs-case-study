package com.inghubs.casestudy.service;

import com.inghubs.casestudy.model.Asset;
import com.inghubs.casestudy.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;

    public List<Asset> listAssets(Long customerId) {
        // Fetch and return all assets belonging to the given customer
        return assetRepository.findByCustomerId(customerId);
    }
}

