package service;

import domain.crew.Crew;
import repository.AttendanceRepository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import view.format.CustomDateTimeFormatter;

public class AttendanceStoreService {
    private final AttendanceRepository attendanceRepository;

    public AttendanceStoreService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public void save(String attendanceStorePath) {
        List<String> lines = loadLines(attendanceStorePath);
        for (String line : lines) {
            String[] parsed = line.split(",");
            Crew crew = new Crew(parsed[0]);
            LocalDateTime attendanceTime = CustomDateTimeFormatter.parseFullDateAndTime(parsed[1]);
            createCrewIfNew(crew);
            attendanceRepository.createNewAttendance(parsed[0]
                    , attendanceTime.getDayOfMonth()
                    , attendanceTime.getHour()
                    , attendanceTime.getMinute()
            );
        }
    }

    private List<String> loadLines(String file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            return readLine(reader);
        } catch (IOException e) {
            throw new RuntimeException("파일 로드 중에 오류가 발생했습니다.");
        }
    }

    private void createCrewIfNew(Crew crew) {
        try {
            attendanceRepository.save(crew);
        } catch (RuntimeException ignored) {
        }
    }

    private List<String> readLine(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        List<String> lines = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }
}
