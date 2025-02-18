package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import repository.AttendanceRepository;

public class FileController {

    private final String FILE_ERROR_MESSAGE = "파일을 불러오는 데 문제가 발생했습니다. 잠시 후 다시 시도해 주세요.";
    private final AttendanceRepository attendanceRepository;

    public FileController(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public void initializeFile(String filePath) {
        try {
            createRecords(loadFile(filePath));
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

    private void createRecords(Scanner attendaceScanner) {
        while (attendaceScanner.hasNextLine()) {
            String[] attr = attendaceScanner.nextLine().split(",");
            String name = attr[0];

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(attr[1], formatter);

            attendanceRepository.attend(name, dateTime);
        }
    }
}
