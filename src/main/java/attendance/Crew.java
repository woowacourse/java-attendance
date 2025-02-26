package attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Crew {

    private String nickname;
    private List<Attendance> attendances;

    public Crew(String nickname) {
        this.nickname = nickname;
        this.attendances = new ArrayList<>();
    }

    public Attendance addAttendance(LocalDateTime attendanceDateTime) {
        Attendance attendance = new Attendance(attendanceDateTime);
        attendances.add(attendance);
        return attendance;
    }

    public boolean isEqualCrew(String nickname) {
        return this.nickname.equals(nickname);
    }

    public Attendance updateAttendance(LocalDate updateDate, LocalTime updateTime) {
        Attendance beforeAttendance = findAttendanceByDate(updateDate);
        attendances.remove(beforeAttendance);
        Attendance updatedAttendance = beforeAttendance.updateAttendanceTime(updateTime);
        attendances.add(updatedAttendance);
        return updatedAttendance;
    }


    public Attendance findAttendanceByDate(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isEqualDate(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 날짜의 출석이 없습니다."));
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }
}
