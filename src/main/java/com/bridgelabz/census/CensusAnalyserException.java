package com.bridgelabz.census;

public class CensusAnalyserException extends RuntimeException{
    public enum ExceptionType{
        UNABLE_TO_PARSE,CENSUS_FILE_PROBLEM, FILE_FORMAT_PROBLEM;
    }
    public ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, Throwable cause, ExceptionType type) {
        super(message, cause);
        this.type = type;
    }
}
