package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Crew {
    private final String nickname;
    private final Attendances attendances = new Attendances();

    public Crew(String nickname) {
        this.nickname = nickname;
    }

    public Attendance addAttendance(LocalDateTime date) {
        Attendance attendance = new Attendance(date);
        attendances.addAttendance(attendance);
        return attendance;
    }

    public AttendanceAlertLevel getAttendanceAlertLevel() {
        return attendances.calculateAttendanceAlertLevel();
    }

    public boolean isAlreadyChecked(LocalDateTime today) {
        return attendances.getSpecificAttendance(today.toLocalDate()).isPresent();
    }

    public Attendance getSpecificAttendance(LocalDate specificDate) {
        return attendances.getSpecificAttendance(specificDate).get();
    }

    public Attendance changeAttendance(LocalDate changeDate, Time time) {
        return attendances.changeAttendance(changeDate, time);
    }

    public String getNickname() {
        return nickname;
    }

    public void addAbsent(LocalDateTime today) {
        attendances.addAbsent(today);
    }

    public Attendances getAttendances() {
        return attendances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crew crew = (Crew) o;
        return Objects.equals(nickname, crew.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickname);
    }


}
