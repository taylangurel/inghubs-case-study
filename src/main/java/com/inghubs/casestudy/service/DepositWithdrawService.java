package com.inghubs.casestudy.service;

import com.inghubs.casestudy.exception.NotFoundException;
import com.inghubs.casestudy.model.Asset;
import com.inghubs.casestudy.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepositWithdrawService {

    private final AssetRepository assetRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public String depositMoney(double amount) {
        //Load the customer
        Long customerId = customUserDetailsService.getCurrentUserId();

        // Fetch or create the TRY asset
        Asset tryAsset = assetRepository.findByCustomerId(customerId)
                .stream()
                .filter(a -> a.getAssetName().equals("TRY"))
                .findFirst()
                .orElseGet(() -> {
                    Asset newTryAsset = new Asset(null, customerId, "TRY", 0.0, 0.0);
                    assetRepository.save(newTryAsset);
                    return newTryAsset;
                });

        // Update the TRY asset with the deposited amount
        tryAsset.setSize(tryAsset.getSize() + amount);
        tryAsset.setUsableSize(tryAsset.getUsableSize() + amount);
        assetRepository.save(tryAsset);

        return "Deposit successful";
    }

    public String withdrawMoney(double amount) {
        //Load the customer
        Long customerId = customUserDetailsService.getCurrentUserId();

        // Fetch the TRY asset or throw an exception if not found
        Asset tryAsset = assetRepository.findByCustomerId(customerId)
                .stream()
                .filter(a -> a.getAssetName().equals("TRY"))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("TRY asset not found for customer"));

        // Ensure there are enough funds to withdraw
        if (tryAsset.getUsableSize() < amount) {
            throw new IllegalArgumentException("Insufficient funds for withdrawal");
        }

        // Update the TRY asset with the withdrawn amount
        tryAsset.setSize(tryAsset.getSize() - amount);
        tryAsset.setUsableSize(tryAsset.getUsableSize() - amount);
        assetRepository.save(tryAsset);

        return "Withdraw successful";
    }
}


