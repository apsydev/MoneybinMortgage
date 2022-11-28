package com.moneybin.mortgage.service;

import com.moneybin.mortgage.dto.MortgageDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.csv.CSVFormat.DEFAULT;

public class CsvUtils {

    private static final String COMMA = ",";
    private static final String UTF_8 = "UTF8";
    private Logger logger = LoggerFactory.getLogger(CsvUtils.class);

    public List<MortgageDto> readCustomerData(byte[] csvBytes) throws UnsupportedEncodingException {
        List<MortgageDto> customers = new ArrayList<>();
        try {
            CSVParser csvParser = CSVFormat.Builder.create(DEFAULT)
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setDelimiter(",")
                    .build()
                    .parse(
                    new InputStreamReader(new ByteArrayInputStream(csvBytes), UTF_8));
            csvParser.stream()
                    .filter(line -> line.size() == 4)
                    .forEach(line -> customers.add(MortgageDto.builder()
                    .customerName(line.get(0))
                    .loanAmount(new BigDecimal(line.get(1)))
                    .interestRate(new BigDecimal(line.get(2)))
                    .loanDurationYears(Integer.valueOf(line.get(3)))
                    .build()));
        } catch (IOException e) {
            logger.error("Cannot parse csv file");
            throw new IllegalStateException("wrong csv file content",e);
        }
       /*
        try(Scanner scanner = new Scanner(new ByteArrayInputStream(csvBytes))){
            while (scanner.hasNextLine()) {
                String customerLine = scanner.nextLine();
                String[] customerData = customerLine.split(COMMA);
                customers.add(MortgageDto.builder()
                        .customerName(customerData[0])
                        .loanAmount(new BigDecimal(customerData[1]))
                        .interestRate(new BigDecimal(customerData[2]))
                        .loanDurationYears(Integer.valueOf(customerData[3]))
                        .build());
            }
        }*/
        return customers;
    }

}
