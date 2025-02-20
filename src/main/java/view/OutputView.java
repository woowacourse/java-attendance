package view;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;
import model.Attendance;
import model.AttendanceType;

public class OutputView {

    private final String CHECK_IN_FORMAT = "%d월 %d일 %s %02d:%02d (%s)%n";
    private final String MODIFY_FORMAT = "%d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!%n";
    private final String MODIFY_ABSENCE_FORMAT = "%d월 %02d일 %s --:-- (결석) -> %02d:%02d (%s) 수정 완료!%n";
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
