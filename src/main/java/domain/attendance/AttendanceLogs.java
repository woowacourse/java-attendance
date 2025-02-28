package domain.attendance;

import domain.crew.CrewStatus;
import exception.ErrorException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceLogs {

    private final List<AttendanceLog> attendanceLogs;

    public AttendanceLogs() {
        this.attendanceLogs = new ArrayList<>();
    }

    public AttendanceLog findAttendanceLog(LocalDate attendDate) {
        return attendanceLogs.stream()
                .filter(attendanceLog -> attendanceLog.isAttendDate(attendDate))
                .findFirst()
                .orElseThrow(() -> new ErrorException("해당 날짜의 출석 기록이 없습니다."));
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
        AttendanceLog oldAttendanceLog = findAttendanceLog(editDate);
        attendanceLogs.remove(oldAttendanceLog);
        return registerLog(LocalDateTime.of(editDate, editTime));
    }

    public Map<AttendanceStatus, Integer> calculateCrewAttendanceStatus(LocalDate todayDate) {
        Map<AttendanceStatus, Integer> attendanceHistoryStatus = new HashMap<>();
        initializeAttendanceHistoryStatus(attendanceHistoryStatus);
        calculateAttendanceHistoryStatus(attendanceHistoryStatus, todayDate);
        calculateUnattendCount(attendanceHistoryStatus, todayDate);
        return attendanceHistoryStatus;
    }

    public CrewStatus calculateCrewStatus(LocalDate todayDate) {
        Map<AttendanceStatus, Integer> attendanceLogsStatus = calculateCrewAttendanceStatus(todayDate);
        return CrewStatus.findStatus(attendanceLogsStatus.get(AttendanceStatus.LATE),
                attendanceLogsStatus.get(AttendanceStatus.ABSENT));
    }

    public List<AttendanceLog> getAttendanceHistory(LocalDate todayDate) {
        List<AttendanceLog> attendanceHistory = new ArrayList<>();
        for (AttendanceLog attendanceLog : attendanceLogs) {
            addAttendanceHistory(attendanceHistory, attendanceLog, todayDate);
        }
        return attendanceHistory;
    }

    private void addAttendanceHistory(List<AttendanceLog> attendanceHistory, AttendanceLog attendanceLog, LocalDate todayDate) {
        if (attendanceLog.getAttendanceDate().isBefore(todayDate)) {
            attendanceHistory.add(attendanceLog);
        }
    }

    private boolean isAttendanceLog(LocalDate attendDate) {
        return attendanceLogs.stream()
                .anyMatch(attendanceLog -> attendanceLog.isAttendDate(attendDate));
    }

    private void initializeAttendanceHistoryStatus(Map<AttendanceStatus, Integer> attendanceHistoryStatus) {
        for (AttendanceStatus attendanceStatus : AttendanceStatus.values()) {
            attendanceHistoryStatus.put(attendanceStatus, 0);
        }
    }

    private void calculateAttendanceHistoryStatus(Map<AttendanceStatus, Integer> attendanceHistoryStatus, LocalDate todayDate) {
        List<AttendanceLog> crewAttendanceLogs = getAttendanceHistory(todayDate);
        for (AttendanceLog attendanceLog : crewAttendanceLogs) {
            AttendanceStatus attendanceStatus = attendanceLog.getAttendanceStatus();
            attendanceHistoryStatus.put(attendanceStatus, attendanceHistoryStatus.get(attendanceStatus) + 1);
        }
    }

    private void calculateUnattendCount(Map<AttendanceStatus, Integer> attendanceHistoryStatus, LocalDate todayDate) {
        LocalDate startDate = LocalDate.of(2024, 12, 1);
        int unattendCount = 0;
        for (LocalDate logDate = startDate; logDate.isBefore(todayDate); logDate = logDate.plusDays(1)) {
            unattendCount = countUnattendDate(logDate, unattendCount);
        }
        attendanceHistoryStatus.put(AttendanceStatus.ABSENT, attendanceHistoryStatus.get(AttendanceStatus.ABSENT) + unattendCount);
    }

    private int countUnattendDate(LocalDate logDate, int unattendCount) {
        if (isUnattendDate(logDate)) {
            unattendCount++;
        }
        return unattendCount;
    }

    private boolean isUnattendDate(LocalDate logDate) {
        return !(isAttendanceLog(logDate) || isWeekend(logDate) || isHoliday(logDate));
    }

    private boolean isWeekend(LocalDate attendDate) {
        DayOfWeek dayOfWeek = attendDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private boolean isHoliday(LocalDate attendDate) {
        return attendDate.getMonth() == Month.DECEMBER && attendDate.getDayOfMonth() == 25;
    }
}
