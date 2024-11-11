package com.microservice.building_be.dto.response;

import com.microservice.building_be.model.Apartment;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentResponse {
    private List<Apartment> apartments;
    private int total;
}

