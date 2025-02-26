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
        DayOfWeek dayOfWeek = DayOfWeek.calculateDayOfWeek(attendanceTime.toLocalDate());
        AttendanceType attendanceType = attendanceHistory.getAttendanceType();
        AttendanceType modifyAttendanceType = modifyAttendanceHistory.getAttendanceType();
        System.out.println(MODIFY_ATTENDANCE_RESULT_MESSAGE.formatted(
            attendanceTime.getMonthValue(), attendanceTime.getDayOfMonth(), dayOfWeek.getName(),
            attendanceTime.getHour(), attendanceTime.getMinute(),
            attendanceType.getTypeDescription(),
            modifyAttendanceTime.getHour(), modifyAttendanceTime.getMinute(),
            modifyAttendanceType.getTypeDescription())
        );
    }

    public void printAttendanceHistories(Crew crew, AttendanceHistories attendanceHistories) {
        System.out.println(ATTENDANCE_INFO_MESSAGE.formatted(crew.getName()));
        for (AttendanceHistory attendanceHistory : attendanceHistories.getAttendanceHistories()) {
            LocalDateTime attendanceTime = attendanceHistory.getAttendanceTime().getTime();

            int month = attendanceTime.getMonthValue();
            int day = attendanceTime.getDayOfMonth();
            DayOfWeek dayOfWeek = DayOfWeek.calculateDayOfWeek(attendanceTime.toLocalDate());
            int hour = attendanceTime.getHour();
            int minute = attendanceTime.getMinute();

            AttendanceType attendanceType = attendanceHistory.getAttendanceType();
            if (printAbsenceCase(attendanceTime, dayOfWeek, attendanceType)) {
                continue;
            }
            System.out.println(ATTENDANCE_RESULT_MESSAGE.formatted(
                month, day, dayOfWeek.getName(), hour, minute, attendanceType.getTypeDescription())
            );
        }
    }

    private boolean printAbsenceCase(LocalDateTime attendanceTime, DayOfWeek dayOfWeek,
        AttendanceType attendanceType) {
        if (attendanceTime.getHour() == 0 && attendanceTime.getMinute() == 0) {
            System.out.println(ATTENDANCE_ABSENCE_MESSAGE.formatted(
                attendanceTime.getMonthValue(), attendanceTime.getDayOfMonth(), dayOfWeek.getName(),
                attendanceType.getTypeDescription()));
            return true;
        }
        return false;
    }

    public void printAttendanceResult(AttendanceHistory attendanceHistory) {
        AttendanceTime attendanceTime = attendanceHistory.getAttendanceTime();
        AttendanceType attendanceType = attendanceHistory.getAttendanceType();
        LocalDate localDate = attendanceHistory.getAttendanceDate();
        LocalTime localTime = attendanceTime.getTime().toLocalTime();
        DayOfWeek dayOfWeek = DayOfWeek.calculateDayOfWeek(localDate);
        System.out.printf(ATTENDANCE_RESULT_MESSAGE, localDate.getMonth(),
            localDate.getDayOfMonth(), dayOfWeek.getName(),
            localTime.getHour(), localTime.getMinute(), attendanceType.getTypeDescription());
        System.out.println();
    }

    public void printInterviewTarget() {
        System.out.println(INTERVIEW_TARGET_MESSAGE);
    }

    public void printAttendanceTypeResult(Map<AttendanceType, Long> attendanceResult) {
        for (AttendanceType attendanceType : attendanceResult.keySet()) {
            System.out.println(ATTENDANCE_TYPE_RESULT_MESSAGE.formatted(
                attendanceType.getTypeDescription(), attendanceResult.get(attendanceType))
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
