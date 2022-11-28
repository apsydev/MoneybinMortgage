package com.moneybin.mortgage.domain.repository;

import com.moneybin.mortgage.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomersRepo extends CrudRepository<Customer, Long> {

    Optional<Customer> findCustomerByName(String name);
}
