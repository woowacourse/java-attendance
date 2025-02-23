package attendance.view;

import static attendance.domain.AttendanceType.ABSENCE;
import static attendance.domain.AttendanceType.LATE;

import attendance.domain.AttendanceHistory;
import attendance.domain.AttendancePolicy;
import attendance.domain.AttendanceType;
import attendance.domain.Crew;
import attendance.domain.CrewStatus;
import attendance.domain.dto.AttendanceHistoryDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class OutputView {
    private static final String ATTENDANCE_HISTORY_MESSAGE = "\n%02d월 %02d일 %s %02d:%02d (%s)";
    private static final String ATTENDANCE_HISTORIES_MESSAGE_HEADER = "\n이번 달 %s의 출석 기록입니다.\n";
    private static final String NO_ATTENDANCE_HISTORY_MESSAGE = "\n%02d월 %02d일 %s --:-- (결석)";
    private static final String MODIFY_ATTENDANCE_HISTORY_MESSAGE = "\n%d월 %d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!\n";
    private static final String ATTENDANCE_STATISTICS_MESSAGE = "%s: %d회";
    private static final String INTERVIEW_TARGET_MESSAGE = "면담 대상자입니다.\n";
    private static final String DANGEROUS_CREW_MESSAGE_HEADER = "\n제적 위험자 조회 결과";
    private static final String DANGEROUS_CREW_MESSAGE_BODY = "- %s: 결석 %d회, 지각 %d회 (%s)";

    public static void printAttendanceHistory(AttendanceHistory attendanceHistory) {
        LocalDateTime attendanceTime = attendanceHistory.getAttendanceDateTime();
        AttendanceType attendanceType = attendanceHistory.getAttendanceType();
        String message = ATTENDANCE_HISTORY_MESSAGE.formatted(attendanceTime.getMonthValue(),
                attendanceTime.getDayOfMonth(), attendanceTime.getDayOfWeek(), attendanceTime.getHour(),
                attendanceTime.getMinute(), attendanceType.getName());
        System.out.println(message);
    }

    public static void printModifyAttendanceHistory(AttendanceHistoryDto beforeAttendanceHistoryDto,
                                                    AttendanceHistory afterAttendanceHistory) {
        LocalDateTime beforeAttendanceTime = beforeAttendanceHistoryDto.getAttendanceTime();
        LocalDateTime afterAttendanceTime = afterAttendanceHistory.getAttendanceDateTime();
        AttendanceType beforeAttendanceType = beforeAttendanceHistoryDto.getAttendanceType();
        AttendanceType afterAttendanceType = afterAttendanceHistory.getAttendanceType();
        String message = MODIFY_ATTENDANCE_HISTORY_MESSAGE.formatted(beforeAttendanceTime.getMonthValue(),
                beforeAttendanceTime.getDayOfMonth(), beforeAttendanceTime.getDayOfWeek(),
                beforeAttendanceTime.getHour(), beforeAttendanceTime.getMinute(), beforeAttendanceType.getName(),
                afterAttendanceTime.getHour(), afterAttendanceTime.getMinute(), afterAttendanceType.getName());
        System.out.println(message);
    }

    public static void printAttendanceHistories(LocalDate today, Crew crew) {
        System.out.println(ATTENDANCE_HISTORIES_MESSAGE_HEADER.formatted(crew.getName()));
        int year = today.getYear();
        int month = today.getMonthValue();
        for (int date = 1; date < today.getDayOfMonth(); date++) {
            LocalDate attendanceDate = LocalDate.of(year, month, date);
            printAttendanceHistoryByDate(crew, attendanceDate);
        }
        System.out.println();
    }

    public static void printAttendanceStatistics(Map<AttendanceType, Integer> attendanceStatistics) {
        for (Entry<AttendanceType, Integer> entry : attendanceStatistics.entrySet()) {
            AttendanceType attendanceType = entry.getKey();
            int count = entry.getValue();
            System.out.println(ATTENDANCE_STATISTICS_MESSAGE.formatted(attendanceType, count));
        }
        System.out.println();
    }

    public static void printInterviewTarget() {
        System.out.println(INTERVIEW_TARGET_MESSAGE);
    }

    public static void printDangerousCrews(LocalDate today, List<Crew> dangerousCrews) {
        System.out.println(DANGEROUS_CREW_MESSAGE_HEADER);
        sortDangerousCrews(today, dangerousCrews);
        for (Crew crew : dangerousCrews) {
            printDangerousCrew(today, crew);
        }
        System.out.println();
    }

    private static void printAttendanceHistoryByDate(Crew crew, LocalDate attendanceDate) {
        try {
            AttendancePolicy.checkNotWeekendAndHoliday(attendanceDate);
        } catch (IllegalArgumentException e) {
            return;
        }
        try {
            printAttendanceHistory(crew.getAttendanceHistory(attendanceDate));
        } catch (IllegalArgumentException e) {
            printNoAttendanceHistory(attendanceDate);
        }
    }

    private static void printNoAttendanceHistory(LocalDate attendanceDate) {
        System.out.println(NO_ATTENDANCE_HISTORY_MESSAGE.formatted(attendanceDate.getMonthValue(),
                attendanceDate.getDayOfMonth(), attendanceDate.getDayOfWeek()));
    }

    private static void printDangerousCrew(LocalDate today, Crew crew) {
        Map<AttendanceType, Integer> attendanceStatistics = crew.calculateAttendanceResult(today);
        CrewStatus crewStatus = crew.calculateCrewStatus(attendanceStatistics);
        System.out.println(DANGEROUS_CREW_MESSAGE_BODY.formatted(crew.getName(), attendanceStatistics.get(ABSENCE),
                attendanceStatistics.get(LATE), crewStatus.getName()));
    }

    private static void sortDangerousCrews(LocalDate today, List<Crew> dangerousCrews) {
        dangerousCrews.sort(new Comparator<Crew>() {
            @Override
            public int compare(Crew o1, Crew o2) {
                CrewStatus crewStatus1 = o1.calculateCrewStatus(o1.calculateAttendanceResult(today));
                CrewStatus crewStatus2 = o2.calculateCrewStatus(o2.calculateAttendanceResult(today));
                if (crewStatus1 == crewStatus2) {
                    return o1.getName().compareTo(o2.getName());
                }
                return crewStatus2.getOrder() - crewStatus1.getOrder();
            }
        });
    }
}
