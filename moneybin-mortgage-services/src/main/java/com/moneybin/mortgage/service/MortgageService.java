package com.moneybin.mortgage.service;

import com.moneybin.mortgage.dto.CustomerDto;
import com.moneybin.mortgage.dto.MortgageDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service to calculate Mortgage parameters
 *
 * @author pavel.sychykau
 */
public interface MortgageService {

    /**
     * Performs calculation of an amount to be paid each month for mortgage loan by the formula:
     *
     * E = U[b(1 + b)^p]/[(1 + b)^p - 1], where
     * E = Fixed monthly payment
     * b = Interest on a monthly basis
     * U = Total loan
     * p = Number of payments
     *
     * @param loanAmount  amount of loan (U)
     * @param interestRate  rate for the loan (b)
     * @param periodInMonths  total loan duration in months(p)
     * @return  fixed monthly payment (E)
     */
    BigDecimal calculateMonthlyInstalment(BigDecimal loanAmount, BigDecimal interestRate, Integer periodInMonths);

    List<MortgageDto> retrieveAllLoans();

    List<CustomerDto> retrieveAllCustomers();

    void saveNewMortgageLoan(MortgageDto mortgageDto);

    void updateMortgageLoan(Long loanId, MortgageDto mortgageDto);

    MortgageDto findMortgageLoanById(Long id);

    void deleteMortgageLoanById(Long id);
}
