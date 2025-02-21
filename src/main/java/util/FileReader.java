package util;

import domain.AttendanceBook;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class FileReader {
    private static final String PATH = "src/main/attendance.csv";

    public static AttendanceBook readExistedAttendanceData() {
        AttendanceBook attendanceBook = new AttendanceBook();
        try {
            java.io.FileReader fileReader = new java.io.FileReader(PATH);
            Scanner scanner = new Scanner(fileReader);
            readPerLine(attendanceBook, scanner);
        } catch (FileNotFoundException e) {
            System.out.println("파일 경로가 유효하지 않습니다.");
        }
        return attendanceBook;
    }

    private static void readPerLine(AttendanceBook attendanceBook, Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String crewName = line.split(",")[0];
            addNewCrewWhenNotExisted(attendanceBook, crewName);
            initializeCrewInfo(attendanceBook, line, crewName);
        }
    }

    private static void addNewCrewWhenNotExisted(AttendanceBook attendanceBook, String name) {
        if (!attendanceBook.contains(name)) {
            attendanceBook.enter(name);
        }
    }

    private static void initializeCrewInfo(AttendanceBook attendanceBook, String line, String name) {
        String[] dateAndTime = line.split(",")[1].split(" ");
        LocalDateTime localDateTime = LocalDateTime.of(Integer.parseInt(dateAndTime[0]),
                Integer.parseInt(dateAndTime[1]),
                Integer.parseInt(dateAndTime[2]),
                Integer.parseInt(dateAndTime[3]),
                Integer.parseInt(dateAndTime[4]));
        attendanceBook.add(name, localDateTime);
    }
}
