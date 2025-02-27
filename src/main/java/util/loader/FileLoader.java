package util.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileLoader {

    private FileLoader() {
    }

    public static Scanner loadCSV(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            scanner.nextLine(); // attribute 행 제거
            return scanner;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        }
    }
}
