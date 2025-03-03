package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceLog {
    private final List<Attendance> attendanceLog;

    public AttendanceLog(List<Attendance> attendanceLog) {
        this.attendanceLog = new ArrayList<>(attendanceLog);
    }

    public Attendance registerAttendance(LocalDateTime attendanceDateTime) {
        Attendance attendance = new Attendance(attendanceDateTime);
        attendanceLog.stream()
            .filter(attendance1 -> attendance1.getAttendanceDate().equals(attendanceDateTime.toLocalDate()))
            .findFirst()
            .ifPresent(attendance1 -> {
                throw new IllegalArgumentException("이미 해당 날짜에 출석이 등록되어 있습니다. 수정 기능을 이용해주세요");
            });
        attendanceLog.add(attendance);
        return attendance;
    }

    public List<Attendance> modifyAttendanceRecord(LocalDateTime attendanceDateTime) {
        Attendance modifyOldAttendance = attendanceLog.stream()
            .filter((attendance) -> attendance.getAttendanceDate().equals(attendanceDateTime.toLocalDate()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 날입니다."));

        attendanceLog.remove(modifyOldAttendance);
        Attendance modifyNewAttendance = new Attendance(attendanceDateTime);
        attendanceLog.add(modifyNewAttendance);

        return List.of(modifyOldAttendance, modifyNewAttendance);
    }

    public List<Attendance> checkAttendancesRecord() {
        return attendanceLog.stream().filter(attendance -> attendance.getAttendanceDate().isBefore(LocalDate.now()))
            .toList();
    }

    public int countAttendanceStatus(Subject subject) {
        return Math.toIntExact(attendanceLog.stream()
            .filter(attendance -> attendance.getAttendanceStatus().equals(subject.getStatus())).count());
    }

    public List<Attendance> getAttendanceLog() {
        return attendanceLog;
    }
}
