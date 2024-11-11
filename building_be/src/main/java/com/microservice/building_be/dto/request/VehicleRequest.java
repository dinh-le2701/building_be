package com.microservice.building_be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequest {
    private String vehicle_name;
    private String license_plate;
    private String vehicle_type;
    private String color;
}
