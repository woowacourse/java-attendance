package attendance.view;

import attendance.model.AttendanceLog;
import attendance.model.AttendanceType;
import attendance.model.AttendanceWarningLevel;
import attendance.model.EducationSchedule;
import attendance.model.Nickname;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

public class OutputView {

    public void printDate(LocalDate date) {
        System.out.print(date.format(DateTimeFormatter.ofPattern("오늘은 MM월 dd일 E요일입니다. ")));
    }

    public void printAttend(LocalDateTime dateTime, AttendanceType attendanceType) {
        System.out.print(dateTime.format(DateTimeFormatter.ofPattern("\nMM월 dd일 E요일 HH:mm ")));
        System.out.printf("(%s)%n", attendanceType.getKoreanLabel());
    }

    public void printEditAttendanceLog(AttendanceLog beforeAttendanceLog,
                                       AttendanceType beforeType,
                                       AttendanceLog afterAttendanceLog,
                                       AttendanceType afterType) {
        if (beforeAttendanceLog.isNotRecorded()) {
            System.out.print(beforeAttendanceLog.getAttendanceDate()
                    .format(DateTimeFormatter.ofPattern("\nMM월 dd일 E요일 --:-- ")));
            System.out.printf("(%s)", beforeType.getKoreanLabel());
            System.out.print(afterAttendanceLog.getAttendanceTime().format(DateTimeFormatter.ofPattern(" -> HH:mm ")));
            System.out.printf("(%s)", afterType.getKoreanLabel());
            System.out.println(" 수정 완료!");
            return;
        }
        System.out.print(beforeAttendanceLog.getAttendanceDateTime()
                .format(DateTimeFormatter.ofPattern("\nMM월 dd일 E요일 HH:mm ")));
        System.out.printf("(%s)", beforeType.getKoreanLabel());
        System.out.print(afterAttendanceLog.getAttendanceTime().format(DateTimeFormatter.ofPattern(" -> HH:mm ")));
        System.out.printf("(%s)", afterType.getKoreanLabel());
        System.out.println(" 수정 완료!");
    }

    public void printAttendanceLogs(Nickname nickname, List<AttendanceLog> logs) {
        System.out.printf("%n이번 달 %s의 출석 기록입니다.%n%n", nickname);
        for (AttendanceLog log : logs) {
            if (log.isNotRecorded()) {
                System.out.print(log.getAttendanceDate().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일 --:-- ")));
                System.out.printf("(%s)%n", AttendanceType.ABSENT.getKoreanLabel());
                continue;
            }
            System.out.print(log.getAttendanceDateTime().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm ")));
            System.out.printf("(%s)%n", determineAttendanceType(log.getAttendanceDate(), log.getAttendanceTime()).getKoreanLabel());
        }
        System.out.println();
    }

    private AttendanceType determineAttendanceType(LocalDate baseDate, LocalTime attendanceTime) {
        LocalTime startTimeInBaseDate = EducationSchedule.findStartTimeByDay(baseDate.getDayOfWeek());
        return AttendanceType.determine(startTimeInBaseDate, attendanceTime);
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
}
