package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import service.DayComparator;

public class Crew {
    private final AttendanceCount attendanceCount = new AttendanceCount();
    private final List<Attandance> attendances = new ArrayList<>();

    public void addAttendance(LocalDateTime date) {
        attendances.add(new Attandance(date));
    }

    public void addAbsent(LocalDateTime today) {
        int dayOfMonth = today.getDayOfMonth();
        List<Integer> attendanceDays = attendances.stream().map(Attandance::getDay).toList();
        List<Integer> weekDays = new ArrayList<>();
        for(int day = 1 ; day < dayOfMonth ; day++) {
            if(DayComparator.isHoliday(day, today)) {
                continue;
            }
            weekDays.add(day);
        }
        weekDays.removeAll(attendanceDays);

        for(int day : weekDays) {
            attendances.add(new Attandance(LocalDateTime.of(today.getYear(), today.getMonth(), day, 23, 59)));
        }
    }
}
