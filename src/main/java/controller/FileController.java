package controller;

import domain.AttendanceManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileController {

    private static final String FILE_ERROR_MESSAGE = "파일을 불러오는 데 문제가 발생했습니다. 잠시 후 다시 시도해 주세요.";

    private final AttendanceManager attendanceManager;

    public FileController(AttendanceManager attendanceManager) {
        this.attendanceManager = attendanceManager;
    }

    public void initializeFile(String filePath) {
        try {
            Map<String, List<LocalDateTime>> result = createRecords(loadFile(filePath));
            for (String name : result.keySet()) {
                attendanceManager.createCrew(name, result.get(name));
            }
        } catch (FileNotFoundException e) {
            System.err.println(FILE_ERROR_MESSAGE);
        }
    }

    private Scanner loadFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        return scanner;
    }

    private Map<String, List<LocalDateTime>> createRecords(Scanner attendaceScanner) {
        Map<String, List<LocalDateTime>> result = new HashMap<>();
        while (attendaceScanner.hasNextLine()) {
            String[] attr = attendaceScanner.nextLine().split(",");
            String name = attr[0];

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(attr[1], formatter);

            result.putIfAbsent(name, new ArrayList<>());
            result.get(name).add(dateTime);
        }
        return result;
    }
}
