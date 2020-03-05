package com.bridgelabz.census;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusLoader {

    public Map<String, CensusDTO> loadCensusData(CensusAnalyser.Country country, String[] csvFilePath) {
        if(country.equals(CensusAnalyser.Country.INDIA))
            return this.loadCensusData(IndiaStateCensus.class,csvFilePath);
        else if(country.equals(CensusAnalyser.Country.US))
            return this.loadCensusData(USStateCensus.class,csvFilePath);
        else
            throw new CensusAnalyserException("Incorrect country",CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }

    private <E> Map<String, CensusDTO> loadCensusData(Class<E> className,String ...csvFilePath) {
        Map<String, CensusDTO> censusMap=new HashMap<>();
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));) {
            ICSVBuilder csvBuilder=CSVBuilderFactory.createBuilder();
            Iterator<E> stateCensus= csvBuilder.getCSVFileIterator(reader,className);
            Iterable<E> stateCensusIterable=()->stateCensus;
            System.out.println(className.getName());
            if (className.getName().equals("com.bridgelabz.census.IndiaStateCensus")){
                StreamSupport.stream(stateCensusIterable.spliterator(),false)
                        .map(IndiaStateCensus.class::cast)
                        .forEach(indiaStateCode -> censusMap.put(indiaStateCode.state,new CensusDTO(indiaStateCode)));
            }
            else if(className.getName().equals("com.bridgelabz.census.USStateCensus")){
                StreamSupport.stream(stateCensusIterable.spliterator(),false)
                        .map(USStateCensus.class::cast)
                        .forEach(indiaStateCode -> censusMap.put(indiaStateCode.state,new CensusDTO(indiaStateCode)));
            }
            if(csvFilePath.length==1)
                return censusMap;
            loadDataIndiaStateCode(censusMap,csvFilePath[1]);
            return censusMap;
        } catch (IOException ioe) {
            throw new CensusAnalyserException(ioe.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }


    }

    private int loadDataIndiaStateCode(Map<String, CensusDTO> censusMap, String csvFilePath) {
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder=CSVBuilderFactory.createBuilder();
            Iterator<IndiaStateCode> codeIterator= csvBuilder.getCSVFileIterator(reader,IndiaStateCode.class);
            Iterable<IndiaStateCode> stateCodeCSV= ()-> codeIterator;

            StreamSupport.stream(stateCodeCSV.spliterator(),false)
                    .filter(indianStateCodeCSV -> censusMap.get(indianStateCodeCSV) != null)
                    .forEach(indiaStateCode -> censusMap.get(indiaStateCode).stateCode = indiaStateCode.stateCode);

            return censusMap.size();
        } catch (IOException ioe) {
            throw new CensusAnalyserException(ioe.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

}

