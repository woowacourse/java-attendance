package attendance.service;


import attendance.domain.AttendanceDismissStatus;
import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceStatus;
import attendance.domain.Attendances;
import attendance.domain.DateTimeFormatterWrapper;
import attendance.repository.AttendanceFileRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceManagerService {
    private final String ATTENDANCE_HISTORY_STATUS = "\n출석: %d회\n지각: %d회\n결석: %d회\n";
    private final AttendanceManager attendanceManager;
    private final AttendanceFileRepository attendanceFileRepository;
    private final String ATTENDANCE_RESULT_FORMAT = "%s (%s)";
    private final String ATTENDANCE_MODIFY_RESULT_FORMAT = "%s -> %s (%s) 수정 완료!";
    private final String ATTENDANCE_DISMISS_STATUS_FORMAT = "\n%s 대상자입니다.";
    private final String CREW_ATTENDANCE_HISTORY_PREFIX = "이번 달 %s의 출석 기록입니다.\n\n";


    public AttendanceManagerService(AttendanceManager attendanceManager,
                                    AttendanceFileRepository attendanceFileRepository) {
        this.attendanceManager = attendanceManager;
        this.attendanceFileRepository = attendanceFileRepository;
        initiateAttendanceManager();
    }

    private void initiateAttendanceManager() {
        List<String> attendanceLines = attendanceFileRepository.loadAttendanceLinesFromAttendanceFile();

        for (String attendanceLine : attendanceLines) {
            String[] attendanceUnits = attendanceLine.split(",");
            String nickname = attendanceUnits[0];
            LocalDateTime datetime = DateTimeFormatterWrapper.parsingAttendanceDateTime(attendanceUnits[1]);
            addAttendance(nickname, datetime);
        }
    }

    public void addAttendance(String nickname, LocalDateTime datetime) {
        attendanceManager.addAttendance(nickname, datetime);
    }

    public String attendanceResult(String nickname, LocalDate attendanceDate) {
        var attendances = findAttendancesByNickname(nickname);
        var attendanceTime = attendances.getAttendanceTime(attendanceDate);
        var attendanceStatus = attendances.getAttendanceStatus(attendanceDate);

        var dateTimeFormatResult = DateTimeFormatterWrapper.parsingAttendanceResult(
                LocalDateTime.of(attendanceDate, attendanceTime));
        return String.format(ATTENDANCE_RESULT_FORMAT, dateTimeFormatResult, attendanceStatus);
    }

    public Attendances findAttendancesByNickname(String nickname) {
        return attendanceManager.findAttendances(nickname);
    }

    public String formattingAttendanceModify(LocalTime afterModifyTime, String beforeAttendance,
                                             String afterAttendanceStatus) {
        String timeFormatResult = DateTimeFormatterWrapper.parsingAttendanceTime(afterModifyTime);
        return String.format(ATTENDANCE_MODIFY_RESULT_FORMAT, beforeAttendance, timeFormatResult,
                afterAttendanceStatus);
    }

    public void modify(String nickname, LocalDate modifyDate, LocalTime afterModifyTime) {
        attendanceManager.modifyAttendance(nickname, modifyDate, afterModifyTime);
    }

    public String attendanceModify(String nickname, LocalDate modifyDate, LocalTime afterModifyTime) {
        String beforeAttendance = attendanceResult(nickname, modifyDate);
        attendanceManager.modifyAttendance(nickname, modifyDate, afterModifyTime);
        String attendanceStatus = findAttendancesByNickname(nickname)
                .getAttendanceStatus(modifyDate);
        String timeFormatResult = DateTimeFormatterWrapper.parsingAttendanceTime(afterModifyTime);
        return String.format(ATTENDANCE_MODIFY_RESULT_FORMAT, beforeAttendance, timeFormatResult,
                attendanceStatus);
    }

    public String crewAttendanceHistory(String nickname) {
        AttendanceHistory attendanceHistory = attendanceManager.crewAttendanceHistory(nickname);
        Map<String, Integer> status = attendanceHistory.statusMap();
        return crewAttendanceHistory(status, nickname, attendanceHistory.attendanceHistories());
    }

    private String crewAttendanceHistory(Map<String, Integer> status, String nickname,
                                         List<String> attendanceHistories) {
        StringBuilder stringBuilder = new StringBuilder(String.format(CREW_ATTENDANCE_HISTORY_PREFIX, nickname));
        stringBuilder.append(formattingHistory(attendanceHistories));
        int absenceCount = AttendanceStatus.absenceCount(status);
        int lateCount = AttendanceStatus.lateCount(status);
        int attendanceCount = AttendanceStatus.attendanceCount(status);
        AttendanceDismissStatus attendanceDismissStatus = AttendanceDismissStatus.calculateAttendanceDismiss(
                absenceCount, AttendanceStatus.lateCount(status));
        return stringBuilder.append(attendanceStatus(absenceCount, lateCount, attendanceCount))
                .append(formattingAttendanceDismissStatus(attendanceDismissStatus))
                .toString();
    }

    private String formattingHistory(List<String> attendanceHistories) {
        return attendanceHistories.stream()
                .collect(Collectors.joining("\n"));
    }

    public String attendanceStatus(int absence, int late, int attendance) {
        return String.format(ATTENDANCE_HISTORY_STATUS, attendance, late, absence);
    }

    public String formattingAttendanceDismissStatus(AttendanceDismissStatus attendanceDismissStatus) {
        if (attendanceDismissStatus == AttendanceDismissStatus.NONE) {
            return "";
        }
        return String.format(ATTENDANCE_DISMISS_STATUS_FORMAT, attendanceDismissStatus.getStatus());
    }

    public String getAttendanceStatus(LocalDate date, String nickname) {
        return findAttendancesByNickname(nickname)
                .getAttendanceStatus(date);
    }

    public void validateNickname(String nickname) {
        attendanceManager.validateNickname(nickname);
        attendanceManager.findAttendances(nickname);
    }

    public void validateTime(LocalTime time) {
        attendanceManager.validateIsSchoolOpen(time);
    }

    public void validateDate(LocalDate date) {
        attendanceManager.validateIsAttendanceAvailable(date);
    }

    public List<String> attendancesNicknames() {
        return attendanceManager.attendancesNicknames();
    }
}
