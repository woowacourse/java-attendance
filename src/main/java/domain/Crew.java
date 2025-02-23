package domain;

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
        // 이미 해당 날짜를 체크했다면 true
        return attendances.getSpecificAttendance(today.getDayOfMonth()).isPresent();
    }

    public Attendance getSpecificAttendance(int day) {
        return attendances.getSpecificAttendance(day).get();
    }

    public Attendance changeAttendance(int changeDay, Time time) {
        return attendances.changeAttendance(changeDay, time);
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
