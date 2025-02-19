package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class Attendance {
    private final Crew crew;
    private LocalDateTime presentTime;
    private AttendanceType attendanceType;

    public Attendance(Crew crew, LocalDateTime presentTime, AttendanceType attendanceType) {
        this.crew = crew;
        this.presentTime = presentTime;
        this.attendanceType = attendanceType;
    }

    public boolean isSameCrewDate(final Crew crew, final LocalDate localDate) {
        return this.crew.equals(crew) && this.presentTime.toLocalDate().equals(localDate);
    }

    public List<String> getInfo() {
        return List.of(
                String.valueOf(presentTime.getMonthValue()),
                String.valueOf(presentTime.getDayOfMonth()),
                presentTime.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN),
                presentTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                this.attendanceType.toString()
        );
    }

    public boolean isSameCrew(Crew crew) {
        return this.crew.equals(crew);
    }

    public void modifyLocalDateTime(LocalDateTime changedPresentTime) {
        this.presentTime = changedPresentTime;
    }


    public LocalDate getDate() {
        return presentTime.toLocalDate();
    }

    public String getTimeValue() {
        return presentTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public AttendanceType getType() {
        return attendanceType;
    }

    public void modifyAttendanceType(LocalDateTime localDateTime) {
        attendanceType = AttendanceType.of(localDateTime);
    }

}
