package com.moneybin.mortgage.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "CUSTOMER")
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Customer extends BasicIdentity {

    public Customer(String name){
        this.name = name;
    }

    @Column(name = "name", nullable = false, length = 45, unique = true)
    private String name;

    @OneToMany(mappedBy = "customer")
    private List<MortgageLoan> mortgageLoans;
}
