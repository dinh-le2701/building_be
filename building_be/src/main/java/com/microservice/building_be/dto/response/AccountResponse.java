package com.microservice.building_be.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AccountResponse {
    private Long id;
    private String role;
    private String email;
    private String account_name;
    private String create_date;
    private String status;
}
