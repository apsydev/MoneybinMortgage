package com.moneybin.mortgage.controller;

import com.moneybin.mortgage.dto.MortgageDto;
import com.moneybin.mortgage.service.MortgageCalculationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/moneybin/mortgage")
@CrossOrigin
public class MortgageBinController {

    @Autowired
    public MortgageCalculationFacade mortgageCalculationFacade;

    @PostMapping("/instalment/customer")
    public ResponseEntity<?> calculateMonthlyInstalment(@RequestBody MortgageDto customer){
        customer = mortgageCalculationFacade.calculateMonthlyInstalment(customer);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/instalment/customers")
    public ResponseEntity<?> calculateMonthlyInstalmentBulk(@RequestBody List<MortgageDto> customers){
        customers = mortgageCalculationFacade.calculateMonthlyInstalment(customers);
        return ResponseEntity.ok(customers);
    }

    @PostMapping("/instalment/customers/bulk")
    public ResponseEntity<?> calculateMonthlyInstalmentMultipart(@RequestPart("customers") MultipartFile file){
        List<MortgageDto> customers = null;
        if (null == file.getOriginalFilename()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            byte[] bytes = file.getBytes();
            customers = mortgageCalculationFacade.calculateMonthlyInstalment(bytes);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ok(customers);
    }
}
