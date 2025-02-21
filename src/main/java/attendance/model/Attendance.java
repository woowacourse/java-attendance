package attendance.model;

import attendance.util.DateUtils;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

public class Attendance {

    private final Crew crew;
    private final LocalDateTime attendanceDateTime;

    public Attendance(Crew crew, LocalDateTime attendanceDateTime) {
        validateDateTime(attendanceDateTime);
        this.crew = crew;
        this.attendanceDateTime = attendanceDateTime;
    }

    private void validateDateTime(LocalDateTime attendanceDateTime) {
        if (DateUtils.isWeekend(attendanceDateTime.getDayOfWeek())) {
            throw new IllegalArgumentException("주말인 경우 출석할 수 없습니다.");
        }
        if (Holiday.isHoliday(attendanceDateTime.toLocalDate())) {
            throw new IllegalArgumentException("법정 공휴일에는 출석할 수 없습니다.");
        }
    }

    public boolean isCrewAttendanceInMonth(Crew crew, Month findMonth) {
        return this.crew.equals(crew) && attendanceDateTime.getMonth() == findMonth;
    }

    public LocalDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance that = (Attendance) o;
        return Objects.equals(crew, that.crew) && Objects.equals(attendanceDateTime.toLocalDate(),
                that.attendanceDateTime.toLocalDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(crew, attendanceDateTime.toLocalDate());
    }
}
