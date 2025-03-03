package view;

import domain.*;
import exception.AppException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class OutputView {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일", Locale.KOREAN);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public void printTodayCheckInTime(CheckInDate checkInDate, CheckInTime checkInTime) {
        AttendanceStatus status = AttendanceStatus.determineAttendanceStatus(checkInDate.getClassStartTime(), checkInTime.toLocalTime());
        System.out.println(formatDate(checkInDate.toLocalDate())
                + " " + formatTime(checkInTime.toLocalTime())
                + " (" + status + ")");
    }

    public void printModifiedChSeckInTime(CheckInDate checkInDate, CheckInTime beforeTime, CheckInTime afterTime) {
        AttendanceStatus beforeStatus = AttendanceStatus.determineAttendanceStatus(checkInDate.getClassStartTime(), beforeTime.toLocalTime());
        AttendanceStatus afterStatus = AttendanceStatus.determineAttendanceStatus(checkInDate.getClassStartTime(), afterTime.toLocalTime());
        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료! \n"
                , formatDate(checkInDate.toLocalDate())
                , formatTime(beforeTime.toLocalTime())
                , beforeStatus
                , formatTime(afterTime.toLocalTime())
                , afterStatus
        );
    }

    public void printAttendanceHistory(String nickname, LocalDate today, CheckInHistory checkInHistory) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", nickname);
        for (int i = 1; i < today.getDayOfMonth(); i++) {
            printAttendanceForDay(i, checkInHistory);
        }
        printAttendanceResult(checkInHistory, today);
        printIsCrewDanger(checkInHistory, today);
    }

    public void printDangerCrews(List<DangerCrew> crews) {
        System.out.println("제적 위험자 조회 결과");

        for (DangerCrew crew : crews) {
            int lateCount = crew.getLateCount();
            int absenceCount = crew.getAbsenceCount();
            PenaltyStatus status = PenaltyStatus.determinePenalty(lateCount, absenceCount);
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n", crew, absenceCount, lateCount, status);
        }
    }

    private void printAttendanceForDay(int day, CheckInHistory checkInHistory) {
        String datePart = "";
        String timePart = "--:--";
        AttendanceStatus status = AttendanceStatus.ABSENCE;
        try {
            CheckInDate date = getCheckInDate(day);
            datePart = formatDate(date.toLocalDate());
            if (checkInHistory.hasHistory(date)) {
                CheckInTime checkInTime = checkInHistory.getCheckInTime(date);
                timePart = formatTime(checkInTime.toLocalTime());
                status = AttendanceStatus.determineAttendanceStatus(date, checkInTime);
            }
        } catch (AppException e) {
            return;
        }
        System.out.printf("%s %s (%s)\n", datePart, timePart, status);
    }


    private static CheckInDate getCheckInDate(int i) {
        CheckInDate date;
        date = CheckInDate.of(2024, 12, i);
        return date;
    }

    private void printAttendanceResult(CheckInHistory checkInHistory, LocalDate today) {
        System.out.println();
        System.out.println("출석: " + checkInHistory.countPresence(today) + "회");
        System.out.println("지각: " + checkInHistory.countLate(today) + "회");
        System.out.println("결석: " + checkInHistory.countAbsence(today) + "회");
        System.out.println();
    }


    private void printIsCrewDanger(CheckInHistory checkInHistory, LocalDate today) {
        PenaltyStatus status = checkInHistory.getPenaltyStatus(today);
        if (status != PenaltyStatus.NONE) {
            System.out.println(status + "대상자 입니다.");
        }
    }

    private String formatDate(LocalDate date) {
        String datePart = date.format(DATE_FORMATTER);
        String dayOfWeek = date.getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL, Locale.KOREAN);
        return datePart + " " + dayOfWeek;
    }

    private String formatTime(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }
}
