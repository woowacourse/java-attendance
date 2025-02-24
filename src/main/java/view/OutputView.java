package view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import model.Attendance;
import model.AttendanceStatistics;
import model.AttendanceType;
import model.Attendances;
import model.Crew;
import model.PunishmentType;

public class OutputView {

    public static final String ERROR_MESSAGE_FORMAT = "[ERROR] %s%n";
    private final String CHECK_IN_FORMAT = "%d월 %d일 %s %02d:%02d (%s)%n";
    private final String MODIFY_FORMAT = "%d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!%n";
    private final String MODIFY_ABSENCE_FORMAT = "%d월 %02d일 %s --:-- (결석) -> %02d:%02d (%s) 수정 완료!%n";
    private final String ATTENDANCE_RECORD_HEADER_FORMAT = "이번 달 %s의 출석 기록입니다.%n";
    private final String ATTENDANCE_RECORD_FORMAT = "%d월 %02d일 %s %02d:%02d (%s)%n";
    private final String ATTENDANCE_RECORD_ABSENCE_FORMAT = "%d월 %02d일 %s --:-- (결석)%n";
    private final String ATTENDANCE_COUNT = "%s: %d회%n";
    private final String ATTENDANCE_PUNISHMENT = "%s 대상자입니다.";
    private final String EXPULSION_LIST_HEADER = "제적 위험자 조회 결과";
    private final String PUNISHMENT_FORMAT = "- %s: 결석 %d회, 지각 %d회 (%s)%n";
    private final String SUCCESS = "출석";
    private final String BE_LATE = "지각";
    private final String ABSENCE = "결석";
    private final String WARNING = "경고";
    private final String MEETING = "면담";
    private final String EXPULSION = "제적";

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

    public void printAttendanceRecord(Crew crew, Attendances attendances, AttendanceStatistics result) {
        LocalDate today = LocalDate.now();
        System.out.printf(ATTENDANCE_RECORD_HEADER_FORMAT, crew.getNickname());
        System.out.println(attendances.getAttendances().size());
        for (Attendance attendance : attendances.getAttendances()) {
            if (attendance.getAttendanceType().equals(AttendanceType.ABSENCE)) {
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

        Map<AttendanceType, Integer> counts = result.getStatistics();
        for (Entry<AttendanceType, Integer> countsEntry : counts.entrySet()) {
            System.out.printf(
                    ATTENDANCE_COUNT,
                    convertToAttendanceTypeString(countsEntry.getKey()),
                    countsEntry.getValue()
            );
        }

        if (!result.getPunishmentType().equals(PunishmentType.NONE)) {
            System.out.printf(ATTENDANCE_PUNISHMENT, convertToPunishmentTypeString(result.getPunishmentType()));
        }
    }

    public void printAllCrewPunishment(List<AttendanceStatistics> dangerCrews) {
        System.out.println(EXPULSION_LIST_HEADER);
        for (AttendanceStatistics crewAttendanceResult : dangerCrews) {
            Crew crew = crewAttendanceResult.getCrew();
            Map<AttendanceType, Integer> attendanceTypeCount = crewAttendanceResult.getStatistics();
            System.out.printf(
                    PUNISHMENT_FORMAT,
                    crew.getNickname(),
                    attendanceTypeCount.get(AttendanceType.ABSENCE),
                    attendanceTypeCount.get(AttendanceType.BE_LATE),
                    convertToPunishmentTypeString(crewAttendanceResult.getPunishmentType())
            );
        }
        printEmptyLine();
    }

    public void printErrorMessage(RuntimeException e) {
        printEmptyLine();
        System.out.printf(ERROR_MESSAGE_FORMAT, e.getMessage());
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

    private String convertToPunishmentTypeString(PunishmentType punishmentType) {
        if (punishmentType.equals(PunishmentType.WARNING)) {
            return WARNING;
        }
        if (punishmentType.equals(PunishmentType.MEETING)) {
            return MEETING;
        }
        if (punishmentType.equals(PunishmentType.EXPULSION)) {
            return EXPULSION;
        }
        return "";
    }

    private void printEmptyLine() {
        System.out.println();
    }
}
