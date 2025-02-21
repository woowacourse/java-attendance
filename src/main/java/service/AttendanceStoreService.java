package service;

import domain.AttendanceCustomDate;
import domain.Crew;
import repository.AttendanceRepository;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceStoreService {
    private final AttendanceRepository attendanceRepository;

    public AttendanceStoreService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public void save() {
        List<String> lines = loadLines("src/main/resources/attendances.csv");
        for (String line : lines) {
            String[] parsed = line.split(",");;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            Crew crew = new Crew(parsed[0]);
            LocalDateTime attendanceTime = LocalDateTime.parse(parsed[1], formatter);
            LocalDateTime now = AttendanceCustomDate.now();
            try {
                attendanceRepository.save(crew, now.getYear(), now.getMonthValue());
            } catch (RuntimeException ignored) {
            }
            attendanceRepository.createNewAttendance(parsed[0]
                    , attendanceTime.getDayOfMonth()
                    , attendanceTime.getHour()
                    , attendanceTime.getMinute()
            );
        }
    }

    private List<String> loadLines(String file) {
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine();
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            throw new RuntimeException("파일 로드 중에 오류가 발생했습니다.");
        }
    }
}
