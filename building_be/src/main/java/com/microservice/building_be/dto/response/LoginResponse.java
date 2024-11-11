package com.microservice.building_be.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class LoginResponse {

    private String message;
    private Long account_id;
    private String email;
    private String token;
    private Collection role; ;
}
