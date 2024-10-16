package com.inghubs.casestudy.repository;

import com.inghubs.casestudy.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByCustomerId(Long customerId);

    //List<Asset> findByAssetName(String assetName); //TODO:: Bunu dene!
}

