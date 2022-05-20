package com.example.dataformatconverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DataFormatterTests {
    private EvilFormatParser parser;

    @BeforeEach
    public void setup() {
        parser = new EvilFormatParser();
    }

    @Test
    public void itParsesBooleanMessages() {
        EvilFormatParserResult<Boolean> trueResponse = parser.parse("1|54321|1|0|true|");
        EvilFormatParserResult<Boolean> falseResponse = parser.parse("1|54321|1|0|false|");

        assertThat(trueResponse.getValue()).isTrue();
        assertThat(trueResponse).isInstanceOf(BooleanEvilFormatParserResult.class);
        assertThat(falseResponse.getValue()).isFalse();
    }

    @Test
    public void itParsesStringMessages() {
        EvilFormatParserResult<String> response1 = parser.parse("1|23456|1|0|1|1|8|a text value|");
        EvilFormatParserResult<String> response2 = parser.parse("1|23456|1|0|1|1|8|another text value|");

        assertThat(response1.getValue()).isEqualTo("a text value");
        assertThat(response1).isInstanceOf(StringEvilFormatParserResult.class);
        assertThat(response2.getValue()).isEqualTo("another text value");
    }

    @Test
    public void itParsesErrorMessages() {
        EvilFormatParserResult<String> response1 = parser.parse("1|0|1|99|some error message|");
        EvilFormatParserResult<String> response2 = parser.parse("1|0|1|0|some other error message|");

        assertThat(response1.getValue()).isEqualTo("some error message");
        assertThat(response2.getValue()).isEqualTo("some other error message");
    }

}
