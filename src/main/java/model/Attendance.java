package model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {

    private final Crew crew;
    private LocalDateTime checkInTime;
    private AttendanceType attendanceType;

    private Attendance(Crew crew, LocalDateTime checkInTime, AttendanceType attendanceType) {
        this.crew = crew;
        this.checkInTime = checkInTime;
        this.attendanceType = attendanceType;
    }

    public static Attendance of(Crew crew, LocalDateTime checkInTime) {
        validateHolidayAndWeekend(checkInTime);
        validateOperationTime(checkInTime);

        return new Attendance(crew, checkInTime, AttendanceType.calculateType(checkInTime));
    }

    public boolean isSameDateAndCrew(Attendance attendance) {
        return checkInTime.toLocalDate().equals(attendance.checkInTime.toLocalDate()) && attendance.crew.isEqualName(
                crew.getNickname());
    }

    public void modify(LocalTime modifiedCheckInTime) {
        checkInTime = LocalDateTime.of(checkInTime.toLocalDate(), modifiedCheckInTime);
        attendanceType = AttendanceType.calculateType(checkInTime);
    }

    private static void validateHolidayAndWeekend(LocalDateTime checkInTime) {
        if (Holiday.isHolidayOrWeekend(checkInTime)) {
            throw new IllegalArgumentException("주말 및 공휴일에는 출석할 수 없습니다.");
        }
    }

    private static void validateOperationTime(LocalDateTime checkInTime) {
        if (AttendanceTime.isNotInOperation(checkInTime)) {
            throw new IllegalArgumentException("지금은 운영 시간이 아닙니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance that = (Attendance) o;
        return Objects.equals(crew, that.crew) && Objects.equals(checkInTime, that.checkInTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crew, checkInTime);
    }

    public Crew getCrew() {
        return crew;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public AttendanceType getAttendanceType() {
        return attendanceType;
    }
}
