package util;

import static constant.ErrorMessage.FILE_READ_ERROR;
import static constant.ErrorMessage.NOT_FOUND_FILE;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class FileParser {

    public static List<String> readLines(String resourcePath) {
        InputStream inputStream = FileParser.class.getClassLoader().getResourceAsStream(resourcePath);

        if (inputStream == null) {
            throw new IllegalArgumentException(NOT_FOUND_FILE.getMessage());
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines()
                    .skip(1)
                    .toList();
        } catch (Exception e) {
            throw new IllegalArgumentException(FILE_READ_ERROR.getMessage());
        }
    }
}
