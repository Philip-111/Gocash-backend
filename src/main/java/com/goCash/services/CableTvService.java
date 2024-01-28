package com.goCash.services;

import com.goCash.utils.ApiResponse;


public interface CableTvService {
    ApiResponse getTvProviderBouquets(String provider);
    ApiResponse queryCableTvSubscription(String cableTvRequest);
}
