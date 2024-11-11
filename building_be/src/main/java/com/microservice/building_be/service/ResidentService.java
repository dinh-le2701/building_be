package com.microservice.building_be.service;


import com.microservice.building_be.dto.request.VehicleRequest;
import com.microservice.building_be.model.Resident;
import com.microservice.building_be.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResidentService {

    //    List<Resident> getResidents();
    Page<Resident> getAllResidents(int page, int size);

    Resident getResidentById(Long resident_id);

    Resident createResident(Resident resident, List<Vehicle> vehicles);

    Resident updateResidentById(Long resident_id, Resident updateResident);

    void deleteResidentById(Long resident_id);

    Vehicle  addVehicleToResident(Long residentId, VehicleRequest vehicleRequest);
}