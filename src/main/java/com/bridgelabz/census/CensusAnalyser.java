package com.bridgelabz.census;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<CensusDTO> censusList=null;

    Map<String, CensusDTO> censusMap=null;

    public CensusAnalyser() {
        censusList = new ArrayList();
        censusMap=new HashMap();
    }


    public int loadDataIndiaStateCensus(String csvFilePath) {
        censusMap=new CensusLoader().loadCensusData(csvFilePath,IndiaStateCensus.class);
        censusList=censusMap.values().stream().collect(Collectors.toList());
        return censusMap.size();
    }

    public int loadDataUSStateCensus(String csvFilePath) {
        censusMap=new CensusLoader().loadCensusData(csvFilePath,USStateCensus.class);
        censusList=censusMap.values().stream().collect(Collectors.toList());
        return censusMap.size();
    }

    public int loadDataIndiaStateCode(String csvFilePath){
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

    private <E> int getCount(Iterator<E> Iterator) {
        Iterable<E> census=()-> Iterator;
        int numOfEnteries =(int) StreamSupport.stream(census.spliterator(),false).count();
        return numOfEnteries;
    }

    public String getStateWiseSortCensusData() {
        if( censusList.size()==0 || censusList== null  ){
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDTO> censusComparator= Comparator.comparing(stateCensus->stateCensus.state);
        sort(censusComparator);
        String json=new Gson().toJson(censusList);
        return json;
    }

    public String getPopulationWiseSortCensusData() {
        if( censusList.size()==0 || censusList== null  ){
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDTO> censusComparator= Comparator.comparing(stateCensus->stateCensus.population);
        sort(censusComparator);
        String json=new Gson().toJson(censusList);
        return json;
    }

    public String getDensityWiseSortCensusData() {
        if( censusList.size()==0 || censusList== null  ){
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDTO> censusComparator= Comparator.comparing(stateCensus->stateCensus.populationDensity);
        sort(censusComparator);
        String json=new Gson().toJson(censusList);
        return json;
    }

    public String getAreaWiseSortCensusData() {
        if( censusList.size()==0 || censusList== null  ){
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDTO> censusComparator= Comparator.comparing(stateCensus->stateCensus.totalArea);
        sort(censusComparator);
        String json=new Gson().toJson(censusList);
        return json;
    }

   private void sort(Comparator<CensusDTO> censusComparator) {
        for (int i=0;i<censusList.size();i++){
            for(int j=0;j<censusList.size()-i-1;j++){
                CensusDTO census1=censusList.get(j);
                CensusDTO census2=censusList.get(j+1);
                if(censusComparator.compare(census1,census2)>0){
                    censusList.set(j,census2);
                    censusList.set(j+1,census1);
                }
            }
        }

    }


}
