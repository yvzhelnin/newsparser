package ru.zhelnin.newsparser.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StringToInputStreamConverter {

    public static String convert(InputStream inputStream) throws IOException {
        byte[] byteArray;
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            int readCount;
            byte[] data = new byte[1024];
            while ((readCount = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, readCount);
            }
            buffer.flush();
            byteArray = buffer.toByteArray();
        }
        return new String(byteArray, "UTF-8");
    }
}
