package com.microservice.building_be.service.serviceimpl;


import com.microservice.building_be.dto.request.AccountRequest;
import com.microservice.building_be.dto.response.AccountResponse;
import com.microservice.building_be.enums.Role;
import com.microservice.building_be.enums.Status;
import com.microservice.building_be.exception.ResourceNotFoundException;
import com.microservice.building_be.model.Account;
import com.microservice.building_be.repository.AccountRepository;
import com.microservice.building_be.service.AccountService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AccountRepository accountRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AccountResponse> getAccounts() {
        List<Account> account = accountRepository.findAll();


        List<AccountResponse> responses = new ArrayList<>();
        for (int  i = 0; i < account.size(); i ++){
            AccountResponse accountResponse = modelMapper.map(account.get(i), AccountResponse.class);
            responses.add(accountResponse);
        }

        return responses;
    }

    @Override
    public AccountResponse getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found account has id: " + id)
        );
        return modelMapper.map(account, AccountResponse.class);
    }

    @Override
    @Transactional
    public AccountResponse createNewAccount(AccountRequest accountRequest) {
        Account account = new Account();
        account.setAccount_name(accountRequest.getAccount_name());
        account.setEmail(accountRequest.getEmail());
        account.setCreate_date(Timestamp.valueOf(LocalDateTime.now()));
        account.setUpdate_date(null);
        account.setRole(Role.USER.name());
        account.setStatus(Status.ENABLED.name());
        account.setPassword(bCryptPasswordEncoder.encode(accountRequest.getPassword()));

        accountRepository.save(account);

        AccountResponse response = modelMapper.map(account, AccountResponse.class);

        return response;
    }
    private LocalDateTime getCurrentFormattedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm dd/MM/yyyy");
        return LocalDateTime.now();
    }
    @Override
    public AccountResponse updateAccount(Long id, AccountRequest request) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found account has id: " + id)
        );

        account.setAccount_name(request.getAccount_name());
        account.setEmail(request.getEmail());
        account.setUpdate_date(Timestamp.valueOf(LocalDateTime.now()));
        accountRepository.save(account);

        AccountResponse response = modelMapper.map(account, AccountResponse.class);

        return response;
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public AccountResponse lockAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found account has id: " + id)
        );
        account.setStatus(Status.DISABLED.name());
        account.setDelete_date(Timestamp.valueOf(LocalDateTime.now()));
        accountRepository.save(account);

        return modelMapper.map(account, AccountResponse.class);
    }



}