package service;

import domain.AttendanceRecord;
import domain.AttendanceStatus;
import domain.Crew;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import repository.AttendanceRecordRepository;
import repository.CrewRepository;

public class AttendanceRecordLoader {

    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String ATTENDANCE_RECORDS_FILE_PATH = "src/main/resources/attendances.csv";

    private AttendanceRecordLoader() {
    }

    public static void loadAttendanceRecordsFromFile() {
        File file = new File(ATTENDANCE_RECORDS_FILE_PATH);
        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr);
        ) {
            br.lines().skip(1)
                    .forEach(AttendanceRecordLoader::addToRepository);
        } catch (IOException e) {
            System.out.println("기존 출석 기록을 읽어오지 못했습니다.");
        }
    }

    private static void addToRepository(String line) {
        String[] parsed = line.split(",", -1);
        String nickname = parsed[0];
        String dateTime = parsed[1];
        LocalDateTime d = LocalDateTime.parse(dateTime, DATETIME_FORMAT);
        CrewRepository.addCrew(new Crew(nickname));
        AttendanceRecordRepository.add(new AttendanceRecord(nickname, d.toLocalDate(), d.toLocalTime(),
                AttendanceStatus.of(d.toLocalDate(), d.toLocalTime())));
    }
}
