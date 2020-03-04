package com.bridgelabz.census;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<IndiaStateCensus> censusList=null;
    public int loadDataIndiaStateCensus(String csvFilePath) {

        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder=CSVBuilderFactory.createBuilder();
            censusList=csvBuilder.getCSVFileList(reader,IndiaStateCensus.class);
            return censusList.size();
        } catch (IOException ioe) {
            throw new CensusAnalyserException(ioe.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    public int loadDataIndiaStateCode(String csvFilePath){
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder=CSVBuilderFactory.createBuilder();
            List<IndiaStateCode> codeList=csvBuilder.getCSVFileList(reader,IndiaStateCode.class);
            return codeList.size();
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
        Comparator<IndiaStateCensus> censusComparator= Comparator.comparing(stateCensus->stateCensus.state);
        sort(censusComparator);
        String json=new Gson().toJson(censusList);
        return json;
    }

   private void sort(Comparator<IndiaStateCensus> censusComparator) {
        for (int i=0;i<censusList.size();i++){
            for(int j=0;j<censusList.size()-i-1;j++){
                IndiaStateCensus census1=censusList.get(j);
                IndiaStateCensus census2=censusList.get(j+1);
                if(censusComparator.compare(census1,census2)>0){
                        censusList.set(j,census2);
                        censusList.set(j+1,census1);
                }
            }
        }

    }
}
