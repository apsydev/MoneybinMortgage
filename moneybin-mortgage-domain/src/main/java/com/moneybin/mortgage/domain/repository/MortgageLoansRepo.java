package com.moneybin.mortgage.domain.repository;

import com.moneybin.mortgage.domain.MortgageLoan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MortgageLoansRepo extends CrudRepository<MortgageLoan, Long> {
}
