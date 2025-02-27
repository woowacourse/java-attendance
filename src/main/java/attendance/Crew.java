package attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Crew {

    private final String nickname;
    private final List<Attendance> attendances;

    public Crew(final String nickname) {
        this.nickname = nickname;
        this.attendances = new ArrayList<>();
    }

    public Attendance addAttendance(final LocalDateTime attendanceDateTime) {
        Attendance attendance = new Attendance(attendanceDateTime);
        attendances.add(attendance);
        return attendance;
    }

    public boolean isEqualCrew(final String nickname) {
        return this.nickname.equals(nickname);
    }

    public Attendance updateAttendance(final LocalDate updateDate, final LocalTime updateTime) {
        Attendance beforeAttendance = findAttendanceByDate(updateDate);
        attendances.remove(beforeAttendance);
        Attendance updatedAttendance = beforeAttendance.updateAttendanceTime(updateTime);
        attendances.add(updatedAttendance);
        return updatedAttendance;
    }


    public Attendance findAttendanceByDate(final LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isEqualDate(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 날짜의 출석이 없습니다."));
    }

    public long countAttendanceStatus(final AttendanceStatus status) {
        return attendances.stream()
                .filter(attendance -> attendance.isEqualStatus(status))
                .count();
    }

    public AbsenceRule checkAbsenceRule() {
        long totalExpulsionCount = countAttendanceStatus(AttendanceStatus.ABSENCE) + countAttendanceStatus(AttendanceStatus.LATE) / 3;
        return AbsenceRule.checkExpulsionCount(totalExpulsionCount);
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }
}
