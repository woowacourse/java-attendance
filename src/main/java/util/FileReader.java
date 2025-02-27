package util;

import domain.Attendance;
import domain.AttendanceRecord;
import domain.CrewName;
import dto.InitialInformation;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileReader {
    public static InitialInformation readAttendanceInfo() {
        Map<CrewName, AttendanceRecord> initialInformation = new HashMap<>();
        Scanner scanner;
        try {
            scanner = new Scanner(new File("src/main/java/attendances.csv"));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("[ERROR] 파일 경로가 유효하지 않습니다.");
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] nameAndDateAndTime = line.split(" ");
            CrewName crewName  = new CrewName(nameAndDateAndTime[0]);
            LocalDate date = LocalDate.parse(nameAndDateAndTime[1], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalTime time = LocalTime.parse(nameAndDateAndTime[2], DateTimeFormatter.ofPattern("HH:mm"));
            Attendance attendance = new Attendance(date, time);

            AttendanceRecord existedAttendanceRecord = initialInformation.get(crewName);

            if(existedAttendanceRecord == null) {
                AttendanceRecord attendanceRecord = new AttendanceRecord();
                attendanceRecord.add(attendance);
                initialInformation.put(crewName, attendanceRecord);
                continue;
            }
            existedAttendanceRecord.add(attendance);
        }
        return new InitialInformation(initialInformation);
    }
}
