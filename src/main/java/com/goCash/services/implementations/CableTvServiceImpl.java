package com.goCash.services.implementations;

import com.goCash.dto.request.CableTvRequest;
import com.goCash.dto.response.CableTvResponse;
import com.goCash.exception.ApiCallException;
import com.goCash.services.CableTvService;
import com.goCash.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class CableTvServiceImpl implements CableTvService {
    @Value("${USERNAME}")
    private String getAuthUsername;

    @Value("${PASSWORD}")
    private String getAuthPassword;

    @Value("${cableTv_url}")
    private String cableTv_url;

    private final RestTemplate restTemplate = new RestTemplate();
    @Override
    public ApiResponse<List<Object>> getTvProviderBouquets(String provider) {
        String uri = "https://api-service.vtpass.com/api/service-variations?serviceID=" + provider;
        log.info("get details from VtPass api");

        Object[] objects = new Object[]{restTemplate.getForObject(
                uri,
                Object.class)};

        return ApiResponse.<List<Object>>builder()
                .code("00")
                .message("successful")
                .data(Arrays.stream(objects).toList())
                .httpStatus(HttpStatus.ACCEPTED)
                .build();
    }

    @Override
    public ApiResponse queryCableTvSubscription(String cableTvRequest) {
        String authToken = getAuthToken(getAuthUsername, getAuthPassword);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.add("User-Agent", "Spring's Template");
        httpHeaders.add("Authorization", "Basic " + authToken);

        CableTvRequest request = new CableTvRequest();
        request.setRequestId(cableTvRequest);

        HttpEntity<CableTvRequest> transactionStatusRequestHttpEntity = new HttpEntity<>
                (request, httpHeaders);
        ResponseEntity<CableTvResponse> responseEntity = restTemplate.postForEntity(
                (cableTv_url),
                transactionStatusRequestHttpEntity,
                CableTvResponse.class
        );
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode.is2xxSuccessful()) {
            log.info("ApiResponse: {}", responseEntity.getBody());
            return ApiResponse.<CableTvResponse>builder()
                    .code("00")
                    .message("Transaction Successful")
                    .data(responseEntity.getBody())
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else {
            log.error("API call failed with status code: {}", statusCode);
            throw new ApiCallException("API call failed", statusCode);
        }
    }

    private String getAuthToken(String username, String password) {
        String credentials = username + ":" + password;
        byte[] credentialsBytes = credentials.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(credentialsBytes);
    }
}
