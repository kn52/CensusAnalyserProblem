package com.bridgelabz.census.test;

import com.bridgelabz.census.CensusAnalyser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CensusAnalyserTest {

    String filePath="/home/admin3/Desktop/knkns/intelliJ/FileCsv/IndiaStateCensusData.csv";
    CensusAnalyser analyser;
    @Test
    public void givenCSVFilePath_ShouldReturn_NoOfRecords() {
        try {
            int count=analyser.loadData(filePath);
            Assert.assertEquals(29,count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() throws Exception {
        analyser=new CensusAnalyser();
    }
}