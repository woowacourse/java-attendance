package attendance.model;

import attendance.util.DateUtils;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

public class Attendance {

    private final Crew crew;
    private final LocalDateTime attendanceTime;

    public Attendance(Crew crew, LocalDateTime attendanceTime) {
        validateDateTime(attendanceTime);
        this.crew = crew;
        this.attendanceTime = attendanceTime;
    }

    private void validateDateTime(LocalDateTime attendanceTime) {
        if (DateUtils.isWeekend(attendanceTime.getDayOfWeek())) {
            throw new IllegalArgumentException("주말인 경우 출석할 수 없습니다.");
        }
        if (Holiday.isHoliday(attendanceTime.toLocalDate())) {
            throw new IllegalArgumentException("법정 공휴일에는 출석할 수 없습니다.");
        }
    }

    public boolean isCrewAttendanceInMonth(Crew crew, Month findMonth) {
        return this.crew.equals(crew) && attendanceTime.getMonth() == findMonth;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance that = (Attendance) o;
        return Objects.equals(crew, that.crew) && Objects.equals(attendanceTime.toLocalDate(),
                that.attendanceTime.toLocalDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(crew, attendanceTime.toLocalDate());
    }
}
