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

    private static String formatAttendanceLog(AttendanceLogDto attendanceLogDto) {
        return String.format("%d월 %d일 %s요일 %02d:%02d (%s)",
                attendanceLogDto.attendanceDate().getMonthValue(),
                attendanceLogDto.attendanceDate().getDayOfMonth(),
                Convertor.convertDayOfWeekToKorean(attendanceLogDto.attendanceDate().getDayOfWeek()),
                attendanceLogDto.attendanceTime().getHour(),
                attendanceLogDto.attendanceTime().getMinute(),
                attendanceLogDto.attendanceStatus().getDescription());
    }
}
