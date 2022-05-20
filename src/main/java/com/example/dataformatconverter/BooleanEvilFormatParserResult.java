package com.example.dataformatconverter;

public class BooleanEvilFormatParserResult extends EvilFormatParserResult<Boolean> {
    private final Boolean value;

    public BooleanEvilFormatParserResult(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }
}
