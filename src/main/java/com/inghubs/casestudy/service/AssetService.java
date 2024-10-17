package com.inghubs.casestudy.service;

import com.inghubs.casestudy.model.Asset;
import com.inghubs.casestudy.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final CustomUserDetailsService customUserDetailsService;


    public List<Asset> listAssets() {
        //Load the customer
        Long customerId = customUserDetailsService.getCurrentUserId();

        // Fetch and return all assets belonging to the given customer
        return assetRepository.findByCustomerId(customerId);
    }
}

