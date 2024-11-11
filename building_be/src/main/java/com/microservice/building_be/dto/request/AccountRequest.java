package com.microservice.building_be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {

    private String account_name;
    private String email;
    private String password;
    private LocalDate update_date;
    private LocalDate delete_date;
    private String status;
}