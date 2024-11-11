package com.microservice.building_be.service.serviceimpl;

import com.microservice.building_be.dto.request.LoginRequest;
import com.microservice.building_be.dto.response.LoginResponse;
import com.microservice.building_be.exception.ResourceNotFoundException;
import com.microservice.building_be.model.Account;
import com.microservice.building_be.repository.AccountRepository;
import com.microservice.building_be.service.AuthService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    private final static String SIGNER_KEY = "62dff4543f5521fae8c895a1838f006a6be2cfb15092f2524f6d60095be04845dfea2875789c329fc149567315afff6a19441778b6291b59eb45b701356408a0";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public LoginResponse handleLogin(LoginRequest request) {
        if(request.getEmail() == null || request.getPassword() == null){
            throw new ResourceNotFoundException("False");
        }
        var account = accountRepository.findByEmail(request.getEmail()).orElseThrow(()-> new ResourceNotFoundException("Account not found!"));


        if(!passwordEncoder.matches(request.getPassword(), account.getPassword())){
            throw new ResourceNotFoundException("Wrong password");
        }

        var token = generateToken(account);

        LoginResponse response = new LoginResponse();
        response.setAccount_id(account.getId());
        response.setEmail(account.getEmail());
        response.setToken(token);
        response.setRole(account.getAuthorities());

        return response;
    }


    public String generateToken(Account account){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        try{
            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(account.getAccount_name())
                    .issuer("Định Lê")
                    .issueTime(new Date())
                    .expirationTime(new Date(
                            Instant.now().plus(24, ChronoUnit.HOURS).toEpochMilli()
                    ))
                    .jwtID(UUID.randomUUID().toString())
                    .claim("role", account.getRole())
                    .build();

            Payload payload = new Payload(jwtClaimsSet.toJSONObject());

            JWSObject jwsObject = new JWSObject(header, payload);
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e){
            System.out.println("Can not create token");
            throw new RuntimeException(e);
        }
    }
    private String buildScope(Account account) {
        StringJoiner stringJoiner = new StringJoiner("");

        if (account.getRole() != null && !account.getRole().isEmpty()) {
            stringJoiner.add("ROLE_" + account.getRole());
        }

        return stringJoiner.toString();
    }
}

