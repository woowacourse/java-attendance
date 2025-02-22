package attendance.model;

import attendance.util.DateUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Objects;

public class Attendance {

    private final Crew crew;
    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;

    public Attendance(Crew crew, LocalDateTime attendanceDateTime) {
        this(crew, attendanceDateTime.toLocalDate(), attendanceDateTime.toLocalTime());
    }

    public Attendance(Crew crew, LocalDate attendanceDate, LocalTime attendanceTime) {
        validateAttendantDateTime(attendanceDate);
        this.crew = crew;
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public static Attendance absent(Crew crew, LocalDate attendanceDate) {
        return new Attendance(crew, attendanceDate, null);
    }

    public boolean isCrewAttendanceInMonth(Crew crew, Month findMonth) {
        return this.crew.equals(crew) && attendanceDate.getMonth() == findMonth;
    }

    public boolean isAlreadyAttendance(Attendance attendance) {
        return this.crew.equals(attendance.crew) &&
                this.attendanceDate.equals(attendance.attendanceDate);
    }

    public boolean isAlreadyAttendance(Crew crew, LocalDate attendanceDate) {
        return this.crew.equals(crew) &&
                this.attendanceDate.equals(attendanceDate);
    }

    public AttendanceType getAttendanceType() {
        return AttendanceType.judge(AttendanceStartTime.findDayOfWeek(attendanceDate.getDayOfWeek()), attendanceTime);
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    public LocalDateTime getAttendanceDateTime() {
        return LocalDateTime.of(attendanceDate, attendanceTime);
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

    private void validateAttendantDateTime(LocalDate attendanceDate) {
        if (DateUtils.isWeekend(attendanceDate.getDayOfWeek())) {
            throw new IllegalArgumentException("주말인 경우 출석할 수 없습니다.");
        }
        if (Holiday.isHoliday(attendanceDate)) {
            throw new IllegalArgumentException("법정 공휴일에는 출석할 수 없습니다.");
        }
    }
}
