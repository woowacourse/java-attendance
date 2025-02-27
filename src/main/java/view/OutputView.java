package view;

import dto.AttendanceLogDto;
import util.Convertor;

public class OutputView {

    public static void printErrorMessage(String message) {
        System.out.println(message);
    }

    public static void printAttendanceRegisterLog(AttendanceLogDto attendanceLogDto) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.lineSeparator())
                .append(formatAttendanceLog(attendanceLogDto))
                .append(System.lineSeparator());
        System.out.println(stringBuilder);
    }

    public static void printAttendanceEditLog(AttendanceLogDto oldAttendanceLogDto, AttendanceLogDto newAttendanceLogDto) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.lineSeparator())
                .append(formatAttendanceLog(oldAttendanceLogDto))
                .append(" -> ")
                .append(formatAttendanceLogStatus(newAttendanceLogDto))
                .append(" 수정 완료!")
                .append(System.lineSeparator());
        System.out.println(stringBuilder);
    }

    private static String formatAttendanceLog(AttendanceLogDto attendanceLogDto) {
        return String.format("%d월 %d일 %s요일 %s",
                attendanceLogDto.attendanceDate().getMonthValue(),
                attendanceLogDto.attendanceDate().getDayOfMonth(),
                Convertor.convertDayOfWeekToKorean(attendanceLogDto.attendanceDate().getDayOfWeek()),
                formatAttendanceLogStatus(attendanceLogDto));
    }

    private static String formatAttendanceLogStatus(AttendanceLogDto attendanceLogDto) {
        return String.format("%02d:%02d (%s)",
                attendanceLogDto.attendanceTime().getHour(),
                attendanceLogDto.attendanceTime().getMinute(),
                attendanceLogDto.attendanceStatus().getDescription());
    }
}
