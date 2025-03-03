package attendance.view;

import attendance.dto.AttendanceLogDto;
import attendance.dto.AttendanceWarning;
import attendance.model.AttendanceType;
import attendance.model.AttendanceWarningLevel;
import attendance.model.Nickname;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

public class OutputView {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public void printDate(LocalDate date) {
        System.out.printf("%n오늘은 %s입니다. ", date.format(DATE_FORMATTER));
    }

    public void printAttendanceLog(LocalDateTime attendanceDateTime, AttendanceType attendanceType) {
        System.out.printf("%n%s (%s)%n",
                attendanceDateTime.format(DATE_TIME_FORMATTER),
                attendanceType.getKoreanLabel());
    }

    public void printAttendanceLog(LocalDate attendanceDate,
                                   LocalTime attendanceTime,
                                   AttendanceType attendanceType) {
        if (attendanceTime == null) {
            System.out.printf("%n%s --:-- (%s)",
                    attendanceDate.format(DATE_FORMATTER),
                    AttendanceType.ABSENT.getKoreanLabel());
            return;
        }
        System.out.printf("%n%s (%s)",
                LocalDateTime.of(attendanceDate, attendanceTime).format(DATE_TIME_FORMATTER),
                attendanceType.getKoreanLabel());
    }

    public void printEditAttendanceLog(LocalTime attendanceTime, AttendanceType attendanceType) {
        System.out.printf(" -> %s (%s) 수정 완료!%n", attendanceTime.format(TIME_FORMATTER),
                attendanceType.getKoreanLabel());
    }

    public void printAttendanceLogs(Nickname nickname, List<AttendanceLogDto> attendanceLogDtos) {
        StringBuilder message = new StringBuilder();
        message.append("%n이번 달 %s의 출석 기록입니다.%n%n".formatted(nickname));
        attendanceLogDtos.forEach(attendanceLogDto -> message.append(formatAttendanceLog(attendanceLogDto)));
        System.out.println(message);
    }

    private String formatAttendanceLog(AttendanceLogDto attendanceLogDto) {
        if (attendanceLogDto.attendanceTime() == null) {
            return "%s --:-- (%s)%n".formatted(
                    attendanceLogDto.attendanceDate().format(DATE_FORMATTER),
                    AttendanceType.ABSENT.getKoreanLabel());
        }
        return "%s %s (%s)%n".formatted(
                attendanceLogDto.attendanceDate().format(DATE_FORMATTER),
                attendanceLogDto.attendanceTime().format(TIME_FORMATTER),
                attendanceLogDto.attendanceType().getKoreanLabel());
    }

    public void printAttendanceTypeCount(EnumMap<AttendanceType, Integer> count) {
        System.out.println(formatAttendanceTypeCount(count));
    }

    private String formatAttendanceTypeCount(EnumMap<AttendanceType, Integer> count) {
        return Arrays.stream(AttendanceType.values())
                .map(attendanceType ->
                        "%s: %d회".formatted(
                                attendanceType.getKoreanLabel(),
                                count.get(attendanceType)))
                .collect(Collectors.joining("\n"));
    }

    public void printWarningLevel(AttendanceWarningLevel attendanceWarningLevel) {
        if (attendanceWarningLevel == AttendanceWarningLevel.CLEAN) {
            System.out.println();
            return;
        }
        System.out.printf("%n%s 대상자입니다.%n", attendanceWarningLevel.getKoreanLabel());
    }

    public void printWarningList(List<AttendanceWarning> warnings) {
        if (warnings.isEmpty()) {
            System.out.println("\n제적 위험자가 없습니다.");
            return;
        }
        System.out.println("\n제적 위험자 조회 결과");
        System.out.println(formatWarningList(warnings));
    }

    private String formatWarningList(List<AttendanceWarning> warnings) {
        return warnings.stream()
                .map(warning ->
                        "- %s: 결석 %d회, 지각 %d회 (%s)".formatted(
                                warning.nickname(),
                                warning.absentCount(),
                                warning.lateCount(),
                                warning.koreanLabel()))
                .collect(Collectors.joining("\n"));
    }

    public void printError(String errorMessage) {
        System.out.printf("%n[ERROR] %s%n", errorMessage);
    }

    public void printTimeFormatError() {
        System.out.println("\n[ERROR] 시간은 24시 형식을 사용해야 합니다. (HH:mm)");
    }
}
