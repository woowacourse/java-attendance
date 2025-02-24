package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataReader {

    public List<String> readAttendances(String path) {
        List<String> rawAttendances = new ArrayList<>();
        FileReader fileReader;
        try {
            fileReader = new FileReader(path);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        }

        Scanner scanner = new Scanner(fileReader);
        scanner.nextLine();
        while (scanner.hasNext()) {
            rawAttendances.add(scanner.nextLine());
        }

        return rawAttendances;
    }
}
