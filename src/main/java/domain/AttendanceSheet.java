package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceSheet {
    List<Attendance> attendances;

    public AttendanceSheet(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void add(String nickname, LocalDate date, LocalTime time) {
        this.attendances.add(new Attendance(nickname, date, time));
    }
}
