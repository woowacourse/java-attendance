package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import attendance.exception.AttendanceArgumentException;

public record Attendance(LocalDateTime dateTime, AttendanceStatus status) {
    private static final LocalTime CAMPUS_OPEN = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_CLOSE = LocalTime.of(23, 0);
    private static final LocalTime LESSON_MONDAY = LocalTime.of(13, 0);
    private static final LocalTime LESSON_DEFAULT_DAY = LocalTime.of(10, 0);

    private static final String OUT_OF_SCHEDULE = "캠퍼스 운영시간 외에 출석할 수 없습니다.";

    public Attendance(LocalDateTime dateTime) {
        this(validateDateTime(dateTime), judgeStatus(dateTime));
    }

    public static Attendance generateTruancy(LocalDate date) {
        LocalDateTime midnight = date.atStartOfDay();
        return new Attendance(midnight, AttendanceStatus.TRUANCY);
    }

    private static LocalDateTime validateDateTime(LocalDateTime dateTime) {
        if (isDuringCampus(dateTime.toLocalTime())) {
            throw new AttendanceArgumentException(OUT_OF_SCHEDULE);
        }
        return dateTime;
    }

    private static AttendanceStatus judgeStatus(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();
        LocalTime schedule = getSchedule(date);
        return AttendanceStatus.judgeStatus(time, schedule);
    }

    private static boolean isDuringCampus(LocalTime time) {
        return time.isBefore(CAMPUS_OPEN) || time.isAfter(CAMPUS_CLOSE);
    }

    private static LocalTime getSchedule(LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
            return LESSON_MONDAY;
        }
        return LESSON_DEFAULT_DAY;
    }

    public String getConvertedStatus() {
        return status.convert();
    }

    public boolean isTruancy() {
        return status.equals(AttendanceStatus.TRUANCY);
    }
}
