package domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileStoreManager {
    private final CrewAttendanceStorage storage;

    public FileStoreManager(CrewAttendanceStorage storage) {
        this.storage = storage;
    }
    public void save(String file) {
        List<String> lines = readLines(file);
        Set<String> crews = getCrews(lines);
        for (String crew : crews) {
            storage.create(crew);
        }
        registerAttendances(lines);
    }

    private List<String> readLines(String file) {
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


    private Set<String> getCrews(List<String> lines) {
        Set<String> crews = new HashSet<>();
        for (String line : lines) {
            String[] split = line.split(",");
            crews.add(split[0].trim());
        }
        return crews;
    }

    private void registerAttendances(List<String> lines) {
        for (String line : lines) {
            String[] split = line.split(",");
            String crew = split[0].trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime attendanceTime = LocalDateTime.parse(split[1], formatter);
            storage.register(crew, attendanceTime.toLocalDate(), attendanceTime.toLocalTime());
        }
    }
}
