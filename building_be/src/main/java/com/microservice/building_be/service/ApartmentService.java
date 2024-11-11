package com.microservice.building_be.service;

import com.microservice.building_be.dto.response.ApartmentResponse;
import com.microservice.building_be.model.Apartment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApartmentService {

    ApartmentResponse getAllApartments();

    Page<Apartment> getAllStaffs(int page, int size);

    Apartment getApartmentById(Integer apartment_id);

    Apartment saveApartment(Apartment apartment);

    Apartment updateApartmentById(Integer apartment_id, Apartment updateApartment);

    void deleteApartmentById(Integer apartment_id);



}
