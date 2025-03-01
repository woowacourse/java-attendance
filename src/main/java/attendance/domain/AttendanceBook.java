package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    private final Map<Crew, AttendanceRecord> attendanceBook;
    private static final int DEFAULT_VALUE = 0;

    public AttendanceBook(Crews crews, LocalDateTime now) {
        this.attendanceBook = new HashMap<>();
        initializeAttendanceBook(crews, now);
    }

    private void initializeAttendanceBook(Crews crews, LocalDateTime now) {
        for (Crew crew : crews.getCrews()) {
            attendanceBook.put(crew, putDefaultValue(now));
        }
    }

    private AttendanceRecord putDefaultValue(LocalDateTime now) {
        List<AttendanceTime> attendanceTimes = new ArrayList<>();
        for (int i = 1; i < now.getDayOfMonth(); i++) {
            LocalDateTime dateTime = now.withDayOfMonth(i).withHour(DEFAULT_VALUE).withMinute(DEFAULT_VALUE);
            excludeWeekend(dateTime, attendanceTimes);
        }
        return new AttendanceRecord(attendanceTimes);
    }

    private void excludeWeekend(LocalDateTime dateTime, List<AttendanceTime> attendanceTimes) {
        if (dateTime.getDayOfWeek() != DayOfWeek.SATURDAY && dateTime.getDayOfWeek() != DayOfWeek.SUNDAY) {
            attendanceTimes.add(new AttendanceTime(dateTime));
        }
    }

    public AttendanceStatus registerAttendance(String inputCrewName, LocalDateTime inputTime) {
        Crew crew = findRegisteredCrew(inputCrewName);
        AttendanceRecord attendanceRecord = attendanceBook.get(crew);
        return attendanceRecord.registerAttendance(inputTime);
    }

    private Crew findRegisteredCrew(String inputCrewName) {
        return attendanceBook.keySet()
                .stream()
                .filter(crew -> crew.getName().equals(inputCrewName))
                .findFirst()
                .orElseThrow();
    }

    public Map<Crew, AttendanceRecord> getAttendanceBook() {
        return attendanceBook;
    }

}
