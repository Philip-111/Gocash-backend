package com.goCash.controller;

import com.goCash.dto.request.DataSubscriptionRequest;
import com.goCash.dto.request.ElectricityBillRequest;
import com.goCash.services.DataService;
import com.goCash.services.ElectricityBillService;
import com.goCash.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/go-cash/paybill")
@RequiredArgsConstructor
public class BillsController {
    private final ElectricityBillService electricityBillService;
    private final DataService dataService;

    @PostMapping("/electricity/pay")
    public ResponseEntity<ApiResponse> payElectricityBill(@RequestBody ElectricityBillRequest electricityBillRequest) {
        log.info("request to pay for electricity units.");
        ApiResponse transactionStatusResponse = electricityBillService.payElectricityBill(electricityBillRequest);
        return ResponseEntity.ok(transactionStatusResponse);
    }
    @PostMapping("/purchase-data")
    public ResponseEntity<ApiResponse> purchaseData(@Valid @RequestBody DataSubscriptionRequest request) {

        ApiResponse response = dataService.purchaseDataBundle(request);
        return ResponseEntity.ok(response);
    }
}
