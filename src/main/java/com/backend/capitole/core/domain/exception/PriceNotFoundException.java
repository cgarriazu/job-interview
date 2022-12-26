package com.backend.capitole.core.domain.exception;

public class PriceNotFoundException extends RuntimeException{
    public PriceNotFoundException(){
        super("Price not found.");
    }
}
