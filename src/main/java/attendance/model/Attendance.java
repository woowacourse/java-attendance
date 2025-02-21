package attendance.model;

import attendance.util.DateUtils;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

public class Attendance {

    private final Crew crew;
    private final LocalDateTime attendanceDateTime;

    public Attendance(Crew crew, LocalDateTime attendanceDateTime) {
        validateAttendantDateTime(attendanceDateTime);
        this.crew = crew;
        this.attendanceDateTime = attendanceDateTime;
    }

    public boolean isCrewAttendanceInMonth(Crew crew, Month findMonth) {
        return this.crew.equals(crew) && attendanceDateTime.getMonth() == findMonth;
    }

    public boolean isAlreadyAttendance(Attendance attendance) {
        return this.crew.equals(attendance.crew) &&
                this.attendanceDateTime.toLocalDate().equals(attendance.attendanceDateTime.toLocalDate());
    }

    public LocalDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    private void validateAttendantDateTime(LocalDateTime attendanceDateTime) {
        if (DateUtils.isWeekend(attendanceDateTime.getDayOfWeek())) {
            throw new IllegalArgumentException("주말인 경우 출석할 수 없습니다.");
        }
        if (Holiday.isHoliday(attendanceDateTime.toLocalDate())) {
            throw new IllegalArgumentException("법정 공휴일에는 출석할 수 없습니다.");
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Attendance that = (Attendance) object;
        return Objects.equals(crew, that.crew) && Objects.equals(getAttendanceDateTime(),
                that.getAttendanceDateTime());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(crew);
        result = 31 * result + Objects.hashCode(getAttendanceDateTime());
        return result;
    }
}
