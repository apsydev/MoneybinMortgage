package com.moneybin.mortgage.service;

import com.moneybin.mortgage.dto.CustomerDto;
import com.moneybin.mortgage.dto.MortgageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MortgageCalculationFacade {

    private MortgageService mortgageService;
    private CsvUtils csvUtils;

    public MortgageDto calculateMonthlyInstalment(MortgageDto loan){

        loan.setMonthlyInstalment(mortgageService.calculateMonthlyInstalment(
                loan.getLoanAmount(),
                loan.getInterestRate(),
                loan.getLoanDurationYears() * 12));
        return loan;
    }

    public List<MortgageDto> calculateMonthlyInstalment(List<MortgageDto> customers){
        List<MortgageDto> mortgageDtos = customers
                .stream()
                .peek(this::calculateInstalment)
                .collect(Collectors.toList());
        return mortgageDtos;
    }

    public List<MortgageDto> calculateMonthlyInstalment(byte[] customerBytes) throws UnsupportedEncodingException {
        List<MortgageDto> mortgageDtos = csvUtils.readCustomerData(customerBytes)
                .stream()
                .peek(this::calculateInstalment)
                .collect(Collectors.toList());
        return mortgageDtos;
    }

    public List<MortgageDto> retrieveAllLoans() {
        return mortgageService.retrieveAllLoans();
    }

    public List<CustomerDto> retrieveAllCustomers() {
        return mortgageService.retrieveAllCustomers();
    }

    public void saveNewLoan(MortgageDto mortgageDto){
        mortgageService.saveNewMortgageLoan(mortgageDto);
    }

    public void updateLoan(Long loanId, MortgageDto mortgageDto){
        mortgageService.updateMortgageLoan(loanId, mortgageDto);
    }

    public void saveMortgageLoans(List<MortgageDto> mortgageDtos){
        mortgageDtos.forEach(this::saveNewLoan);
    }

    private MortgageDto calculateInstalment(MortgageDto dto){
        dto.setMonthlyInstalment(mortgageService.calculateMonthlyInstalment(
                dto.getLoanAmount(),
                dto.getInterestRate(),
                dto.getLoanDurationYears() * 12));
        return dto;
    }

    public MortgageDto getMortgageLoanById(Long id){
        return mortgageService.findMortgageLoanById(id);
    }

    public void deleteMortgageLoan(Long id){
        mortgageService.deleteMortgageLoanById(id);
    }
}
