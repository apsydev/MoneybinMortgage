package com.moneybin.mortgage.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "LOAN")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class MortgageLoan extends BasicIdentity {

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    @Column(name = "loan_amount", nullable = false)
    private BigDecimal loanAmount;

    @Column(name = "interest_rate", nullable = false)
    private BigDecimal interestRate;

    @Column(name = "loan_duration", nullable = false)
    private Integer loanDurationYears;

    @Column(name = "monthly_instalment")
    private BigDecimal monthlyInstalment;

}
