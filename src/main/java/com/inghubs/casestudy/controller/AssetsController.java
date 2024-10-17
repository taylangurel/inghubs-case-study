package com.inghubs.casestudy.controller;

import com.inghubs.casestudy.model.Asset;
import com.inghubs.casestudy.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assets")
public class AssetsController {

    private final AssetService assetService;

    @GetMapping("/list")
    public List<Asset> listAssets() {
        return assetService.listAssets();
    }
}

