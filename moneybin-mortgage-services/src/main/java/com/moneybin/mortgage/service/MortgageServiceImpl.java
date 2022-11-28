package com.moneybin.mortgage.service;

import com.moneybin.mortgage.domain.Customer;
import com.moneybin.mortgage.domain.MortgageLoan;
import com.moneybin.mortgage.domain.repository.CustomersRepo;
import com.moneybin.mortgage.domain.repository.MortgageLoansRepo;
import com.moneybin.mortgage.dto.CustomerDto;
import com.moneybin.mortgage.dto.MortgageDto;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class MortgageServiceImpl implements MortgageService {

    private MortgageLoansRepo mortgageLoansRepo;
    private CustomersRepo customersRepo;

    @Override
    public BigDecimal calculateMonthlyInstalment(BigDecimal loanAmount, BigDecimal interestRatePercentage, Integer periodInMonths) {
        BigDecimal interestRate = interestRatePercentage.divide(BigDecimal.valueOf(100),12, RoundingMode.HALF_EVEN);
        BigDecimal a = loanAmount.multiply(interestRate.multiply(
                interestRate.add(BigDecimal.ONE).pow(periodInMonths)));
        BigDecimal b = (interestRate.add(BigDecimal.ONE).pow(periodInMonths)).subtract(BigDecimal.ONE);
        return a.divide(b,2, RoundingMode.HALF_EVEN);
    }

    public List<MortgageDto> retrieveAllLoans(){
        List<MortgageDto> mortgageDtos = new ArrayList<>();
        mortgageLoansRepo.findAll().forEach(mortgageLoan -> mortgageDtos.add(createMortgageLoanDto(mortgageLoan)));
        return mortgageDtos;
    }

    private MortgageDto createMortgageLoanDto(MortgageLoan mortgageLoan) {
        return MortgageDto.builder()
                .loanAmount(mortgageLoan.getLoanAmount())
                .loanDurationYears(mortgageLoan.getLoanDurationYears())
                .interestRate(mortgageLoan.getInterestRate())
                .customerName(mortgageLoan.getCustomer().getName())
                .monthlyInstalment(mortgageLoan.getMonthlyInstalment())
                .loanId(mortgageLoan.getId())
                .build();
    }

    @Override
    public List<CustomerDto> retrieveAllCustomers() {
        List<CustomerDto> customerDtos = new ArrayList<>();
        customersRepo.findAll().forEach(customer -> customerDtos.add(CustomerDto.builder()
                .customerName(customer.getName())
                .customerId(customer.getId())
                .build()));
        return customerDtos;
    }

    @Override
    public void saveNewMortgageLoan(MortgageDto mortgageDto) {
        Customer customer = customersRepo.findCustomerByName(mortgageDto.getCustomerName())
                .orElse(new Customer(mortgageDto.getCustomerName()));
        customersRepo.save(customer);
        MortgageLoan mortgageLoan = mortgageLoansRepo
                .findById(mortgageDto.getLoanId() == null ? 0 : mortgageDto.getLoanId())
                .orElse(
                        MortgageLoan.builder()
                            .loanAmount(mortgageDto.getLoanAmount())
                            .loanDurationYears(mortgageDto.getLoanDurationYears())
                            .monthlyInstalment(mortgageDto.getMonthlyInstalment() == null ? BigDecimal.ZERO : mortgageDto.getMonthlyInstalment())
                            .customer(customer)
                            .interestRate(mortgageDto.getInterestRate())
                        .build());
        mortgageLoansRepo.save(updateMortgage(mortgageLoan, mortgageDto));
    }

    @Override
    public void updateMortgageLoan(Long loanId, MortgageDto mortgageDto) {
        MortgageLoan mortgageLoan = mortgageLoansRepo.findById(loanId).orElseThrow(() -> new IllegalArgumentException("No loans found by id " + loanId));
        MortgageLoan updatedMortgageLoan = updateMortgage(mortgageLoan, mortgageDto);
        BigDecimal newInstalment = calculateMonthlyInstalment(updatedMortgageLoan.getLoanAmount(),
                updatedMortgageLoan.getInterestRate(),
                updatedMortgageLoan.getLoanDurationYears() * 12);
        updatedMortgageLoan.setMonthlyInstalment(newInstalment);
        mortgageLoansRepo.save(updatedMortgageLoan);
    }

    @Override
    public MortgageDto findMortgageLoanById(Long loanId) {
        MortgageLoan mortgageLoan = mortgageLoansRepo.findById(loanId).orElseThrow(() -> new IllegalArgumentException("No loans found by id " + loanId));
        return createMortgageLoanDto(mortgageLoan);
    }

    @Override
    public void deleteMortgageLoanById(Long loanId) {
        MortgageLoan mortgageLoan = mortgageLoansRepo.findById(loanId).orElseThrow(() -> new IllegalArgumentException("No loans found by id " + loanId));
        mortgageLoansRepo.delete(mortgageLoan);
    }

    private MortgageLoan updateMortgage(MortgageLoan loan, MortgageDto mortgageDto){
        loan.setLoanAmount(mortgageDto.getLoanAmount());
        loan.setLoanDurationYears(mortgageDto.getLoanDurationYears());
        loan.setInterestRate(mortgageDto.getInterestRate());
        loan.setMonthlyInstalment(mortgageDto.getMonthlyInstalment());
        return loan;
    }
}
