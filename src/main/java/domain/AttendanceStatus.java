package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTENDANCE("출석", LocalTime.of(10, 5), LocalTime.of(13, 5)),
    LATENESS("지각", LocalTime.of(10, 30), LocalTime.of(13, 30)),
    ABSENCE("결석", LocalTime.MAX, LocalTime.MAX);

    private final String description;
    private final LocalTime morningSessionCutoff;
    private final LocalTime afternoonSessionCutoff;

    AttendanceStatus(String description, LocalTime morningSessionCutoff, LocalTime afternoonSessionCutoff) {
        this.description = description;
        this.morningSessionCutoff = morningSessionCutoff;
        this.afternoonSessionCutoff = afternoonSessionCutoff;
    }

    public static AttendanceStatus from(LocalDate date, LocalTime time) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        LocalTime attendanceCutoff = ATTENDANCE.morningSessionCutoff;
        LocalTime latenessCutoff = LATENESS.morningSessionCutoff;

        if (dayOfWeek == DayOfWeek.MONDAY) {
            attendanceCutoff = ATTENDANCE.afternoonSessionCutoff;
            latenessCutoff = LATENESS.afternoonSessionCutoff;
        }

        return determineStatus(time, attendanceCutoff, latenessCutoff);
    }

    private static AttendanceStatus determineStatus(LocalTime time, LocalTime attendanceCutoff,
                                                    LocalTime latenessCutoff) {
        if (time.isAfter(latenessCutoff)) {
            return ABSENCE;
        }
        if (time.isAfter(attendanceCutoff)) {
            return LATENESS;
        }
        return ATTENDANCE;
    }

    public String getDescription() {
        return description;
    }
}
