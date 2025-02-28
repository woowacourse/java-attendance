package util;

import static util.Constants.*;

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
    private static final String FILE_PATH = "src/main/java/attendances.csv";
    private static final String FILE_PATH_INVALID_ERROR = "[ERROR] 파일 경로가 유효하지 않습니다.";
    private static final String PARSE_UNIT = " ";
    private static final int NAME_INDEX = 0;
    private static final int DATE_INDEX = 1;
    private static final int TIME_INDEX = 2;

    private final Map<CrewName, AttendanceRecord> initialInformation;

    public FileReader() {
        this.initialInformation = new HashMap<>();
    }

    public InitialInformation readAttendanceInfo() {
        Scanner scanner = initializeFileReader();
        while (scanner.hasNextLine()) {
            String[] nameAndDateAndTime = scanner.nextLine().split(PARSE_UNIT);
            CrewName crewName  = new CrewName(nameAndDateAndTime[NAME_INDEX]);
            LocalDate date = LocalDate.parse(nameAndDateAndTime[DATE_INDEX], DateTimeFormatter.ofPattern(DATE_FORMAT));
            LocalTime time = LocalTime.parse(nameAndDateAndTime[TIME_INDEX], DateTimeFormatter.ofPattern(TIME_FORMAT));
            enterAttendance(crewName, date, time);
        }
        return new InitialInformation(initialInformation);
    }

    private Scanner initializeFileReader() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(FILE_PATH));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(FILE_PATH_INVALID_ERROR);
        }
        return scanner;
    }

    private void enterAttendance(CrewName crewName, LocalDate date, LocalTime time) {
        Attendance attendance = new Attendance(date, time);
        AttendanceRecord existedAttendanceRecord = initialInformation.get(crewName);
        if(existedAttendanceRecord == null) {
            initialInformation.put(crewName, initialAttendanceRecord(attendance));
            return;
        }
        existedAttendanceRecord.add(attendance);
    }

    private AttendanceRecord initialAttendanceRecord(Attendance attendance) {
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.add(attendance);
        return attendanceRecord;
    }
}
