 package com.bridgelabz.census.test;

import com.bridgelabz.census.CensusAnalyser;
import com.bridgelabz.census.CensusAnalyserException;
import com.bridgelabz.census.IndiaStateCensus;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class CensusAnalyserTest {

    CensusAnalyser analyser;
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CENSUS_WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CENSUS_WRONG_EXT_CSV_FILE = "./src/test/resources/IndiaStateCensusData.cv";
    private static final String INDIA_STATE_CSV_FILE_PATH="./src/test/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_CSV_FILE_PATH="./src/test/resources/USCensusData.csv";
    private static final String INDIA_STATE_WRONG_CSV_FILE_PATH="./src/resources/IndiaStateCode.csv";

    @Test
    public void givenIndiaStateCensusPath_Correct_ShouldReturn_NoOfRecords() {
        try {
            analyser=new CensusAnalyser(CensusAnalyser.Country.INDIA);
            int count=analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,count);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndiaStateCensusPath_Wrong_ShouldThrow_Exception() {
        try {
            analyser=new CensusAnalyser(CensusAnalyser.Country.INDIA);
            analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCensusExtension_Wrong_ShouldThrow_Exception() {
        try {
            analyser=new CensusAnalyser(CensusAnalyser.Country.INDIA);
            analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_WRONG_EXT_CSV_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodePath_Correct_ShouldReturn_NoOfRecords() {
        try {
            analyser=new CensusAnalyser(CensusAnalyser.Country.INDIA);
            int count=analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CSV_FILE_PATH);
            Assert.assertEquals(29,count);
        } catch (CensusAnalyserException e) {  }
    }

    @Test
    public void givenIndiaStateCodePath_Wrong_ShouldReturn_Exception() {
        try {
            analyser=new CensusAnalyser(CensusAnalyser.Country.INDIA);
            int count=analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCensus_WhenSortedOnState_ShouldReturnSortedResult() {
        try{
            analyser=new CensusAnalyser(CensusAnalyser.Country.INDIA);
            analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortCensusData = analyser.getSortCensusData(CensusAnalyser.SortFieldBy.STATE);
            IndiaStateCensus[] censusCsv = new Gson().fromJson(sortCensusData, IndiaStateCensus[].class);
            Assert.assertEquals("Andhra Pradesh",censusCsv[0].state);
        }catch(CensusAnalyserException e){}
    }

    @Test
    public void givenIndiaStateCensus_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        try{
            analyser=new CensusAnalyser(CensusAnalyser.Country.INDIA);
            analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortCensusData = analyser.getSortCensusData(CensusAnalyser.SortFieldBy.POPULATION);
            IndiaStateCensus[] censusCsv = new Gson().fromJson(sortCensusData, IndiaStateCensus[].class);
            Assert.assertEquals(199812341,censusCsv[0].population);
        }catch(CensusAnalyserException e){}

    }

    @Test
    public void givenIndiaStateCensus_WhenSortedOnDensity_ShouldReturnSortedResult() {
        try{
            analyser=new CensusAnalyser(CensusAnalyser.Country.INDIA);
            analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortCensusData = analyser.getSortCensusData(CensusAnalyser.SortFieldBy.POPULATION_DENSITY);
            IndiaStateCensus[] censusCsv = new Gson().fromJson(sortCensusData, IndiaStateCensus[].class);
            Assert.assertEquals(103804637,censusCsv[0].population);
        }catch(CensusAnalyserException e){}

    }

    @Test
    public void givenIndiaStateCensus_WhenSortedOnArea_ShouldReturnSortedResult() {
        try{
            analyser=new CensusAnalyser(CensusAnalyser.Country.INDIA);
            analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortCensusData = analyser.getSortCensusData(CensusAnalyser.SortFieldBy.TOTAL_AREA);
            IndiaStateCensus[] censusCsv = new Gson().fromJson(sortCensusData, IndiaStateCensus[].class);
            Assert.assertEquals(1457723,censusCsv[0].population);
        }catch(CensusAnalyserException e){}

    }

    @Test
    public void givenUSStateCensusPath_Correct_ShouldReturn_NoOfRecords() {
        try {
            analyser=new CensusAnalyser(CensusAnalyser.Country.US);
            int count=analyser.loadDataCensus(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51,count);
        } catch (CensusAnalyserException e) { }
    }
    @Test
    public void givenUSStateCensus_WhenSortedOnState_ShouldReturnSortedResult() {
        try{
            analyser=new CensusAnalyser(CensusAnalyser.Country.US);
            analyser.loadDataCensus(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
            String sortCensusData = analyser.getSortCensusData(CensusAnalyser.SortFieldBy.STATE);
            IndiaStateCensus[] censusCsv = new Gson().fromJson(sortCensusData, IndiaStateCensus[].class);
            Assert.assertEquals("Alabama",censusCsv[0].state);
        }catch(CensusAnalyserException e){}
    }

    @Test
    public void givenUSStateCensus_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        try{
            analyser=new CensusAnalyser(CensusAnalyser.Country.US);
            analyser.loadDataCensus(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
            String sortCensusData = analyser.getSortCensusData(CensusAnalyser.SortFieldBy.POPULATION);
            IndiaStateCensus[] censusCsv = new Gson().fromJson(sortCensusData, IndiaStateCensus[].class);
            Assert.assertEquals(37253956,censusCsv[0].population);
        }catch(CensusAnalyserException e){}

    }

    @Test
    public void givenUSStateCensus_WhenSortedOnDensity_ShouldReturnSortedResult() {
        try{
            analyser=new CensusAnalyser(CensusAnalyser.Country.US);
            analyser.loadDataCensus(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
            String sortCensusData = analyser.getSortCensusData(CensusAnalyser.SortFieldBy.POPULATION_DENSITY);
            IndiaStateCensus[] censusCsv = new Gson().fromJson(sortCensusData, IndiaStateCensus[].class);
            Assert.assertEquals(601723,censusCsv[0].population);
        }catch(CensusAnalyserException e){}

    }

    @Test
    public void givenUSStateCensus_WhenSortedOnArea_ShouldReturnSortedResult() {
        try{
            analyser=new CensusAnalyser(CensusAnalyser.Country.US);
            analyser.loadDataCensus(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
            String sortCensusData = analyser.getSortCensusData(CensusAnalyser.SortFieldBy.TOTAL_AREA);
            IndiaStateCensus[] censusCsv = new Gson().fromJson(sortCensusData, IndiaStateCensus[].class);
            Assert.assertEquals(710231,censusCsv[0].population);
        }catch(CensusAnalyserException e){}

    }

    @Test
    public void givenIndiaUSStateCensus_WhenSortedOnDensity_ShouldReturnSortedResult() {
        try{
            analyser=new CensusAnalyser(CensusAnalyser.Country.INDIA);
            analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortCensusData1 = analyser.getSortCensusData(CensusAnalyser.SortFieldBy.POPULATION);
            IndiaStateCensus[] censusCsv1 = new Gson().fromJson(sortCensusData1, IndiaStateCensus[].class);
            analyser=new CensusAnalyser(CensusAnalyser.Country.US);
            analyser.loadDataCensus(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
            String sortCensusData2 = analyser.getSortCensusData(CensusAnalyser.SortFieldBy.POPULATION_DENSITY);
            String largest;
            IndiaStateCensus[] censusCsv2 = new Gson().fromJson(sortCensusData2, IndiaStateCensus[].class);
            if(censusCsv1[0].population>censusCsv2[0].population)
                largest=censusCsv1[0].state;
            else
                largest=censusCsv2[0].state;
            Assert.assertEquals("INDIA",largest);
        }catch(CensusAnalyserException e){}

    }
}