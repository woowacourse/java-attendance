package view;

import domain.*;
import exception.AppException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        String datePart = "";
        String timePart = "--:--";
        AttendanceStatus status = AttendanceStatus.ABSENCE;
        for (int i = 1; i < today.getDayOfMonth(); i++) {
            try {
                CheckInDate date = CheckInDate.of(2024, 12, i);
                datePart = formatDate(date.toLocalDate());
                if (checkInHistory.hasHistory(date)) {
                    CheckInTime checkInTime = checkInHistory.getCheckInTime(date);
                    timePart = checkInTime.toLocalTime().format(TIME_FORMATTER);
                    LocalTime classStartTime = ClassTime.getClassStartTime(date.toLocalDate());
                    status = AttendanceStatus.determineAttendanceStatus(classStartTime, checkInTime.toLocalTime());
                }
            } catch (AppException e) {
                continue;
            }
            System.out.printf("%s %s (%s)\n", datePart, timePart, status);
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
