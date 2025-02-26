package domain.attendance;

import domain.crew.CrewStatus;
import exception.ErrorException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceLogs {

    private final List<AttendanceLog> attendanceLogs;

    public AttendanceLogs() {
        this.attendanceLogs = new ArrayList<>();
    }

    public AttendanceLog registerLog(LocalDateTime attendDateTime) {
        if (isAttendanceLog(attendDateTime.toLocalDate())) {
            throw new ErrorException("출석 기록이 이미 등록되었습니다.");
        }
        AttendanceLog attendanceLog = new AttendanceLog(attendDateTime);
        this.attendanceLogs.add(attendanceLog);
        return attendanceLog;
    }

    public AttendanceLog editLog(LocalDate editDate, LocalTime editTime) {
        AttendanceLog oldAttendanceLog = attendanceLogs.stream()
                .filter(attendanceLog -> attendanceLog.isAttendDate(editDate))
                .findFirst()
                .orElseThrow(() -> new ErrorException("수정할 출석 기록이 없습니다."));
        attendanceLogs.remove(oldAttendanceLog);
        return registerLog(LocalDateTime.of(editDate, editTime));
    }

    public CrewStatus calculateCrewStatus() {
        int lateCount = calculateLateCount();
        int absentCount = calculateAbsentCount();
        return CrewStatus.findStatus(lateCount, absentCount);
    }

    private int calculateLateCount() {
        return (int) attendanceLogs.stream()
                .filter(attendanceLog -> attendanceLog.getAttendanceStatus() == AttendanceStatus.LATE)
                .count();
    }

    private int calculateAbsentCount() {
        return (int) attendanceLogs.stream()
                .filter(attendanceLog -> attendanceLog.getAttendanceStatus() == AttendanceStatus.ABSENT)
                .count();
    }

    private boolean isAttendanceLog(LocalDate attendDate) {
        return attendanceLogs.stream()
                .anyMatch(attendanceLog -> attendanceLog.isAttendDate(attendDate));
    }
}
