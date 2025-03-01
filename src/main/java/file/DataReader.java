package file;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataReader {

    private static final String ATTENDANCES_FILE_PATH = "src/main/resources/attendances.csv";

    public List<String> readRawAttendances() {
        List<String> rawAttendances = new ArrayList<>();
        FileReader fileReader;
        try {
            fileReader = new FileReader(ATTENDANCES_FILE_PATH);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("파일을 읽을 수 없습니다.");
        }

        Scanner scanner = new Scanner(fileReader);
        scanner.nextLine();
        while (scanner.hasNext()) {
            rawAttendances.add(scanner.nextLine());
        }

        return rawAttendances;
    }
}
