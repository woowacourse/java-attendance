package util.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileLoader {

    private static final String FILE_LOAD_ERROR_MESSAGE = "파일을 읽어올 수 없습니다.";

    private FileLoader() {
    }

    public static Scanner loadCSV(String filePath) {
        try {
            File file = new File(filePath);
            return new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(FILE_LOAD_ERROR_MESSAGE);
        }
    }
}