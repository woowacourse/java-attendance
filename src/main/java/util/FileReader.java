package util;

import domain.AttendanceBook;
import domain.Crew;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class FileReader {
    public static AttendanceBook readExistedAttendanceData() {
        AttendanceBook attendanceBook = new AttendanceBook();
        try {
            java.io.FileReader fileReader = new java.io.FileReader("src/main/attendance.csv");
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

    private static void addNewCrewWhenNotExisted(AttendanceBook attendanceBook, String crewName) {
        if (!attendanceBook.containsCrewName(crewName)) {
            attendanceBook.addCrew(new Crew(crewName));
        }
    }

    private static void initializeCrewInfo(AttendanceBook attendanceBook, String line, String crewName) {
        String[] attendanceDateTime = line.split(",")[1].split(" ");
        LocalDateTime localDateTime = LocalDateTime.of(Integer.parseInt(attendanceDateTime[0]),
                Integer.parseInt(attendanceDateTime[1]),
                Integer.parseInt(attendanceDateTime[2]),
                Integer.parseInt(attendanceDateTime[3]),
                Integer.parseInt(attendanceDateTime[4]));
        attendanceBook.addCrewAttendanceByName(crewName, localDateTime);
    }
}
