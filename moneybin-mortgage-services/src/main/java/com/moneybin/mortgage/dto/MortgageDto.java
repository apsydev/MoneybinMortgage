package com.moneybin.mortgage.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MortgageDto implements Serializable {

    private Long loanId;
    private String customerName;
    private BigDecimal loanAmount;
    private BigDecimal interestRate;
    private Integer loanDurationYears;
    private BigDecimal monthlyInstalment;
}
