package com.microservice.building_be.controller;


import com.microservice.building_be.model.Apartment;
import com.microservice.building_be.service.ApartmentService;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/v1/apartment")
@Slf4j
public class ApartmentController {

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping("")
    public ResponseEntity<Page<Apartment>> getAllStaff(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sort
    ){
        try {
            Page<Apartment> staffs = apartmentService.getAllStaffs(page, size);
            return new ResponseEntity<>(staffs, HttpStatus.OK);
        } catch (SqlScriptException e){
            log.warn(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{apartment_id}")
    public ResponseEntity<Apartment> getApartmentById(@PathVariable("apartment_id") Integer apartment_id){
        try {
            Apartment apartment = apartmentService.getApartmentById(apartment_id);
            log.info("Found apartment has id " + apartment_id);
            return new ResponseEntity<>(apartment, HttpStatus.OK);
        } catch (Exception e){
            log.warn("Không tìm thấy dữ liệu!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<Apartment> createApartment(@RequestBody Apartment apartment){
        try {
            Apartment newApartment = apartmentService.saveApartment(apartment);
            log.info("Create new apartment successfully!");
            return new ResponseEntity<>(newApartment, HttpStatus.CREATED);
        } catch (Exception e){
            log.error("Create new apartment has error!" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{apartment_id}")
    public ResponseEntity<Apartment> updateApartent(@PathVariable("apartment_id") Integer apartment_id, Apartment updateApartment){
        try {
            Apartment apartment = apartmentService.updateApartmentById(apartment_id, updateApartment);
            log.info("Update has successfully!");
            return new ResponseEntity<>(apartment, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Update has error!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{apartment_id}")
    public ResponseEntity<String> deleteTask(@PathVariable("apartment_id") Integer apartment_id){
        try {
            apartmentService.deleteApartmentById(apartment_id);
            log.info("Delete has successfully!");
            return new ResponseEntity<>("Deleted!", HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            log.error("Delete has error");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}