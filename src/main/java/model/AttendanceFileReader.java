package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceFileReader {

    private static final String ATTENDANCE_FILE_PATH = "src/main/resources/attendance.csv";

    public AttendanceBook read() {
        try {
            BufferedReader csv = new BufferedReader(new FileReader(ATTENDANCE_FILE_PATH));
            Map<String, List<String>> collect = csv.lines()
                    .skip(1)
                    .collect(Collectors.groupingBy(
                            line -> line.split(",")[0],
                            Collectors.mapping(line -> line.split(",")[1], Collectors.toList())
                    ));

            List<CrewAttendances> crews = new ArrayList<>();
            for (String nickname : collect.keySet()) {
                CrewAttendances crew = new CrewAttendances(nickname, new ArrayList<>());
                for (String datetime : collect.get(nickname)) {
                    LocalDate date = LocalDate.parse(datetime.split(" ")[0]);
                    LocalTime time = LocalTime.parse(datetime.split(" ")[1]);

                    crew.addAttendance(new Attendance(date, time));
                }

                crews.add(crew);
            }

            return new AttendanceBook(crews);
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 파일을 읽는데 실패했습니다.");
        }
    }
}
