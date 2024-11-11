package com.microservice.building_be.service.serviceimpl;

import com.microservice.building_be.dto.request.ResidentRequest;
import com.microservice.building_be.dto.request.VehicleRequest;
import com.microservice.building_be.exception.ResourceNotFoundException;
import com.microservice.building_be.model.Resident;
import com.microservice.building_be.model.Vehicle;
import com.microservice.building_be.repository.ResidentRepository;
import com.microservice.building_be.repository.VehicleRepository;
import com.microservice.building_be.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ResidentServiceImpl implements ResidentService {

    @Autowired
    private ResidentRepository residentRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Page<Resident> getAllResidents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return residentRepository.findAll(pageable);
    }

    @Override
    public Resident getResidentById(Long resident_id) {
        return residentRepository.findById(resident_id).orElseThrow(
                () -> new ResourceNotFoundException("Not found resident has id: " + resident_id)
        );
    }

    @Override
    public Resident createResident(Resident resident, List<Vehicle> vehicles) {
        if (vehicles != null) {
            for (Vehicle vehicle : vehicles) {
                vehicle.setResident(resident);  // Link vehicle to resident
            }
        }
        // Resident newResident = residentRepository.save(resident);
        return residentRepository.save(resident);
    }

    @Override
    public Resident updateResidentById(Long resident_id, Resident updateResident) {
        Resident resident = residentRepository.findById(resident_id).orElseThrow(
                () -> new ResourceNotFoundException("Not found resident has id: " + resident_id)
        );

        resident.setResident_name(updateResident.getResident_name());
        resident.setEmail(updateResident.getEmail());
        resident.setPhone_number(updateResident.getPhone_number());
        resident.setBirthday(updateResident.getBirthday());

        return residentRepository.save(resident);
    }

    @Override
    public void deleteResidentById(Long resident_id) {
        Optional<Resident> resident = residentRepository.findById(resident_id);
        if (resident.isPresent()) {
            residentRepository.deleteById(resident_id);
        } else {
            throw new ResourceNotFoundException("Resident với id " + resident_id + " không tồn tại!");
        }
    }

    @Transactional
    public Vehicle addVehicleToResident(Long residentId, VehicleRequest vehicleRequest) {
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new ResourceNotFoundException("Resident not found"));

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicle_name(vehicleRequest.getVehicle_name());
        vehicle.setLicense_plate(vehicleRequest.getLicense_plate());
        vehicle.setVehicle_type(vehicleRequest.getVehicle_type());
        vehicle.setColor(vehicleRequest.getColor());
        vehicle.setResident(resident); // Link vehicle to resident

        return vehicleRepository.save(vehicle);
    }
}
