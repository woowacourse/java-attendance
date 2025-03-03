package domain;

import constant.Holiday;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import util.DateFormatter;

public class AttendanceHistory {
    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;
    private final AttendanceResult attendanceResult;

    public AttendanceHistory(LocalDateTime attendanceTime) {
        AttendanceValidator.validateAttendanceTime(attendanceTime.toLocalTime());
        AttendanceValidator.validateAttendanceDate(attendanceTime.toLocalDate());
        this.attendanceDate = attendanceTime.toLocalDate();
        this.attendanceTime = attendanceTime.toLocalTime();
        this.attendanceResult = findAttendanceResult(this.attendanceDate, this.attendanceTime);
    }

    public AttendanceHistory(LocalDate attendanceDate, LocalTime attendanceTime) {
        AttendanceValidator.validateAttendanceTime(attendanceTime);
        AttendanceValidator.validateAttendanceDate(attendanceDate);
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
        this.attendanceResult = findAttendanceResult(this.attendanceDate, this.attendanceTime);
    }

    private AttendanceResult findAttendanceResult(LocalDate attendanceDate, LocalTime attendanceTime) {
        return AttendanceResult.findAttendanceResult(attendanceDate, attendanceTime);
    }

    public boolean isSameDate(LocalDate standardDate) {
        return attendanceDate.isEqual(standardDate);
    }

    public boolean isBeforeAttendanceHistory(LocalDate standard) {
        return attendanceDate.isBefore(standard);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceHistory that = (AttendanceHistory) o;
        return Objects.equals(attendanceDate, that.attendanceDate) && Objects.equals(attendanceTime,
                that.attendanceTime) && Objects.equals(attendanceResult, that.attendanceResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceDate, attendanceTime, attendanceResult);
    }

    public AttendanceResult getAttendanceResult() {
        return attendanceResult;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public Optional<LocalTime> getAttendanceTime() {
        return Optional.ofNullable(attendanceTime);
    }

    public static class AttendanceValidator {
        private static void validateAttendanceTime(LocalTime attendanceTime) {
            if (attendanceTime != null && CampusOpenTime.canNotAttendance(attendanceTime)) {
                throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간은 08:00 ~ 23:00 입니다.");
            }
        }

        public static void validateAttendanceDate(LocalDate localDate) {
            if (Holiday.isHoliday(localDate)) {
                String dateFormat = DateFormatter.dateFormat(localDate);
                String errorMessage = String.format("[ERROR] %S은 등교일이 아닙니다.", dateFormat);
                throw new IllegalArgumentException(errorMessage);
            }
        }
    }
}
