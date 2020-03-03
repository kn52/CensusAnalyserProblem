package com.bridgelabz.census;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCode {
    @CsvBindByName(column = "StateName", required = true)
    private String stateName;

    @CsvBindByName(column = "StateCode", required = true)
    private String stateCode;

    @Override
    public String toString(){
        return "State Code {"+
                "State Name='" + stateName + '\'' +
                ", State Code='" + stateCode + '\'' +
                '}';
    }
}
