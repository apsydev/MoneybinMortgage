package com.moneybin.mortgage.domain.configuration;

import com.moneybin.mortgage.domain.repository.CustomersRepo;
import com.moneybin.mortgage.domain.repository.MortgageLoansRepo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpringDomainConfig {

    @Autowired
    private MortgageLoansRepo mortgageLoansRepo;

    @Autowired
    private CustomersRepo customersRepo;
}
