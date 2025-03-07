package view;

import domain.AttendanceRecord;
import domain.AttendanceStatus;
import domain.AttendanceStatusCount;
import domain.Attendances;
import domain.NickName;
import domain.WarningCrew;
import domain.WarningCrews;
import domain.WarningStatus;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.Locale;
import java.util.stream.Collectors;

public class OutputView {
    public void printAttend(AttendanceRecord attendanceRecord) {
        // 12월 13일 금요일 09:59 (출석)
        System.out.println(formatAttendanceRecordToAttend(attendanceRecord));
    }

    private String formatAttendanceRecordToAttend(AttendanceRecord attendanceRecord) {
        return String.format("%s %s %s (%s)%n", formatLocalDate(attendanceRecord.getDate()),
                formatDayOfWeek(attendanceRecord.getDate()
                        .getDayOfWeek()),
                formatLocalTime(attendanceRecord.getTime(), attendanceRecord.isAbsence()),
                formatAttendanceStatus(AttendanceStatus.calculateAttendanceStatus(attendanceRecord)));
    }

    private String formatLocalDate(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("MM월 dd일"));
    }

    private String formatDayOfWeek(DayOfWeek dayOfWeek) {
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    private String formatLocalTime(LocalTime localTime, boolean isAbsence) {
        if (isAbsence) {
            return "--:--";
        }
        return localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    private String formatAttendanceStatus(AttendanceStatus attendanceStatus) {
        if (attendanceStatus == AttendanceStatus.ABSENT) {
            return "결석";
        }
        if (attendanceStatus == AttendanceStatus.LATE) {
            return "지각";
        }
        return "출석";
    }

    public void printEdit(AttendanceRecord beforeAttendanceRecord, AttendanceRecord editAttendanceRecord) {
        System.out.println(formatAttendanceRecordToEdit(beforeAttendanceRecord, editAttendanceRecord));
    }

    private String formatAttendanceRecordToEdit(AttendanceRecord beforeAttendanceRecord,
                                                AttendanceRecord editAttendanceRecord) {
        return String.format("%s %s %s (%s) -> %s (%s) 수정 완료!%n", formatLocalDate(beforeAttendanceRecord.getDate()),
                formatDayOfWeek(beforeAttendanceRecord.getDate()
                        .getDayOfWeek()),
                formatLocalTime(beforeAttendanceRecord.getTime(), beforeAttendanceRecord.isAbsence()),
                formatAttendanceStatus(AttendanceStatus.calculateAttendanceStatus(beforeAttendanceRecord)),
                formatLocalTime(editAttendanceRecord.getTime(), editAttendanceRecord.isAbsence()),
                formatAttendanceStatus(AttendanceStatus.calculateAttendanceStatus(editAttendanceRecord)));
    }

    public void printCheckAttendance(NickName nickName, Attendances attendances) {
        System.out.printf("이번 달 %s의 출석 기록입니다.%n", nickName.getNickName());
        String attendancesResult = formatCheckAttendance(attendances);
        System.out.println(attendancesResult);
        AttendanceStatusCount attendanceStatusCount = attendances.countAttendanceStatus();
        System.out.println(formatAttendanceStatusCount(attendanceStatusCount));
        System.out.println(formatWarningStatus(WarningStatus.calculateWarningStatus(attendanceStatusCount)));
        System.out.println();
    }

    private String formatCheckAttendance(Attendances attendances) {
        return attendances.getAttendances()
                .stream()
                .map(this::formatAttendanceRecordToAttend)
                .collect(Collectors.joining());
    }

    private String formatAttendanceStatusCount(AttendanceStatusCount attendanceStatusCount) {
        return String.format("""
                        출석: %d회
                        지각: %d회
                        결석: %d회""",
                attendanceStatusCount.getCount(AttendanceStatus.ATTENDANT),
                attendanceStatusCount.getCount(AttendanceStatus.LATE),
                attendanceStatusCount.getCount(AttendanceStatus.ABSENT));
    }

    private String formatWarningStatus(WarningStatus warningStatus) {
        if (warningStatus.equals(WarningStatus.CLEAR)) {
            return "";
        }
        return String.format("%s 대상자입니다.", formatWarningStatusShort(warningStatus));
    }

    private String formatWarningStatusShort(WarningStatus warningStatus) {
        if (warningStatus.equals(WarningStatus.EXPEL)) {
            return "제적";
        }
        if (warningStatus.equals(WarningStatus.INTERVIEW)) {
            return "면담";
        }
        if (warningStatus.equals(WarningStatus.WARNING)) {
            return "경고";
        }
        return "";
    }


    public void printWarningCrews(WarningCrews warningCrews) {
        System.out.println("제적 위험자 조회 결과");
        String sortedWarningCrewsMessage = formatWarningCrews(warningCrews);
        System.out.println(sortedWarningCrewsMessage);
    }

    private String formatWarningCrews(WarningCrews warningCrews) {
        return warningCrews.getWarningCrews()
                .stream()
                .sorted(Comparator.comparing((WarningCrew warningCrew) -> {
                            AttendanceStatusCount attendanceStatusCount = warningCrew.attendanceStatusCount();
                            return attendanceStatusCount.getCount(AttendanceStatus.LATE)
                                    + attendanceStatusCount.getCount(AttendanceStatus.ABSENT) * WarningStatus.LATE_FER_ABSENCE;
                        })
                        .reversed()
                        .thenComparing(warningCrew -> warningCrew.nickName()
                                .getNickName()))
                .map(this::formatWarningCrew)
                .collect(Collectors.joining());
    }

    private String formatWarningCrew(WarningCrew warningCrew) {
        // - 빙티: 결석 3회, 지각 2회 (면담)
        return String.format("- %s: 결석 %d회, 지각 %d회 (%s)%n", warningCrew.nickName()
                        .getNickName(), warningCrew.attendanceStatusCount()
                        .getCount(AttendanceStatus.ABSENT), warningCrew.attendanceStatusCount()
                        .getCount(AttendanceStatus.LATE),
                formatWarningStatusShort(WarningStatus.calculateWarningStatus(warningCrew.attendanceStatusCount())));
    }

    public void printErrorMessage(RuntimeException e) {
        System.out.println("[ERROR] " + e.getMessage());
        System.out.println();
    }
}
