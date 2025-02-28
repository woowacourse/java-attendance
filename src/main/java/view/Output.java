package view;

import domain.AttendanceStatus;
import dto.AttendanceResultDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

public class Output {
    public static LocalTime DEFAULT_TIME = LocalTime.of(0, 0);

    public String getAttendanceMessage(AttendanceResultDto attendanceResultDto) {
        if (attendanceResultDto.attendanceStatus().equals(AttendanceStatus.ABSENCE)) {
            return getAbsenceMessage(attendanceResultDto);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE HH:mm", Locale.KOREAN);
        LocalDateTime dateTime = LocalDateTime.of(attendanceResultDto.localDate(), attendanceResultDto.localTime());
        String formattedDate = dateTime.format(formatter);
        return String.format("%s (%s)", formattedDate,
                attendanceResultDto.attendanceStatus());
    }

    private String getAbsenceMessage(AttendanceResultDto attendanceResultDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE", Locale.KOREAN);
        String formattedDate = attendanceResultDto.localDate().format(formatter);
        return String.format("%s --:-- (%s)", formattedDate,
                attendanceResultDto.attendanceStatus());
    }


    public void printEditAttendanceResult(AttendanceResultDto beforeAttendanceResult, AttendanceResultDto afterAttendanceResult) {
        System.out.println(getAttendanceMessage(beforeAttendanceResult) +
                " -> " +
                getAttendanceMessage(afterAttendanceResult)
        );
    }

    public void printAttendResult(AttendanceResultDto attendanceResultDto) {
        System.out.println(getAttendanceMessage(attendanceResultDto));

    }

    public void printAttendanceRecord(String name, LocalDate today, Map<LocalDate, LocalTime> attendanceBook, Map<LocalDate, AttendanceStatus> attendanceStatuses) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", name);
        for (LocalDate date = today.withDayOfMonth(1); !date.isAfter(today); date = date.plusDays(1)) {
            if(!attendanceStatuses.containsKey(date) || attendanceStatuses.get(date).equals(AttendanceStatus.ABSENCE)) {
                System.out.println(getAbsenceMessage(new AttendanceResultDto(
                        date,
                        DEFAULT_TIME,
                        AttendanceStatus.ABSENCE
                )));
            }
            else {
                System.out.println(getAttendanceMessage(new AttendanceResultDto(
                        date,
                        attendanceBook.get(date),
                        attendanceStatuses.get(date)
                )));
            }
        }
    }
}
