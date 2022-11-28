package com.moneybin.mortgage.service;

import com.moneybin.mortgage.dto.MortgageDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PreloadDataRunner implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(PreloadDataRunner.class);

    @NonNull
    private MortgageCalculationFacade facade;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(facade.retrieveAllLoans().size() > 0) return;

        List<MortgageDto> mortgageDtos = readAndCreateMortageDto();

        for(int i = 0; i < mortgageDtos.size(); i++){
            MortgageDto mortgageDto = mortgageDtos.get(i);
            logger.info("Prospect {}: {} wants to borrow {} € for a period of {} years and pay {} € each month",
                    i+1,
                    mortgageDto.getCustomerName(),
                    mortgageDto.getLoanAmount(),
                    mortgageDto.getLoanDurationYears(),
                    mortgageDto.getMonthlyInstalment());
        }
    }

    /*private List<MortgageDto> readAndCreateMortageDto() throws IOException, URISyntaxException {
        List<MortgageDto> mortgageDtos = new ArrayList<>();
        URL resource = this.getClass().getClassLoader().getResource("loans.csv");
        if(resource != null) {
            Path path = Paths.get(resource.toURI());
            byte[] data = Files.readAllBytes(path);
            mortgageDtos = facade.calculateMonthlyInstalment(data);
            facade.saveMortgageLoans(mortgageDtos);
        }
        return mortgageDtos;
    }*/
    private List<MortgageDto> readAndCreateMortageDto() throws IOException, URISyntaxException {
        Resource resource = resourceLoader.getResource("classpath:loans.csv");
        InputStream inputStream = resource.getInputStream();
        List<MortgageDto> mortgageDtos = new ArrayList<>();
        try
        {
            byte[] data = FileCopyUtils.copyToByteArray(inputStream);
            mortgageDtos = facade.calculateMonthlyInstalment(data);
            facade.saveMortgageLoans(mortgageDtos);
        }
        catch (IOException e)
        {
            logger.error("IO Error during file read ", e);
        }
        return mortgageDtos;
    }
}
