package domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceFactory {
    
    private static final Map<String, List<Attendance>> attendances = new HashMap<>();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    public static AttendanceBook createAttendanceBook() throws IOException {
        try (
                FileReader fileReader = new FileReader("./src/main/resources/attendances.csv");
                BufferedReader reader = new BufferedReader(fileReader)
        ) {
            String str = reader.readLine();
            while ((str = reader.readLine()) != null) {
                insertAttendance(str);
            }
            return buildAttendanceBook();
        }
    }
    
    private static void insertAttendance(String str) {
        String name = str.split(",")[0];
        LocalDateTime dateTime = LocalDateTime.parse(str.split(",")[1], DATE_TIME_FORMATTER);
        
        if (!LocalDate.now().getMonth().equals(dateTime.getMonth())) {
            return;
        }
        
        Attendance attendance = new Attendance(dateTime);
        
        if (!attendances.containsKey(name)) {
            attendances.put(name, new ArrayList<>());
        }
        attendances.get(name).add(attendance);
    }
    
    private static AttendanceBook buildAttendanceBook() {
        final Map<CrewName, MemberAttendances> map = new HashMap<>();
        for (Map.Entry<String, List<Attendance>> entry : attendances.entrySet()) {
            map.put(new CrewName(entry.getKey()), new MemberAttendances(entry.getKey(), entry.getValue()));
        }
        return new AttendanceBook(map);
    }
}
