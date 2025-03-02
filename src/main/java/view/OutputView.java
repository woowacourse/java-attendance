package view;

import domain.*;
import domain.constant.StandardDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class OutputView {

    public void printOptionMessage() {
        LocalDate today = StandardDate.TODAY;
        String dayOfWeekName = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        System.out.printf("""
                
                오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """, today.getMonthValue(), today.getDayOfMonth(), dayOfWeekName);
    }

    public void printAttendanceCheckMessage(LocalDateTime localDateTime) {
        String dayOfWeekName = localDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        LocalTime attendanceTime = localDateTime.toLocalTime();
        AttendanceStatus attendanceStatus = StandardTime.judge(localDateTime);

        System.out.println();
        System.out.printf("%d월 %02d일 %s %s (%s)\n", localDateTime.getMonthValue(), localDateTime.getDayOfMonth(), dayOfWeekName, attendanceTime, attendanceStatus.getName());
    }

    public void printUpdatedAttendanceMessage(LocalDateTime originalDateTime, LocalDateTime updatedDateTime) {
        String originalDayOfWeekName = originalDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        LocalTime originalAttendanceTime = originalDateTime.toLocalTime();
        AttendanceStatus originalStatus = StandardTime.judge(originalDateTime);

        String updatedDayOfWeekName = updatedDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        LocalTime updatedAttendanceTime = updatedDateTime.toLocalTime();
        AttendanceStatus updatedStatus = StandardTime.judge(updatedDateTime);

        System.out.printf("%d월 %02d일 %s %s (%s) ", originalDateTime.getMonthValue(), originalDateTime.getDayOfMonth(), originalDayOfWeekName, originalAttendanceTime, originalStatus.getName());
        System.out.printf("-> %d월 %02d일 %s %s (%s) 수정 완료!\n", updatedDateTime.getMonthValue(), updatedDateTime.getDayOfMonth(), updatedDayOfWeekName, updatedAttendanceTime, updatedStatus.getName());
    }

    public void printAttendanceLogsWithCrew(String nickname, Attendances attendances) {
        System.out.printf("\n이번 달 %s의 출석 기록입니다\n\n", nickname);
        List<Attendance> logs = attendances.getLogsWithName(nickname);
        logs.sort(Comparator.comparing(log -> log.getLocalDateTime().toLocalDate().getDayOfMonth()));

        printAttendanceLogs(logs);
        printAttendanceStatus(nickname, attendances);
        printPenaltyStatus(nickname, attendances);

        System.out.println();
    }

    private void printAttendanceLogs(List<Attendance> logs) {
        for (Attendance log : logs) {
            LocalDateTime localDateTime = log.getLocalDateTime();
            LocalDate date = localDateTime.toLocalDate();
            String attendanceTime = convertAbsenceTime(localDateTime);
            String dayOfWeekName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
            System.out.printf("%d월 %02d일 %s %s (%s)\n", date.getMonthValue(), date.getDayOfMonth(), dayOfWeekName, attendanceTime, StandardTime.judge(localDateTime).getName());
        }
    }

    private void printAttendanceStatus(String nickname, Attendances attendances) {
        int attendanceCount = attendances.calculateAttendanceCount(nickname);
        int lateCount = attendances.calculateLateCount(nickname);
        int absentCount = attendances.calculateAbsentCount(nickname);

        System.out.printf("\n출석: %d회\n", attendanceCount);
        System.out.printf("지각: %d회\n", lateCount);
        System.out.printf("결석: %d회\n", absentCount);
    }

    private void printPenaltyStatus(String nickname, Attendances attendances) {
        String penaltyName = PenaltyStatus.findStatusByNickname(nickname, attendances).getName();
        if (!penaltyName.equals("비대상자")) {
            System.out.printf("\n%s 대상자입니다.", penaltyName);
        }
    }

    public void printPenaltyCrews(Attendances attendances) {
        System.out.println("\n제적 위험자 조회 결과");
        List<String> penaltyCrews = getPenaltyCrews(attendances.getCrewNames(), attendances);

        for (String nickname : penaltyCrews) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                    nickname, attendances.calculateAbsentCount(nickname),
                    attendances.calculateLateCount(nickname),
                    PenaltyStatus.findStatusByNickname(nickname, attendances).getName());
        }
        System.out.println();
    }

    private List<String> getPenaltyCrews(List<String> allCrewNames, Attendances attendances) {
        return allCrewNames.stream()
                .filter(nickname -> PenaltyStatus.findStatusByNickname(nickname, attendances) != PenaltyStatus.NONE)
                .sorted(Comparator.<String>comparingInt(
                                nickname -> PenaltyStatus.getTotalAbsentCount(
                                        nickname,
                                        attendances))
                        .reversed()
                        .thenComparing(nickname -> nickname)
                ).toList();
    }

    private String convertAbsenceTime(LocalDateTime dateTime) {
        if (dateTime.toLocalTime() == LocalTime.MAX) {
            return "--:--";
        }
        return dateTime.toLocalTime().toString();
    }
}
