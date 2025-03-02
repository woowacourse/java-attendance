package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import attendance.exception.AttendanceArgumentException;

public record Attendance(LocalDateTime dateTime, AttendanceStatus status) {
    private static final String OUT_OF_SCHEDULE = "캠퍼스 운영시간 외에 출석할 수 없습니다.";

    public Attendance(LocalDateTime dateTime) {
        this(validateDateTime(dateTime), judgeStatus(dateTime));
    }

    public static Attendance generateTruancy(LocalDate date) {
        LocalDateTime midnight = date.atStartOfDay();
        return new Attendance(midnight, AttendanceStatus.TRUANCY);
    }

    private static LocalDateTime validateDateTime(LocalDateTime dateTime) {
        if (Schedule.isDuringCampus(dateTime.toLocalTime())) {
            throw new AttendanceArgumentException(OUT_OF_SCHEDULE);
        }
        return dateTime;
    }

    private static AttendanceStatus judgeStatus(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();
        LocalTime schedule = Schedule.getSchedule(date);
        return AttendanceStatus.judgeStatus(time, schedule);
    }

    public String getConvertedStatus() {
        return status.convertMessage();
    }

    public boolean isTruancy() {
        return status.equals(AttendanceStatus.TRUANCY);
    }
}
