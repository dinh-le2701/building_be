package com.microservice.building_be.dto.request;

import com.microservice.building_be.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentRequest {
    private String resident_name;
    private String phone_number;
    private String email;
    private String cccd;
    private LocalDate birthday;

    private List<Vehicle> vehicles;
}
