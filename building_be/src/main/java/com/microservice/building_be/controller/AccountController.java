package com.microservice.building_be.controller;


import com.microservice.building_be.dto.request.AccountRequest;
import com.microservice.building_be.dto.response.AccountResponse;
import com.microservice.building_be.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;


    // api get all
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAccounts(){
        List<AccountResponse> list = accountService.getAccounts();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    // api get by id
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getById(@PathVariable("id") Long id){
        AccountResponse response = accountService.getAccountById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest accountRequest){
        AccountResponse newAccountResponse =accountService.createNewAccount(accountRequest);
        return new ResponseEntity<>(newAccountResponse, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable("id") Long id, @RequestBody AccountRequest request){
        AccountResponse response = accountService.updateAccount(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable("id") Long id){
        accountService.deleteAccount(id);
        HttpStatus.OK.value();
    }

    @PutMapping("/lock/{id}")
    public ResponseEntity<AccountResponse> lockAccount(@PathVariable("id") Long id){
        AccountResponse response = accountService.lockAccount(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
