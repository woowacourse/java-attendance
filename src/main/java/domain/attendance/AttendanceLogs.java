package domain.attendance;

import domain.crew.CrewStatus;
import exception.ErrorException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<AttendanceStatus, Integer> calculateLogsStatus() {
        Map<AttendanceStatus, Integer> attendanceLogsStatus = new HashMap<>();
        for (AttendanceStatus attendanceStatus : AttendanceStatus.values()) {
            attendanceLogsStatus.put(attendanceStatus, 0);
        }
        for (AttendanceLog attendanceLog : attendanceLogs) {
            AttendanceStatus attendanceStatus = attendanceLog.getAttendanceStatus();
            attendanceLogsStatus.put(attendanceStatus, attendanceLogsStatus.get(attendanceStatus) + 1);
        }
        return attendanceLogsStatus;
    }

    public CrewStatus calculateCrewStatus() {
        Map<AttendanceStatus, Integer> attendanceLogsStatus = calculateLogsStatus();
        return CrewStatus.findStatus(attendanceLogsStatus.get(AttendanceStatus.LATE),
                attendanceLogsStatus.get(AttendanceStatus.ABSENT));
    }

    private boolean isAttendanceLog(LocalDate attendDate) {
        return attendanceLogs.stream()
                .anyMatch(attendanceLog -> attendanceLog.isAttendDate(attendDate));
    }
}
