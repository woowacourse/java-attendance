package view;

import domain.Attendance;
import domain.AttendanceType;
import domain.Attendances;
import domain.Crew;
import domain.PenaltyPolicy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

public class OutputView {

    private static final String ERROR_MESSAGE_FORMAT = "[ERROR] %s%n";
    private static final String CHECKIN_FORMAT = "%d월 %d일 %s %02d:%02d (%s)%n";
    private static final String ABSENCE_FORMAT = "--:-- (결석) ";
    private static final String DATE_FORMAT = "%d월 %02d일 %s ";
    private static final String ARROW = "-> ";
    private static final String TIME_FORMAT = "%02d:%02d (%s) ";
    private static final String MODIFY_COMPLETE = "수정 완료!%n";
    private final String ATTENDANCE_RECORD_HEADER_FORMAT = "이번 달 %s의 출석 기록입니다.%n";
    private final String PENALTY_INFO_FORMAT = "%s 대상자입니다.%n";
    private final String ATTENDANCE_COUNT = "%s: %d회%n";
    private static final String SUCCESS = "출석";
    private static final String LATE = "지각";
    private static final String ABSENCE = "결석";
    private final String WARNING = "경고";
    private final String MEETING = "면담";
    private final String EXPULSION = "제적";

    public void printErrorMessage(RuntimeException e) {
        printEmptyLine();
        System.out.printf(ERROR_MESSAGE_FORMAT, e.getMessage());
    }

    public void printCheckInResult(Attendance attendance) {
        LocalDateTime checkInTime = attendance.getAttendanceTime().getTime();
        System.out.printf(
                CHECKIN_FORMAT,
                checkInTime.getMonthValue(),
                checkInTime.getDayOfMonth(),
                checkInTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()),
                checkInTime.getHour(),
                checkInTime.getMinute(),
                convertToAttendanceTypeString(attendance.judgeType())
        );
    }

    private String convertToAttendanceTypeString(AttendanceType attendanceType) {
        if (attendanceType.equals(AttendanceType.SUCCESS)) {
            return SUCCESS;
        }
        if (attendanceType.equals(AttendanceType.LATE)) {
            return LATE;
        }
        if (attendanceType.equals(AttendanceType.ABSENCE)) {
            return ABSENCE;
        }
        return "";
    }

    public void printModifiedResult(Attendance oldAttendance, Attendance newAttendance) {
        System.out.printf(
                DATE_FORMAT,
                oldAttendance.getAttendanceTime().getTime().getMonthValue(),
                oldAttendance.getAttendanceTime().getTime().getDayOfMonth(),
                oldAttendance.getAttendanceTime().getTime().getDayOfWeek()
                        .getDisplayName(TextStyle.FULL, Locale.getDefault())
        );
        if (oldAttendance.judgeType() == AttendanceType.ABSENCE) {
            System.out.print(ABSENCE_FORMAT);
        }
        if (oldAttendance.judgeType() != AttendanceType.ABSENCE) {
            System.out.printf(
                    TIME_FORMAT,
                    oldAttendance.getAttendanceTime().getTime().getHour(),
                    oldAttendance.getAttendanceTime().getTime().getMinute(),
                    convertToAttendanceTypeString(oldAttendance.judgeType())
            );
        }
        System.out.print(ARROW);
        if (newAttendance.judgeType() == AttendanceType.ABSENCE) {
            System.out.print(ABSENCE_FORMAT);
        }
        if (newAttendance.judgeType() != AttendanceType.ABSENCE) {
            System.out.printf(
                    TIME_FORMAT,
                    newAttendance.getAttendanceTime().getTime().getHour(),
                    newAttendance.getAttendanceTime().getTime().getMinute(),
                    convertToAttendanceTypeString(newAttendance.judgeType())
            );
        }
        System.out.printf(MODIFY_COMPLETE);
    }

    public void printAttendanceRecord(Crew crew, Attendances attendances, LocalDate today) {
        System.out.printf(ATTENDANCE_RECORD_HEADER_FORMAT, crew.getNickname());
        for (Attendance attendance : attendances.getAttendances()) {
            System.out.printf(
                    DATE_FORMAT,
                    today.getMonthValue(),
                    attendance.getAttendanceTime().getTime().getDayOfMonth(),
                    attendance.getAttendanceTime().getTime().toLocalDate().getDayOfWeek()
                            .getDisplayName(TextStyle.FULL, Locale.getDefault()));
            if (attendance.judgeType().equals(AttendanceType.ABSENCE)) {
                System.out.println(ABSENCE_FORMAT);
                continue;
            }
            System.out.printf(
                    TIME_FORMAT,
                    attendance.getAttendanceTime().getTime().getHour(),
                    attendance.getAttendanceTime().getTime().getMinute(),
                    convertToAttendanceTypeString(attendance.judgeType())
            );
            printEmptyLine();
        }

        Map<AttendanceType, Integer> counts = attendances.countAttendanceType();
        for (AttendanceType type : counts.keySet()) {
            System.out.printf(
                    ATTENDANCE_COUNT,
                    convertToAttendanceTypeString(type),
                    counts.get(type)
            );
        }

        if (PenaltyPolicy.judgePenalty(counts).isDanger()) {
            System.out.printf(PENALTY_INFO_FORMAT, convertToPunishmentTypeString(PenaltyPolicy.judgePenalty(counts)));
        }
    }

    private String convertToPunishmentTypeString(PenaltyPolicy punishmentType) {
        if (punishmentType.equals(PenaltyPolicy.WARNING)) {
            return WARNING;
        }
        if (punishmentType.equals(PenaltyPolicy.MEETING)) {
            return MEETING;
        }
        if (punishmentType.equals(PenaltyPolicy.EXPULSION)) {
            return EXPULSION;
        }
        return "";
    }

    private void printEmptyLine() {
        System.out.println();
    }
}
