package com.moneybin.mortgage.service;

import com.moneybin.mortgage.dto.CustomerDto;
import com.moneybin.mortgage.dto.MortgageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Facade provide a unified interface to a set of interfaces of the system.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MortgageCalculationFacade {

    private MortgageService mortgageService;
    private CsvUtils csvUtils;

    /**
     * Calculates Monthly Instalment per one mortgage loan object
     * @param loan  to calculate Monthly Instalment
     * @return {@link MortgageDto} object with saved Monthly Instalment value
     */
    public MortgageDto calculateMonthlyInstalment(MortgageDto loan){

        loan.setMonthlyInstalment(mortgageService.calculateMonthlyInstalment(
                loan.getLoanAmount(),
                loan.getInterestRate(),
                loan.getLoanDurationYears() * 12));
        return loan;
    }

    /**
     * Calculates Monthly Instalment per list of mortgage loans
     * @param loans  list of loans to calculate Monthly Instalment
     * @return list of {@link MortgageDto} objects with saved Monthly Instalment value
     */
    public List<MortgageDto> calculateMonthlyInstalment(List<MortgageDto> loans){
        List<MortgageDto> mortgageDtos = loans
                .stream()
                .peek(this::calculateInstalment)
                .collect(Collectors.toList());
        return mortgageDtos;
    }

    /**
     * Calculates Monthly Instalment per passed file's byte array of mortgage loans
     * @param customerBytes  byte array of a csv file consisted of mortgage loans
     * @return list of {@link MortgageDto} objects with saved Monthly Instalment value
     */
    public List<MortgageDto> calculateMonthlyInstalment(byte[] customerBytes) throws UnsupportedEncodingException {
        List<MortgageDto> mortgageDtos = csvUtils.readCustomerData(customerBytes)
                .stream()
                .peek(this::calculateInstalment)
                .collect(Collectors.toList());
        return mortgageDtos;
    }

    /**
     * Retrieve mortgage loans from DB
     *
     * @return list of {@link MortgageDto} objects
     */
    public List<MortgageDto> retrieveAllLoans() {
        return mortgageService.retrieveAllLoans();
    }

    /**
     * Retrieve customers from DB
     *
     * @return list of {@link CustomerDto} objects
     */
    public List<CustomerDto> retrieveAllCustomers() {
        return mortgageService.retrieveAllCustomers();
    }

    /**
     * Create new mortgage loan in DB
     * @param mortgageDto  dto of a mortgage loan to be persisted
     */
    public void saveNewLoan(MortgageDto mortgageDto){
        mortgageService.saveNewMortgageLoan(mortgageDto);
    }

    /**
     * Updated existing mortgage loan
     *
     * @param loanId  id of a loan to update
     * @param mortgageDto  object with data to be updated
     */
    public void updateLoan(Long loanId, MortgageDto mortgageDto){
        mortgageService.updateMortgageLoan(loanId, mortgageDto);
    }

    /**
     * Persist list of mortgage loans
     * @param mortgageDtos   objects with data to be persisted
     */
    public void saveMortgageLoans(List<MortgageDto> mortgageDtos){
        mortgageDtos.forEach(this::saveNewLoan);
    }

    /**
     * Retrieve mortgage loan from DB
     * @param id  of a lona to retrieve
     * @return  {@link MortgageDto} object of a loan
     */
    public MortgageDto getMortgageLoanById(Long id){
        return mortgageService.findMortgageLoanById(id);
    }

    /**
     * Deletes mortgage loan from DB
     * @param id  of the loan to be deleted
     */
    public void deleteMortgageLoan(Long id){
        mortgageService.deleteMortgageLoanById(id);
    }

    private MortgageDto calculateInstalment(MortgageDto dto){
        dto.setMonthlyInstalment(mortgageService.calculateMonthlyInstalment(
                dto.getLoanAmount(),
                dto.getInterestRate(),
                dto.getLoanDurationYears() * 12));
        return dto;
    }
}
