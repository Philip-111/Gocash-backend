package com.goCash.controller;

import com.goCash.services.CableTvService;
import com.goCash.utils.ApiResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Data
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/tv")
public class CableTvController {

    private final CableTvService tvService;

    @GetMapping(path = "/{provider}")
    public ResponseEntity<?> viewTvVariations(@PathVariable String provider) {
        log.info("request to view different Tv packages like dstv, showmax, gotv");
        ApiResponse response = tvService.getTvProviderBouquets(provider);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/cableTv")
    public ResponseEntity<ApiResponse> queryCableTvSubscription (@RequestParam("requestId") String requestId) {
        ApiResponse transactionStatusResponse = tvService.queryCableTvSubscription(requestId);
        return ResponseEntity.ok(transactionStatusResponse);
    }
}
