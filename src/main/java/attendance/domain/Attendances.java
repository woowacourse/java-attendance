package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static attendance.domain.exception.AttendancesExceptionMessage.ALREADY_EXIST_ATTENDANCE;

public class Attendances {
    private static final int FIRST_DAY = 1;

    private final List<Attendance> attendances;

    public Attendances() {
        this.attendances = new ArrayList<>();
    }

    public Attendance add(final Attendance attendanceInput) {
        for (Attendance attendance : attendances) {
            if(attendance.isEqualAttendanceDate(attendanceInput)) {
                throw new IllegalArgumentException(ALREADY_EXIST_ATTENDANCE);
            }
        }
        attendances.add(attendanceInput);
        return attendanceInput;
    }

    public void remove(final Attendance beforeAttendance) {
        attendances.removeIf(attendance -> attendance.isEqualAttendanceDate(beforeAttendance));
    }

    public void fillAbsentAttendances(LocalDate today) {
        for(LocalDate date = LocalDate.of(today.getYear(), today.getMonth(), FIRST_DAY); date.isBefore(today); date = date.plusDays(FIRST_DAY)) {
            if(!isExistAttendanceByDate(date) && !Holiday.checkHoliday(date.atStartOfDay())) {
                add(new Attendance(LocalDateTime.of(date, LocalTime.MIN)));
            }
        }
    }

    public long countAttendanceStatus(final AttendanceStatus status) {
        return attendances.stream()
                .filter(attendance -> attendance.isEqualStatus(status))
                .count();
    }

    public List<Attendance> getAttendances() {
        return Collections.unmodifiableList(attendances);
    }

    private boolean isExistAttendanceByDate(final LocalDate today) {
        for (Attendance attendance : attendances) {
            if(attendance.isEqualDate(today)){
                return true;
            }
        }
        return false;
    }
}
