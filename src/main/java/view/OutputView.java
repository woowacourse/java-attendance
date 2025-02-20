package view;

import dto.AttendanceResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import model.Attendance;
import model.AttendanceType;
import model.Attendances;
import model.Crew;

public class OutputView {

    private final String CHECK_IN_FORMAT = "%d월 %d일 %s %02d:%02d (%s)%n";
    private final String MODIFY_FORMAT = "%d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!%n";
    private final String MODIFY_ABSENCE_FORMAT = "%d월 %02d일 %s --:-- (결석) -> %02d:%02d (%s) 수정 완료!%n";
    private final String ATTENDANCE_RECORD_HEADER_FORMAT = "이번 달 %s의 출석 기록입니다.%n";
    private final String ATTENDANCE_RECORD_FORMAT = "%d월 %02d일 %s %02d:%02d (%s)%n";
    private final String ATTENDANCE_RECORD_ABSENCE_FORMAT = "%d월 %02d일 %s --:-- (결석)%n";
    private final String ATTENDANCE_COUNT = "%s: %d회%n";
    private final String SUCCESS = "출석";
    private final String BE_LATE = "지각";
    private final String ABSENCE = "결석";

    public void printCheckInResult(Attendance attendance) {
        LocalDateTime checkInTime = attendance.getCheckInTime();
        System.out.printf(
                CHECK_IN_FORMAT,
                checkInTime.getMonthValue(),
                checkInTime.getDayOfMonth(),
                checkInTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()),
                checkInTime.getHour(),
                checkInTime.getMinute(),
                convertToAttendanceTypeString(attendance.getAttendanceType())
        );
    }

    public void printModifiedResult(Optional<Attendance> existAttendance, Attendance modifiedAttendance) {
        if (existAttendance.isEmpty()) {
            System.out.printf(
                    MODIFY_ABSENCE_FORMAT,
                    modifiedAttendance.getCheckInTime().getMonthValue(),
                    modifiedAttendance.getCheckInTime().getDayOfMonth(),
                    modifiedAttendance.getCheckInTime().getDayOfWeek()
                            .getDisplayName(TextStyle.FULL, Locale.getDefault()),
                    modifiedAttendance.getCheckInTime().getHour(),
                    modifiedAttendance.getCheckInTime().getMinute(),
                    convertToAttendanceTypeString(modifiedAttendance.getAttendanceType())
            );
            return;
        }
        Attendance beforeAttendance = existAttendance.get();
        System.out.printf(
                MODIFY_FORMAT,
                beforeAttendance.getCheckInTime().getMonthValue(),
                beforeAttendance.getCheckInTime().getDayOfMonth(),
                beforeAttendance.getCheckInTime().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()),
                beforeAttendance.getCheckInTime().getHour(),
                beforeAttendance.getCheckInTime().getMinute(),
                convertToAttendanceTypeString(beforeAttendance.getAttendanceType()),
                modifiedAttendance.getCheckInTime().getHour(),
                modifiedAttendance.getCheckInTime().getMinute(),
                convertToAttendanceTypeString(modifiedAttendance.getAttendanceType())
        );
    }

    public void printAttendanceRecord(Crew crew, AttendanceResult result) {
        LocalDate today = LocalDate.now();
        System.out.printf(ATTENDANCE_RECORD_HEADER_FORMAT, crew.getNickname());
        Attendances attendances = result.getAttendances();
        for (Attendance attendance : attendances.getAttendances()) {
            if (!attendance.isCome()) {
                System.out.printf(
                        ATTENDANCE_RECORD_ABSENCE_FORMAT,
                        today.getMonthValue(),
                        attendance.getCheckInTime().getDayOfMonth(),
                        attendance.getCheckInTime().toLocalDate().getDayOfWeek()
                                .getDisplayName(TextStyle.FULL, Locale.getDefault()));
                continue;
            }
            System.out.printf(ATTENDANCE_RECORD_FORMAT,
                    attendance.getCheckInTime().getMonthValue(),
                    attendance.getCheckInTime().getDayOfMonth(),
                    attendance.getCheckInTime().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()),
                    attendance.getCheckInTime().getHour(),
                    attendance.getCheckInTime().getMinute(),
                    convertToAttendanceTypeString(attendance.getAttendanceType())
            );
        }

        Map<AttendanceType, Integer> counts = result.getCounts();
        for (Entry<AttendanceType, Integer> countsEntry : counts.entrySet()) {
            System.out.printf(
                    ATTENDANCE_COUNT,
                    convertToAttendanceTypeString(countsEntry.getKey()),
                    countsEntry.getValue()
            );
        }
    }

    private String convertToAttendanceTypeString(AttendanceType attendanceType) {
        if (attendanceType.equals(AttendanceType.SUCCESS)) {
            return SUCCESS;
        }
        if (attendanceType.equals(AttendanceType.BE_LATE)) {
            return BE_LATE;
        }
        if (attendanceType.equals(AttendanceType.ABSENCE)) {
            return ABSENCE;
        }
        return "";
    }
}
