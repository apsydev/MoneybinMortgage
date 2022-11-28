package com.moneybin.mortgage.service;

import com.moneybin.mortgage.domain.repository.CustomersRepo;
import com.moneybin.mortgage.domain.repository.MortgageLoansRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class MortgageServiceTest {

    @Mock
    public MortgageLoansRepo mortgageLoansRepo;

    @Mock
    public CustomersRepo customersRepo;

    @InjectMocks
    public MortgageServiceImpl mortgageService;

    @Test
    public void n001_successTestMortgage(){
        BigDecimal result = mortgageService.calculateMonthlyInstalment(new BigDecimal("3200000"), new BigDecimal("0.03"), 90);
        Assertions.assertEquals(result, new BigDecimal("103217.79"));
    }

    @Test
    public void n002_successTestMortgage2(){
        BigDecimal result = mortgageService.calculateMonthlyInstalment(new BigDecimal("1000"), new BigDecimal("0.05"), 36);
        Assertions.assertEquals(result, new BigDecimal("60.43"));
    }

    @Test
    public void n002_successCSVCalculation() throws URISyntaxException, IOException {
        byte[] data = Files.readAllBytes(
                Paths.get(this.getClass().getClassLoader().getResource("loans.csv").toURI()));

    }
}
