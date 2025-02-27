package view;

import dto.AttendanceStatusDto;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputView {
    public static void printToday() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String date = now.format(DateTimeFormatter.ofPattern("M월 d일"));
        String dayOfWeek = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        System.out.printf("오늘은 %s %s입니다. ", date, dayOfWeek);
    }

    public static void printAttendanceStatus(AttendanceStatusDto dto) {
        System.out.printf("%n%02d월 %02d일 %s %02d:%02d (%s)%n%n",
                dto.month(),
                dto.day(),
                dto.dayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                dto.hour(),
                dto.minute(),
                dto.attendanceType().getName());
    }

    public static void printErrorMessage(String message) {
        System.out.println("[ERROR] " + message);
    }

    public static void printEditAttendanceStatus(List<AttendanceStatusDto> statusDtos) {
        AttendanceStatusDto oldStatusDto = statusDtos.getFirst();
        AttendanceStatusDto newStatusDto = statusDtos.getLast();

        // 이전 기록
        System.out.printf("%n%02d월 %02d일 %s %02d:%02d (%s) -> ",
                oldStatusDto.month(),
                oldStatusDto.day(),
                oldStatusDto.dayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                oldStatusDto.hour(),
                oldStatusDto.minute(),
                oldStatusDto.attendanceType().getName());

        // 바뀐 기록
        System.out.printf("%02d:%02d (%s) 수정 완료!%n%n",
                newStatusDto.hour(),
                newStatusDto.minute(),
                newStatusDto.attendanceType().getName());
    }
}
