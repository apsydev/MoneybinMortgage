package com.moneybin.mortgage.service;

import com.moneybin.mortgage.domain.repository.CustomersRepo;
import com.moneybin.mortgage.domain.repository.MortgageLoansRepo;
import com.moneybin.mortgage.dto.MortgageDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RunWith(SpringRunner.class)
//@SpringBootTest
//@TestPropertySource(
  //      locations = "classpath:application-integrationtest.properties")
public class MortgageFacadeTest {

    @Mock
    private CustomersRepo customersRepo;

    @Mock
    private MortgageLoansRepo mortgageLoansRepo;

    @Autowired
    private MortgageCalculationFacade mortgageCalculationFacade1;

    @Test
    public void n001_successCSVCalculation() throws URISyntaxException, IOException {
        byte[] data = Files.readAllBytes(
                Paths.get(this.getClass().getClassLoader().getResource("loans.csv").toURI()));
        //mortgageCalculationFacade.calculateMonthlyInstalment(data);
        CsvUtils csvUtils = new CsvUtils();
        List<MortgageDto> mortgageDtos = csvUtils.readCustomerData(data);
    }
}
