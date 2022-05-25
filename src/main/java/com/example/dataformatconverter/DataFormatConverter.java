package com.example.dataformatconverter;

public class DataFormatConverter {
    <T> Message<T> convert(String rawData, Class<T> clazz) {
        Message<T> obj = new Message<>();

        String[] elements = rawData.split("\\|");

        if (isBoolean(clazz)) {
            Boolean boolValue = elements[4].equals("true");
            obj.setMessage((T) boolValue);
        }

        return obj;
    }

    private <T> boolean isBoolean(Class<T> clazz) {
        return clazz.getName().equals(Boolean.class.getName());
    }
}
