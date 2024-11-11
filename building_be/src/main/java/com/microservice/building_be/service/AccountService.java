package com.microservice.building_be.service;


import com.microservice.building_be.dto.request.AccountRequest;
import com.microservice.building_be.dto.response.AccountResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {


    List<AccountResponse> getAccounts();

    AccountResponse getAccountById(Long id);

    AccountResponse createNewAccount(AccountRequest account);

    AccountResponse updateAccount(Long id, AccountRequest request);

    void deleteAccount(Long id);

    AccountResponse lockAccount(Long id);


}
