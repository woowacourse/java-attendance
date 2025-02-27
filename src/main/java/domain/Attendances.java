package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = new ArrayList<>(attendances);
    }

    public Attendance findAttendanceByDate(AttendanceDate date) {
        return this.attendances.stream()
                .filter(attendance -> attendance.isSameDate(date))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜에 대한 출석 기록이 존재하지 않습니다."));
    }

    public CrewStatus findCrewStatue(LocalDate nowDate) {
        return CrewStatus.checkCrewStatus(this.countLate(), this.countUnattended(nowDate));
    }

    public boolean checkAlreadyAttend(AttendanceDate date) {
        return this.attendances.stream()
                .filter(attendance -> attendance.isSameDate(date))
                .count() != 0;
    }

    public int countAttendance() {
        return (int) this.attendances.stream()
                .filter(Attendance::isAttended)
                .count();
    }

    public int countLate() {
        return (int) this.attendances.stream()
                .filter(Attendance::isLate)
                .count();
    }

    public int countUnattended(LocalDate nowDate) {
        int absentCount = (int) this.attendances.stream()
                .filter(Attendance::isUnattendedOrNoShow)
                .count();

        absentCount += (AttendanceDate.findPastEducationDates(nowDate).size() - this.attendances.size());
        return absentCount;
    }

    public void addNewAttendance(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        this.attendances.add(new Attendance(attendanceDate, attendanceTime));
    }

    public void editAttendance(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        Attendance oldAttendance = findAttendanceByDate(attendanceDate);
        this.attendances.remove(oldAttendance);
        this.attendances.add(new Attendance(attendanceDate, attendanceTime));
    }
}
