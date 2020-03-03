package com.bridgelabz.census;
import com.opencsv.bean.CsvBindByName;

public class IndiaStateCensus {
    @CsvBindByName(column = "State", required = true)
    private String state;

    @CsvBindByName(column = "Population", required = true)
    private int population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    private String areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    private String densityPerSqKm;

    @Override
    public String toString(){
        return "State Census {"+
                "State='" + state + '\'' +
                ", Population='" + population + '\'' +
                ", AreaInSqKm='" + areaInSqKm + '\'' +
                ", DensityPerSqKm='" + densityPerSqKm + '\'' +
                '}';
    }

}
