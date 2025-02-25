package attendance.view;

import static attendance.domain.AttendanceType.*;

import attendance.domain.AttendanceHistories;
import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceTime;
import attendance.domain.AttendanceType;
import attendance.domain.Crew;
import attendance.domain.DangerousCrew;
import attendance.domain.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class OutputView {

    private static final String ATTENDANCE_RESULT_MESSAGE = "%d월 %d일 %s %02d:%02d (%s)";
    private static final String ATTENDANCE_ABSENCE_MESSAGE = "%d월 %d일 %s --:-- (%s)";
    private static final String ATTENDANCE_INFO_MESSAGE = "이번 달 %s의 출석 기록입니다.";
    private static final String MODIFY_ATTENDANCE_RESULT_MESSAGE = "%d월 %d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!";
    private static final String ATTENDANCE_TYPE_RESULT_MESSAGE = "%s: %s회";
    private static final String INTERVIEW_TARGET_MESSAGE = "면담 대상자입니다.";
    private static final String DANGEROUS_CREW_INFO_MESSAGE = "제적 위험자 조회 결과";
    private static final String DANGEROUS_CREW_INFO = "- %s: 결석 %s회 지각 %s회 (%s)";

    public void printModifyAttendanceResult(AttendanceHistory attendanceHistory,
        AttendanceHistory modifyAttendanceHistory) {
        LocalDateTime attendanceTime = attendanceHistory.getAttendanceTime().getTime();
        LocalDateTime modifyAttendanceTime = modifyAttendanceHistory.getAttendanceTime()
            .getTime();
        int month = attendanceTime.getMonthValue();
        int day = attendanceTime.getDayOfMonth();
        DayOfWeek dayOfWeek = DayOfWeek.calculateDayOfWeek(attendanceTime.toLocalDate());
        AttendanceType attendanceType = attendanceHistory.getAttendanceType();
        int hour = attendanceTime.getHour();
        int minute = attendanceTime.getMinute();

        int modifyHour = modifyAttendanceTime.getHour();
        int modifyMinute = modifyAttendanceTime.getMinute();
        AttendanceType modifyAttendanceType = modifyAttendanceHistory.getAttendanceType();

        System.out.println(MODIFY_ATTENDANCE_RESULT_MESSAGE.formatted(
            month, day, dayOfWeek.getName(), hour, minute, attendanceType.getName(),
            modifyHour, modifyMinute, modifyAttendanceType.getName())
        );
    }

    public void printAttendanceHistories(Crew crew, AttendanceHistories attendanceHistories) {
        System.out.println(ATTENDANCE_INFO_MESSAGE.formatted(crew.getName()));
        for (AttendanceHistory attendanceHistory : attendanceHistories.getAttendanceHistories()) {
            LocalDateTime attendanceTime = attendanceHistory.getAttendanceTime()
                .getTime();

            int month = attendanceTime.getMonthValue();
            int day = attendanceTime.getDayOfMonth();
            DayOfWeek dayOfWeek = DayOfWeek.calculateDayOfWeek(attendanceTime.toLocalDate());
            int hour = attendanceTime.getHour();
            int minute = attendanceTime.getMinute();
            AttendanceType attendanceType = attendanceHistory.getAttendanceType();
            if (hour == 0 && minute == 0) {
                System.out.println(ATTENDANCE_ABSENCE_MESSAGE.formatted(
                    month, day, dayOfWeek.getName(), attendanceType.getName()));
                continue;
            }

            System.out.println(ATTENDANCE_RESULT_MESSAGE.formatted(
                month, day, dayOfWeek.getName(), hour, minute, attendanceType.getName())
            );
        }
    }

    public void printAttendanceResult(AttendanceHistory attendanceHistory) {
        AttendanceTime attendanceTime = attendanceHistory.getAttendanceTime();
        AttendanceType attendanceType = attendanceHistory.getAttendanceType();
        LocalDateTime localDateTime = attendanceTime.getTime();
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        DayOfWeek dayOfWeek = DayOfWeek.calculateDayOfWeek(localDate);
        System.out.printf(ATTENDANCE_RESULT_MESSAGE, month, day, dayOfWeek.getName(),
            localTime.getHour(),
            localTime.getMinute(), attendanceType.getName());
        System.out.println();
    }

    public void printInterviewTarget() {
        System.out.println(INTERVIEW_TARGET_MESSAGE);
    }

    public void printAttendanceTypeResult(Map<AttendanceType, Long> attendanceResult) {
        for (AttendanceType attendanceType : attendanceResult.keySet()) {
            System.out.println(ATTENDANCE_TYPE_RESULT_MESSAGE.formatted(
                attendanceType.getName(), attendanceResult.get(attendanceType))
            );
        }
    }

    public void printDangerousMessage() {
        System.out.println(DANGEROUS_CREW_INFO_MESSAGE);
    }

    public void printDangerousCrews(DangerousCrew dangerousCrew) {
        AttendanceHistories attendanceHistories = dangerousCrew.getAttendanceHistories();
        Map<AttendanceType, Long> attendanceResult = attendanceHistories.calculateAttendanceResult();
        System.out.println(DANGEROUS_CREW_INFO.formatted(
            dangerousCrew.getCrewName(), attendanceResult.get(ABSENCE), attendanceResult.get(LATE),
            dangerousCrew.getStatusName()));
    }
}
