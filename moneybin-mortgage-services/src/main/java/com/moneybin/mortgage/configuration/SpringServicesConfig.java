package com.moneybin.mortgage.configuration;

import com.moneybin.mortgage.service.*;
import com.moneybin.mortgage.domain.configuration.SpringDomainConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@Getter
public class SpringServicesConfig {

    @Autowired
    private final SpringDomainConfig springDomainConfig;

    public SpringServicesConfig(SpringDomainConfig springDomainConfig) {
        this.springDomainConfig = springDomainConfig;
    }

    @Bean
    public MortgageService getMortgageService(){
        return new MortgageServiceImpl(springDomainConfig.getMortgageLoansRepo(), springDomainConfig.getCustomersRepo());
    }

    @Bean
    public MortgageCalculationFacade mortgageCalculationFacade(){
        return new MortgageCalculationFacade(getMortgageService(), new CsvUtils());
    }

    @Bean
    public PreloadDataRunner preloadDataRunner(){
        return new PreloadDataRunner(mortgageCalculationFacade());
    }
}
