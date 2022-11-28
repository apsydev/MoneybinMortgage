package com.moneybin.mortgage.service;

import com.moneybin.mortgage.domain.repository.CustomersRepo;
import com.moneybin.mortgage.domain.repository.MortgageLoansRepo;
import com.moneybin.mortgage.dto.MortgageDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class MortgageFacadeTest {

    @Mock
    private CustomersRepo customersRepo;

    @Mock
    private MortgageLoansRepo mortgageLoansRepo;

    @InjectMocks
    private MortgageServiceImpl mortgageService;

    private CsvUtils csvUtils;

    private MortgageCalculationFacade mortgageCalculationFacade;

    @BeforeEach
    public void init() {
        csvUtils = new CsvUtils();
        mortgageCalculationFacade = new MortgageCalculationFacade(mortgageService, csvUtils);
    }

    @Test
    public void n001_successCSVCalculation() throws URISyntaxException, IOException {
        byte[] data = Files.readAllBytes(
                Paths.get(this.getClass().getClassLoader().getResource("loans.csv").toURI()));
        List<MortgageDto> mortgageDtos = mortgageCalculationFacade.calculateMonthlyInstalment(data);
        Assertions.assertEquals(4, mortgageDtos.size(), "4 customers should be found");
        Assertions.assertEquals(mortgageDtos
                        .stream()
                        .filter(dto -> dto.getCustomerName()
                                .equals("Juha")).findAny().get()
                        .getMonthlyInstalment(),
                new BigDecimal("72.47"));
        Assertions.assertEquals(mortgageDtos
                        .stream()
                        .filter(dto -> dto.getCustomerName()
                                .equals("Karvinen")).findAny().get()
                        .getMonthlyInstalment(),
                new BigDecimal("92.68"));
    }
}
