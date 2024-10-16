package com.inghubs.casestudy.service;

import com.inghubs.casestudy.model.Asset;
import com.inghubs.casestudy.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepositWithdrawService {

    private final AssetRepository assetRepository;

    public String depositMoney(Long customerId, Double amount) {
        Asset tryAsset = getTryAsset(customerId);
        tryAsset.setSize(tryAsset.getSize() + amount);
        tryAsset.setUsableSize(tryAsset.getUsableSize() + amount);
        assetRepository.save(tryAsset);
        return "Deposit successful";
    }

    public String withdrawMoney(Long customerId, Double amount, String iban) {
        Asset tryAsset = getTryAsset(customerId);
        if (tryAsset.getUsableSize() < amount) {
            throw new RuntimeException("Not enough TRY to withdraw");
        }
        tryAsset.setUsableSize(tryAsset.getUsableSize() - amount);
        assetRepository.save(tryAsset);
        return "Withdraw successful";
    }

    private Asset getTryAsset(Long customerId) {
        return assetRepository.findByCustomerId(customerId)
                .stream()
                .filter(a -> a.getAssetName().equals("TRY"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("TRY asset not found"));
    }
}

