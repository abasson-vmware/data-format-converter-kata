package com.example.dataformatconverter;

public class EvilFormatParser {
    public EvilFormatParserResult parse(String message) {
        String[] elements = message.split("\\|");

        if (isBooleanResponse(elements)) {
            Boolean value = Boolean.valueOf(elements[4]);
            return new BooleanEvilFormatParserResult(value);
        } else if (isErrorResponse(elements)) {
            return new StringEvilFormatParserResult(elements[4]);
        }

        return new StringEvilFormatParserResult(elements[7]);
    }

    private boolean isErrorResponse(String[] elements) {
        String fifthElement = elements[4];

        try {
            Integer.parseInt(fifthElement);
            return false;
        } catch (Exception ignored) {

        }

        return true;
    }

    private boolean isBooleanResponse(String[] elements) {
        String fifthElement = elements[4];
        return fifthElement.equals("true") || fifthElement.equals("false");
    }
}
