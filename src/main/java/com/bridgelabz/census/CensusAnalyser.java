package com.bridgelabz.census;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {

    private Country country;

    public enum Country{ INDIA,US }
    public enum SortFieldBy { STATE,POPULATION,TOTAL_AREA,POPULATION_DENSITY }
    Map<SortFieldBy,Comparator <CensusDTO>> sortField =null;
    Map<String, CensusDTO> censusMap=null;

    public CensusAnalyser(Country country) {
        this.country=country;
        sortField = new HashMap<>();
        sortField.put(SortFieldBy.STATE,Comparator.comparing(census->census.state));
        sortField.put(SortFieldBy.POPULATION,Comparator.comparing(census->census.state,Comparator.reverseOrder()));
        sortField.put(SortFieldBy.TOTAL_AREA,Comparator.comparing(census->census.totalArea,Comparator.reverseOrder()));
        sortField.put(SortFieldBy.POPULATION_DENSITY,Comparator.comparing(census->census.populationDensity,Comparator.reverseOrder()));
    }

    public String getSortCensusData(SortFieldBy byField) {
        if( censusMap.size()==0 || censusMap == null  ){
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List censusList=censusMap.values().stream()
                .sorted(sortField.get(byField))
                .map(x->x.getCensusDTO(x))
                .collect(Collectors.toList());
        return new Gson().toJson(censusList);
    }

    public int loadDataCensus(Country country,String ...csvFilePath) {
        censusMap=CensusAdaptorFactory.getCensusData(country,csvFilePath);
        return censusMap.size();
    }
}