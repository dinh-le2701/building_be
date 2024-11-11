package com.microservice.building_be.controller;

import com.microservice.building_be.dto.request.ResidentRequest;
import com.microservice.building_be.dto.request.VehicleRequest;
import com.microservice.building_be.model.Resident;
import com.microservice.building_be.model.Vehicle;
import com.microservice.building_be.service.ResidentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resident")
@Slf4j
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    @GetMapping("")
    public ResponseEntity<Page<Resident>> getAllStaff(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sort
    ){
        try {
            Page<Resident> residents = residentService.getAllResidents(page, size);
            log.info("Get residents successfully");
            return new ResponseEntity<>(residents, HttpStatus.OK);
        } catch (SqlScriptException e){
            log.warn(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{resident_id}")
    public ResponseEntity<Resident> getResidentById(@PathVariable("resident_id") Long resident_id){
        try {
            Resident resident = residentService.getResidentById(resident_id);
            log.info("Found resident has id: " + resident_id);
            return new ResponseEntity<>(resident, HttpStatus.OK);
        } catch (SqlScriptException e) {
            log.warn("Not found residents or the path is incorrect!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<Resident> createResident(@RequestBody ResidentRequest residentRequest){
        try {
            Resident resident = new Resident();
            resident.setResident_name(residentRequest.getResident_name());
            resident.setPhone_number(residentRequest.getPhone_number());
            resident.setEmail(residentRequest.getEmail());
            resident.setCccd(residentRequest.getCccd());
            resident.setBirthday(residentRequest.getBirthday());

            List<Vehicle> vehicles = residentRequest.getVehicles();
            resident.setVehicles(residentRequest.getVehicles());

            Resident saveResident = residentService.createResident(resident, vehicles);
            log.info("Create new resident successfully!");
            return new ResponseEntity<>(saveResident, HttpStatus.CREATED);
        } catch (SqlScriptException e){
            log.warn("Create new resident has failed!" + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{resident_id}")
    public ResponseEntity<Resident>  updateResident(@Valid @PathVariable("resident_id") Long resident_id, Resident updateResident){
        try {
            Resident resident = residentService.updateResidentById(resident_id, updateResident);
            log.info("Update new resident completed!");
            return new ResponseEntity<>(resident, HttpStatus.OK);
        } catch (SqlScriptException e){
            log.info("Update new resident has failed!" + e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{resident_id}")
    public ResponseEntity<String> deleteResident(@PathVariable("resident_id") Long resident_id) {
        try {
            residentService.deleteResidentById(resident_id);
            log.info("Delete resident with id: " + resident_id + " successfully!");
            return new ResponseEntity<>("Deleted!", HttpStatus.OK);
        } catch (SqlScriptException e) {
            log.info("Delete resident has failed! Not found resident");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{resident_id}/vehicles")
    public ResponseEntity<Vehicle> addVehicleToResident(
            @PathVariable("resident_id") Long residentId,
            @Valid @RequestBody VehicleRequest vehicleRequest) {
        try {
            Vehicle vehicle = residentService.addVehicleToResident(residentId, vehicleRequest);
            log.info("Added vehicle to resident with id: " + residentId);
            return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
        } catch (SqlScriptException e) {
            log.warn("Failed to add vehicle for resident id: " + residentId + ", error: " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}