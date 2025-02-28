package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances() {
        this.attendances = new ArrayList<>();
    }

    public Attendance add(final Attendance attendanceInput) {
        for (Attendance attendance : attendances) {
            if(attendance.isEqualAttendanceDate(attendanceInput)) {
                throw new IllegalArgumentException("해당 날짜에 이미 출석이 존재합니다. 출석 수정 기능을 이용해주세요.");
            }
        }
        attendances.add(attendanceInput);
        return attendanceInput;
    }

    public void remove(final Attendance beforeAttendance) {
        attendances.removeIf(attendance -> attendance.isEqualAttendanceDate(beforeAttendance));
    }

    public void fillAbsentAttendances(LocalDate today) {
        for(LocalDate date = LocalDate.of(today.getYear(), today.getMonth(), 1); date.isBefore(today); date = date.plusDays(1)) {
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
