package com.goCash.services;

import com.goCash.entities.WalletAccount;
import org.springframework.stereotype.Service;


import com.goCash.utils.ApiResponse;

@Service
public interface WalletServices {

    ApiResponse fundWallet();
    ApiResponse<Double> balanceEnquiry();
}
