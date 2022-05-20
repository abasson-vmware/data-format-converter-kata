package com.example.dataformatconverter;

public class StringEvilFormatParserResult extends EvilFormatParserResult<String> {

    private final String value;

    public StringEvilFormatParserResult(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
