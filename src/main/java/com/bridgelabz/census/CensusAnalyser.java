package com.bridgelabz.census;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public int loadData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<IndianStateCensus> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndianStateCensus.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndianStateCensus> csvToBean = csvToBeanBuilder.build();
            Iterator<IndianStateCensus> censusCSVIterator = csvToBean.iterator();
            Iterable<IndianStateCensus> census=()->censusCSVIterator;
            int numOfEnteries =(int) StreamSupport.stream(census.spliterator(),false).count();
            return numOfEnteries;
        } catch (IOException ioe) {
            throw new CensusAnalyserException(ioe.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        catch (IllegalStateException ise){
            throw new CensusAnalyserException(ise.getMessage(),CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }

    }
}
