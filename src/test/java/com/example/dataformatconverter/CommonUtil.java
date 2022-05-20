package com.example.dataformatconverter;

import java.io.*;

public class CommonUtil {
    public static String getResourceContents(String resourceFilePath) throws IOException {
        File resourceFile = new File(CommonUtil.class.getClassLoader().getResource(resourceFilePath).getFile());
        Reader reader = new InputStreamReader(new FileInputStream(resourceFile));
        try {
            StringBuilder builder = new StringBuilder();

            char[] buffer = new char[1000];
            do {
                int count = reader.read(buffer);
                if (count > 0) {
                    builder.append(buffer, 0, count);
                } else {
                    break;
                }
            } while (true);
            return builder.toString();
        } finally {
            try {
                reader.close();
            } catch (IOException ignored) {
            }
        }
    }
}
