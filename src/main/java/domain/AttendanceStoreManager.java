package domain;

import exception.CrewNotExistException;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceStoreManager {
    private final CrewAttendances crewAttendances;

    public AttendanceStoreManager(CrewAttendances crewAttendances) {
        this.crewAttendances = crewAttendances;
    }

    public void save(String file) {
        List<String> lines = loadLines(file);
        for (String line : lines) {
            String[] parsed = line.split(",");;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            try {
                crewAttendances.findCrewByName(parsed[0]);
            } catch (CrewNotExistException e) {
                crewAttendances.registerCrew(new Crew(parsed[0]));
            }
            LocalDateTime attendanceTime = LocalDateTime.parse(parsed[1], formatter);
            crewAttendances.createNewAttendance(
                    parsed[0],
                    attendanceTime.toLocalDate(),
                    attendanceTime.toLocalTime()
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
