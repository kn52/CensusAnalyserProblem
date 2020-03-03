package com.bridgelabz.census;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class CensusAnalyser {
    public void WelcomeMessage() {
        System.out.println("Welcome to census analyser problem");
    }

    public int loadData(String csvFilePath) throws Exception {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<IndianStateCensus> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndianStateCensus.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndianStateCensus> csvToBean = csvToBeanBuilder.build();
            Iterator<IndianStateCensus> censusCSVIterator = csvToBean.iterator();
            int namOfEnteries = 0;
            while (censusCSVIterator.hasNext()) {
                namOfEnteries++;
                IndianStateCensus censusData = censusCSVIterator.next();
            }
            return namOfEnteries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

    }
}
