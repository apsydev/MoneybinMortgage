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

    /**
     * Retrieves all mortgage loans
     *
     * @return  list of {@link MortgageDto}
     */
    List<MortgageDto> retrieveAllLoans();

    /**
     * Retrieves all customers
     *
     * @returnlist of {@link CustomerDto}
     */
    List<CustomerDto> retrieveAllCustomers();

    /**
     * Persist mortgage loan
     *
     * @param mortgageDto  mortgage loan to be saved
     */
    void saveNewMortgageLoan(MortgageDto mortgageDto);

    /**
     * Updates mortgage loan data
     *
     * @param loanId       mortgage loan id
     * @param mortgageDto  mortgage loan to be updated
     */
    void updateMortgageLoan(Long loanId, MortgageDto mortgageDto);

    /**
     * Fetch mortgage loan by id
     *
     * @param id  id of loan to find
     * @return  mortgageDto  mortgage loan found
     */
    MortgageDto findMortgageLoanById(Long id);

    /**
     * Deletes mortgage loan
     * @param id  of mortgage loan to be deleted
     */
    void deleteMortgageLoanById(Long id);
}
