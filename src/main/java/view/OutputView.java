package view;

import controller.AttendanceController;
import domain.Attendance;
import domain.AttendanceStatus;
import domain.CheckInTime;
import domain.PenaltyStatus;
import dto.AttendanceLogDetailsDTO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputView {

    public void printTodayCheckInTime(CheckInTime time) {
        LocalDateTime localDateTime = time.toLocalDateTime();
        AttendanceStatus attendanceStatus = time.getAttendanceStatus();
        System.out.println(formatDateTime(localDateTime) + " (" + attendanceStatusToString(attendanceStatus) + ")");
    }

    private String attendanceStatusToString(AttendanceStatus attendanceStatus) {
        if (attendanceStatus == AttendanceStatus.PRESENCE) {
            return "출석";
        }
        if (attendanceStatus == AttendanceStatus.LATE) {
            return "지각";
        }
        return "결석";
    }

    public void printModifyCheckInTime(CheckInTime before, CheckInTime after) {
        String beforeDateTime = formatDateTime(before.toLocalDateTime());
        String beforeStatus = attendanceStatusToString(before.getAttendanceStatus());
        String afterTime = formatTimePart(after.toLocalDateTime());
        String afterStatus = attendanceStatusToString(after.getAttendanceStatus());
        System.out.printf("%s (%s) -> %s (%s) 수정 완료! \n", beforeDateTime, beforeStatus, afterTime, afterStatus);
    }

    public void printAttendanceLog(AttendanceLogDetailsDTO attendanceLogDetails) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", attendanceLogDetails.getName());

        List<LocalDateTime> attendanceTimes = attendanceLogDetails.getAttendanceTimes();
        List<Integer> attendanceDays = attendanceLogDetails.getAttendanceDays();

        for (int i = 1; i < LocalDate.now().getDayOfMonth(); i++) {
            LocalDate localDate = LocalDate.of(2024, 12, i);
            if (localDate.getDayOfWeek() == DayOfWeek.SATURDAY
                    || localDate.getDayOfWeek() == DayOfWeek.SUNDAY
                    || i == 25) {
                continue;
            }
            if (attendanceDays.contains(i)) {
                LocalDateTime localDateTime = attendanceTimes.get(attendanceLogDetails.getAttendanceDays().indexOf(i));
                String dateTime = formatDateTime(localDateTime);
                String status = attendanceStatusToString(CheckInTime.of(localDateTime).getAttendanceStatus());
                System.out.printf("%s (%s)\n", dateTime, status);
                continue;
            }
            String datePart = formatDatePart(LocalDateTime.of(2024, 12, i, 0, 0));
            System.out.printf("%s --:-- (결석)\n", datePart);
        }

        int presenceCount = attendanceLogDetails.getPresenceCount();
        int lateCount = attendanceLogDetails.getLateCount();
        int absenceCount = attendanceLogDetails.getAbsenceCount();
        PenaltyStatus penaltyStatus = attendanceLogDetails.getPenaltyStatus();
        String status = penaltyStatusToString(penaltyStatus);

        System.out.println();
        System.out.println("출석: " + presenceCount + "회");
        System.out.println("지각: " + lateCount + "회");
        System.out.println("결석: " + absenceCount + "회");
        System.out.println();

        if (status != null) {
            System.out.println(status + " 대상자입니다.");
        }
    }

    private String penaltyStatusToString(PenaltyStatus penaltyStatus) {
        if (penaltyStatus == PenaltyStatus.WARNING) {
            return "경고";
        }
        if (penaltyStatus == PenaltyStatus.INTERVIEWEE) {
            return "면담";
        }
        if (penaltyStatus == PenaltyStatus.EXPULSION) {
            return "제적";
        }
        return null;
    }

    public void printDangerCrews(List<Attendance> dangerCrews) {
        System.out.println("제적 위험자 조회 결과");

        for (Attendance attendance : dangerCrews) {
            int absenceCount = attendance.countAbsence();
            int lateCount = attendance.countLate();

            PenaltyStatus penaltyStatus = PenaltyStatus.getPenaltyStatus(absenceCount, lateCount);
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n", attendance.getName(), absenceCount, lateCount, penaltyStatusToString(penaltyStatus));
        }
    }

    private String formatDateTime(LocalDateTime localDateTime) {
        String datePart = formatDatePart(localDateTime);
        String timePart = formatTimePart(localDateTime);

        return datePart + " " + timePart;
    }

    private static String formatDatePart(LocalDateTime localDateTime) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일", Locale.KOREAN);
        String datePart = localDateTime.format(dateFormatter);

        String dayOfWeek = localDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        return datePart + " " + dayOfWeek;
    }

    private static String formatTimePart(LocalDateTime localDateTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(AttendanceController.HOUR_MINUTE_FORMAT);
        String timePart = localDateTime.format(timeFormatter);
        return timePart;
    }
}
