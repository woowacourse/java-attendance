package attendance.view;

import attendance.dto.AttendResult;
import attendance.dto.AttendanceLogDto;
import attendance.dto.EditResult;
import attendance.dto.AttendanceWarning;
import attendance.model.AttendanceType;
import attendance.model.AttendanceWarningLevel;
import attendance.model.Nickname;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

public class OutputView {

    public void printDate(LocalDate date) {
        System.out.print(date.format(DateTimeFormatter.ofPattern("\n오늘은 MM월 dd일 E요일입니다. ")));
    }

    public void printAttendance(AttendResult attendResult) {
        System.out.print(attendResult.attendanceDateTime().format(DateTimeFormatter.ofPattern("\nMM월 dd일 E요일 HH:mm ")));
        System.out.printf("(%s)%n", attendResult.attendanceType().getKoreanLabel());
    }

    public void printEditAttendanceLog(EditResult editResult) {
        System.out.print(editResult.targetDate().format(DateTimeFormatter.ofPattern("\nMM월 dd일 E요일 ")));
        if (editResult.beforeAttendanceTime() == null) {
            System.out.print("--:-- ");
        }
        if (editResult.beforeAttendanceTime() != null) {
            System.out.print(editResult.beforeAttendanceTime().format(DateTimeFormatter.ofPattern("HH:mm ")));
        }
        System.out.printf("(%s) ", editResult.beforeAttendanceType().getKoreanLabel());
        System.out.print(editResult.afterAttendanceTime().format(DateTimeFormatter.ofPattern("-> HH:mm ")));
        System.out.printf("(%s) ", editResult.afterAttendanceType().getKoreanLabel());
        System.out.printf("수정 완료!%n");
    }

    public void printAttendanceLogs(Nickname nickname, List<AttendanceLogDto> attendanceLogDtos) {
        System.out.printf("%n이번 달 %s의 출석 기록입니다.%n%n", nickname);
        attendanceLogDtos.forEach(this::printAttendanceLog);
        System.out.println();
    }

    private void printAttendanceLog(AttendanceLogDto attendanceLogDto) {
        if (attendanceLogDto.attendanceTime() == null) {
            System.out.print(
                    attendanceLogDto.attendanceDate().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일 --:-- ")));
            System.out.printf("(%s)%n", AttendanceType.ABSENT.getKoreanLabel());
            return;
        }
        System.out.print(attendanceLogDto.attendanceDate().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일 ")));
        System.out.print(attendanceLogDto.attendanceTime().format(DateTimeFormatter.ofPattern("HH:mm ")));
        System.out.printf("(%s)%n", attendanceLogDto.attendanceType().getKoreanLabel());
    }

    public void printAttendanceTypeCount(EnumMap<AttendanceType, Integer> count) {
        Arrays.stream(AttendanceType.values())
                .forEach(attendanceType ->
                        System.out.printf("%s: %d회%n", attendanceType.getKoreanLabel(), count.get(attendanceType)));
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
        warnings.forEach(warning -> System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n",
                warning.nickname(), warning.absentCount(), warning.lateCount(), warning.koreanLabel()));
    }

    public void printError(String errorMessage) {
        System.out.printf("%n[ERROR] %s%n", errorMessage);
    }

    public void printTimeFormatError() {
        System.out.println("\n[ERROR] 시간은 24시 형식을 사용해야 합니다. (HH:mm)");
    }
}
