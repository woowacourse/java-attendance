package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {

    private final Crew crew;
    private LocalDateTime checkInTime;
    private AttendanceType attendanceType;
    private final boolean isCome;

    private Attendance(Crew crew, LocalDateTime checkInTime, AttendanceType attendanceType) {
        this.crew = crew;
        this.checkInTime = checkInTime;
        this.attendanceType = attendanceType;
        this.isCome = true;
    }

    private Attendance(Crew crew, LocalDate date) {
        this.crew = crew;
        this.checkInTime = LocalDateTime.of(date, LocalTime.of(0, 0));
        this.attendanceType = AttendanceType.ABSENCE;
        this.isCome = false;
    }

    public static Attendance of(Crew crew, LocalDateTime checkInTime) {
        validateHolidayAndWeekend(checkInTime.toLocalDate());
        validateOperationTime(checkInTime);

        return new Attendance(crew, checkInTime, AttendanceType.calculateType(checkInTime));
    }

    public static Attendance createTimeNullAbsence(Crew crew, LocalDate date) {
        validateHolidayAndWeekend(date);
        return new Attendance(crew, date);
    }

    public Attendance clone(Attendance attendance) {
        return Attendance.of(attendance.crew, attendance.checkInTime);
    }

    public boolean isSameDateAndCrew(Attendance attendance) {
        return checkInTime.toLocalDate().isEqual(attendance.checkInTime.toLocalDate())
                && crew.isEqualName(attendance.crew.getNickname());
    }

    public void modify(LocalTime modifiedTime) {
        LocalDateTime modifiedCheckInTime = LocalDateTime.of(checkInTime.toLocalDate(), modifiedTime);
        validateOperationTime(modifiedCheckInTime);

        checkInTime = modifiedCheckInTime;
        attendanceType = AttendanceType.calculateType(checkInTime);
    }

    public boolean isSame(Crew crew, LocalDate localDate) {
        if (this.crew.equals(crew) && checkInTime.toLocalDate().equals(localDate)) {
            return true;
        }
        return false;
    }

    public boolean findByCrewAndMonth(Crew crew, int month) {
        return this.crew.equals(crew) && checkInTime.getMonthValue() == month;
    }

    private static void validateHolidayAndWeekend(LocalDate date) {
        if (Holiday.isHolidayOrWeekend(date)) {
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

    public boolean isCome() {
        return isCome;
    }
}
