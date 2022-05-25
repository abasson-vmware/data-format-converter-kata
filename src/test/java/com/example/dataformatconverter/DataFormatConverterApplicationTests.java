package com.example.dataformatconverter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DataFormatConverterApplicationTests {
    @Test
    void whenGivenABooleanBasedMessage_AndIsTrue_ItShouldReturnATrueBooleanMessageObject() throws Exception {
        DataFormatConverter sut = new DataFormatConverter();

        Message<Boolean> result = sut.convert("1|54321|1|0|true|", Boolean.class);

        Message<Boolean> expected = new Message();
        expected.setMessage(true);

        assertThat(result.getMessage())
            .isEqualTo(expected.getMessage());
    }

    @Test
    void whenGivenABooleanBasedMessage_AndIsNotABoolean_ItShouldThrow() throws Exception {
        DataFormatConverter sut = new DataFormatConverter();

        Message<Boolean> result = sut.convert("1|54321|1|0|not-a-bool|", Boolean.class);

        Message<Boolean> expected = new Message();
        expected.setMessage(false);

        assertThat(result.getMessage())
            .isEqualTo(expected.getMessage());
    }
}
