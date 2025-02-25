package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = new ArrayList<>(attendances);
    }

    public Attendance findAttendanceByDate(LocalDate date) {
        return this.attendances.stream()
                .filter(attendance -> attendance.isSameDate(date))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜에 대한 출석 기록이 존재하지 않습니다."));
    }

    public Attendance findAttendanceByDate(AttendanceDate date) {
        return this.attendances.stream()
                .filter(attendance -> attendance.isSameDate(date))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜에 대한 출석 기록이 존재하지 않습니다."));
    }

    public CrewStatus getCrewStatue() {
        return CrewStatus.checkCrewStatus(this.countLate(), this.countUnattended());
    }

    public boolean checkAlreadyAttend(AttendanceDate date) {
        return this.attendances.stream()
                .filter(attendance -> attendance.isSameDate(date))
                .count() != 0;
    }

    private int countLate() {
        return (int) attendances.stream()
                .filter(Attendance::isLate)
                .count();
    }

    private int countUnattended() {
        return (int) attendances.stream()
                .filter(Attendance::isUnattendedOrNoShow)
                .count();
    }

    public void addNewAttendance(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        this.attendances.add(new Attendance(attendanceDate, attendanceTime));
    }

    public void editAttendance(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        Attendance oldAttendance = findAttendanceByDate(attendanceDate);
        oldAttendance.editTime(attendanceTime);
    }
}
