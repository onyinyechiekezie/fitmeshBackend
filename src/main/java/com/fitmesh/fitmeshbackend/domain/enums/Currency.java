package com.fitmesh.fitmeshbackend.domain.enums;

public enum Currency {

    NGN("₦"),
    USD("$");

    private final String symbol;

    Currency(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
