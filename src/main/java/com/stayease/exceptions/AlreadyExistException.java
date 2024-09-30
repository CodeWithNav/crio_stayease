package com.stayease.exceptions;

public class AlreadyExistException extends RuntimeException{


    public AlreadyExistException(String message){
        super(message);
    }

}