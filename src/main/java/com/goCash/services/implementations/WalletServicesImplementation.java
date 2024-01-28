package com.goCash.services.implementations;

import com.goCash.dto.response.WalletResponseDto;
import com.goCash.entities.User;
import com.goCash.entities.WalletAccount;
import com.goCash.exception.UserNotFoundException;
import com.goCash.repository.UserRepository;
import com.goCash.repository.WalletRepository;
import com.goCash.services.WalletServices;
import com.goCash.utils.ApiResponse;
import com.goCash.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor

public class WalletServicesImplementation implements WalletServices {
    private final UserRepository userRepository;
    private final Util utils;


    @Override
    public ApiResponse fundWallet() {



        // Saving user by Security context
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String loggedInUserUserName = userDetails.getUsername();



        User user = userRepository.findByEmail(loggedInUserUserName)
                .orElse(null);
        if (user == null) {
            log.info("This user does not exist in our database");
            return ApiResponse.builder()
                    .code("01")
                    .message("User not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }


        WalletResponseDto walletResponseDto = WalletResponseDto.builder()
                .accountNumber(user.getWalletAccount().getAccountNumber())
                .accountName(user.getFirstName() + " " + user.getLastName())
                .bankName("Go Cash")
                .build();
        log.info("User details has been fetched");
        return ApiResponse.builder()
                .code("00")
                .message("User details fetched")
                .data(walletResponseDto)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<Double> balanceEnquiry() {
        String loggedInUserName = utils.getLoginUser();
        log.info("check if user exists");
        Optional<User> user = userRepository.findByEmail(loggedInUserName);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found", HttpStatus.NOT_FOUND);
        }
        else return new ApiResponse<>("00", "Enquiry Success", user.get().getWalletAccount().getBalance(), HttpStatus.OK);
    }


}








