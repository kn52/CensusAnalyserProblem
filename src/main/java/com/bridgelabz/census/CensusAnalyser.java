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

    public int loadDataIndiaStateCensus(String csvFilePath) throws CensusAnalyserException {

        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            Iterator<IndiaStateCensus> censusCSVIterator = getCSVFileIterator(reader,IndiaStateCensus.class);
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

    public int loadDataIndiaStateCode(String csvFilePath) throws CensusAnalyserException {
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            Iterator<IndiaStateCode> codeCSVIterator=getCSVFileIterator(reader,IndiaStateCode.class);
            Iterable<IndiaStateCode> census=()->codeCSVIterator;
            int numOfEnteries =(int) StreamSupport.stream(census.spliterator(),false).count();
            return numOfEnteries;

        } catch (IOException ioe) {
            throw new CensusAnalyserException(ioe.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private <E> Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) {

        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        }catch (IllegalStateException ise){
            throw new CensusAnalyserException(ise.getMessage(),CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }

    }
}
