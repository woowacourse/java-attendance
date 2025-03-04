package attendance.view;

import static attendance.domain.AttendanceType.*;

import attendance.domain.AttendanceResult;
import attendance.domain.AttendanceTime;
import attendance.domain.AttendanceTimes;
import attendance.domain.AttendanceType;
import attendance.domain.CrewStatus;
import attendance.domain.DangerousCrew;
import attendance.domain.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final String ATTENDANCE_RESULT = "%s월 %s일 %s %02d:%02d (%s) ";
    private static final String ABSENCE_RESULT = "%s월 %s일 %s --:-- (%s) ";
    private static final String MODIFY_ATTENDANCE_RESULT = "%s월 %s일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!";
    private static final String ATTENDANCE_HISTORY_NAME_MASSAGE = "이번 달 %s의 출석 기록입니다.";
    private static final String CREW_STATUS_INFO = "%s: %s회";
    private static final String CREW_STATUS = "%s 대상자입니다.";
    private static final String DANGEROUS_CREW_MESSAGE = "제적 위험자 조회 결과";
    private static final String DANGEROUS_CREW_INFO = "- %s: 결석 %s회, 지각 %s회 (%s)";

    private OutputView() {
    }

    public static OutputView create() {
        return new OutputView();
    }

    public void printAttendanceInfo(AttendanceTime attendanceTime, AttendanceType attendanceType) {
        LocalDate date = attendanceTime.getDate();
        LocalTime time = attendanceTime.getTime();
        DayOfWeek dayOfWeek = DayOfWeek.findDayOfWeek(date);
        System.out.println(ATTENDANCE_RESULT.formatted(
            date.getMonthValue(), date.getDayOfMonth(), dayOfWeek.getDayOfWeekName(),
            time.getHour(), time.getMinute(), attendanceType.getType()));
    }

    public void printAttendanceHistory(String nickname, AttendanceTimes attendanceTimes) {
        System.out.println(ATTENDANCE_HISTORY_NAME_MASSAGE.formatted(nickname));
        for (AttendanceTime attendanceTime : attendanceTimes.getAttendanceTimes()) {
            LocalDate date = attendanceTime.getDate();
            LocalTime time = attendanceTime.getTime();
            DayOfWeek dayOfWeek = DayOfWeek.findDayOfWeek(date);
            AttendanceType attendanceType = decideAttendanceType(attendanceTime);
            if (attendanceTime.isAbsenceTime(time)) {
                System.out.println(ABSENCE_RESULT.formatted(
                    date.getMonthValue(), date.getDayOfMonth(), dayOfWeek.getDayOfWeekName(),
                    attendanceType.getType()));
                continue;
            }

            System.out.println(ATTENDANCE_RESULT.formatted(
                date.getMonthValue(), date.getDayOfMonth(), dayOfWeek.getDayOfWeekName(),
                time.getHour(), time.getMinute(), attendanceType.getType()));
        }
    }

    public void printAttendanceResult(AttendanceResult attendanceResult) {
        Map<AttendanceType, Long> calculateResult = attendanceResult.getAttendanceResult();
        for (AttendanceType attendanceType : AttendanceType.values()) {
            System.out.println(CREW_STATUS_INFO.formatted(
                attendanceType.getType(), calculateResult.get(attendanceType)));
        }
    }

    public void printModifyAttendaneTimeResult(AttendanceTime attendanceTime,
        AttendanceTime modifyAttendanceTime) {
        LocalDate date = attendanceTime.getDate();
        LocalTime time = attendanceTime.getTime();
        LocalTime modifyTime = modifyAttendanceTime.getTime();
        AttendanceType attendanceType = decideAttendanceType(attendanceTime);
        AttendanceType modifyAttendanceType = decideAttendanceType(
            modifyAttendanceTime);
        DayOfWeek dayOfWeek = DayOfWeek.findDayOfWeek(date);
        System.out.println(MODIFY_ATTENDANCE_RESULT.formatted(
            date.getMonthValue(), date.getDayOfMonth(), dayOfWeek.getDayOfWeekName(),
            time.getHour(), time.getMinute(), attendanceType.getType(),
        modifyTime.getHour(), modifyTime.getMinute(), modifyAttendanceType.getType()));
    }

    public void printCrewStatus(CrewStatus crewStatus) {
        if (crewStatus == CrewStatus.NORMAL) {
            return;
        }
        System.out.println(CREW_STATUS.formatted(crewStatus.getStatusName()));
    }

    public void printDangerousCrew(List<DangerousCrew> sortedDangerousCrews) {
        System.out.println(DANGEROUS_CREW_MESSAGE);
        for (DangerousCrew dangerousCrew : sortedDangerousCrews) {
            AttendanceResult attendanceResult = dangerousCrew.getAttendanceResult();
            Map<AttendanceType, Long> result = attendanceResult.getAttendanceResult();

            System.out.println(DANGEROUS_CREW_INFO.formatted(
                dangerousCrew.getNickname(), result.get(ABSENCE), result.get(LATE), dangerousCrew.getCrewStatus().getStatusName()
            ));
        }
    }
}
