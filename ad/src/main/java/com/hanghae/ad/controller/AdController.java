package com.hanghae.ad.controller;

import com.hanghae.ad.dto.AdRegistrationRequestDto;
import com.hanghae.ad.dto.AdResponseDto;
import com.hanghae.ad.service.AdService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;

    public AdController(AdService adService) {
        this.adService = adService;
    }

    @PostMapping("/view/{adId}")
    public ResponseEntity<?> viewAd(@PathVariable Long adId, @RequestParam Long userId) {
        adService.viewAd(userId, adId);
        return ResponseEntity.ok("Ad view logged");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAd(@RequestBody AdRegistrationRequestDto request) {
        adService.registerAd(request);
        return ResponseEntity.ok("Ad registered successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdResponseDto> getAdById(@PathVariable Long id) {
        AdResponseDto ad = adService.getAdById(id);
        return ResponseEntity.ok(ad);
    }

    @GetMapping
    public ResponseEntity<List<AdResponseDto>> getAllAds() {
        List<AdResponseDto> ads = adService.getAllAds();
        return ResponseEntity.ok(ads);
    }
}

