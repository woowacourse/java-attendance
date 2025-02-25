package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Attendance {
    private static final String TIME_FORMAT = "HH:mm";

    private final Crew crew;
    private LocalDateTime presentTime;
    private AttendanceType attendanceType;

    public Attendance(Crew crew, LocalDateTime presentTime, AttendanceType attendanceType) {
        this.crew = crew;
        this.presentTime = presentTime;
        this.attendanceType = attendanceType;
    }

    public boolean isSameTime(final LocalDateTime localDateTime) {
        return this.presentTime.equals(localDateTime);
    }

    public boolean isSameCrew(final Crew crew) {
        return this.crew.equals(crew);
    }

    public boolean isSameCrewDate(final Crew crew, final LocalDate localDate) {
        return this.crew.equals(crew) && this.presentTime.toLocalDate().equals(localDate);
    }

    public void modifyLocalDateTime(final LocalDateTime changedPresentTime) {
        this.presentTime = changedPresentTime;
        modifyAttendanceType(changedPresentTime);
    }

    private void modifyAttendanceType(final LocalDateTime localDateTime) {
        attendanceType = AttendanceType.of(localDateTime);
    }

    public LocalDate getDate() {
        return presentTime.toLocalDate();
    }

    public String getTimeValue() {
        return presentTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    public AttendanceType getType() {
        return attendanceType;
    }

    public void updateCrewAttendanceCount(final AttendanceCount attendanceCount) {
        if (attendanceType.equals(AttendanceType.SAFE)) {
            attendanceCount.incrementSafeCount();
            return;
        }
        if (attendanceType.equals(AttendanceType.LATE)) {
            attendanceCount.incrementLateCount();
            return;
        }
        if (attendanceType.equals(AttendanceType.ABSENT)) {
            attendanceCount.incrementAbsentCount();
        }
    }
}
