package util;

import domain.Attendance;
import domain.AttendanceBook;
import domain.Crew;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceFileReader {

    public static Map<Crew, AttendanceBook> read(String ATTENDANCE_FILE_PATH) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(ATTENDANCE_FILE_PATH));
            br.readLine();

            String line;
            Map<Crew, AttendanceBook> attendanceBookMap = new HashMap<>();

            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");

                String name = split[0];
                String date = split[1];

                Crew crew = new Crew(name);
                AttendanceBook attendanceBook = attendanceBookMap.getOrDefault(crew,
                        new AttendanceBook(new ArrayList<>()));

                String[] parts = date.split(" ");
                LocalDate localDate = LocalDate.parse(parts[0]);
                LocalTime localTime = LocalTime.parse(parts[1]);

                Attendance attendance = new Attendance(localDate, localTime);

                List<Attendance> attendances = attendanceBook.getAttendanceBook();
                attendances.add(attendance);
                attendanceBookMap.put(crew, attendanceBook);
            }
            return attendanceBookMap;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
