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

        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            CsvToBeanBuilder<IndiaStateCensus> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaStateCensus.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndiaStateCensus> csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaStateCensus> censusCSVIterator=csvToBean.iterator();
            Iterable<IndiaStateCensus> census=()->censusCSVIterator;
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
