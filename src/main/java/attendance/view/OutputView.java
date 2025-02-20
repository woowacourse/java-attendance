package attendance.view;

import static attendance.domain.AttendanceType.ABSENCE;
import static attendance.domain.AttendanceType.LATE;

import attendance.domain.AttendanceHistory;
import attendance.domain.CrewStatus;
import attendance.domain.dto.AttendanceHistoryDto;
import attendance.domain.AttendancePolicy;
import attendance.domain.AttendanceType;
import attendance.domain.Crew;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class OutputView {
    private static final String ATTENDANCE_RESULT_MESSAGE = "%d월 %d일 %s %02d:%02d (%s)";
    private static final String MODIFY_ATTENDANCE_RESULT_MESSAGE = "%d월 %d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!";

    public void printAttendanceResult(AttendanceHistory attendanceHistory) {
        LocalDateTime attendanceTime = attendanceHistory.getAttendanceTime();
        int month = attendanceTime.getMonthValue();
        int day = attendanceTime.getDayOfMonth();
        DayOfWeek dayOfWeek = attendanceTime.getDayOfWeek();
        AttendanceType attendanceType = attendanceHistory.getAttendanceType();
        System.out.println(ATTENDANCE_RESULT_MESSAGE.formatted(month, day, dayOfWeek, attendanceTime.getHour(),
                attendanceTime.getMinute(), attendanceType.getName()));
    }

    public void printModifyAttendanceResult(AttendanceHistoryDto beforeAttendanceHistoryDto,
                                            AttendanceHistory afterAttendanceHistory) {
        LocalDateTime beforeAttendanceTime = beforeAttendanceHistoryDto.getAttendanceTime();
        LocalDateTime afterAttendanceTime = afterAttendanceHistory.getAttendanceTime();
        int month = beforeAttendanceTime.getMonthValue();
        int day = beforeAttendanceTime.getDayOfMonth();
        DayOfWeek dayOfWeek = beforeAttendanceTime.getDayOfWeek();
        AttendanceType beforeAttendanceType = beforeAttendanceHistoryDto.getAttendanceType();
        AttendanceType afterAttendanceType = afterAttendanceHistory.getAttendanceType();
        System.out.println(
                MODIFY_ATTENDANCE_RESULT_MESSAGE.formatted(month, day, dayOfWeek, beforeAttendanceTime.getHour(),
                        beforeAttendanceTime.getMinute(), beforeAttendanceType.getName(), afterAttendanceTime.getHour(),
                        afterAttendanceTime.getMinute(), afterAttendanceType.getName())
        );
    }

    public void printAttendanceHistories(LocalDate now, Crew crew) {
        for (int i = 1; i < now.getDayOfMonth(); i++) {
            int year = now.getYear();
            int month = now.getMonthValue();
            LocalDate date = LocalDate.of(year, month, i);
            try {
                AttendancePolicy.checkHoliday(date);
            } catch (IllegalArgumentException e) {
                continue;
            }
            try {
                AttendanceHistory attendanceHistory = crew.getAttendanceHistory(date);
                LocalDateTime attendanceTime = attendanceHistory.getAttendanceTime();
                AttendanceType attendanceType = attendanceHistory.getAttendanceType();
                System.out.println("%02d월 %02d일 %s %02d:%02d (%s)".formatted(month, i, date.getDayOfWeek(),
                        attendanceTime.getHour(), attendanceTime.getMinute(), attendanceType.getName()));
            } catch (IllegalArgumentException e) {
                System.out.println("%02d월 %02d일 %s --:-- (결석)".formatted(month, i, date.getDayOfWeek()));
            }
        }
    }

    public void printAttendanceResult(Map<AttendanceType, Integer> attendanceResult) {
        for (AttendanceType attendanceType : attendanceResult.keySet()) {
            System.out.println("%s: %d회".formatted(attendanceType, attendanceResult.get(attendanceType)));
        }
    }

    public void printDangerousCrews(LocalDate now, List<Crew> dangerousCrews) {
        sortDangerousCrews(now, dangerousCrews);
        for (Crew crew : dangerousCrews) {
            Map<AttendanceType, Integer> attendanceResult = crew.calculateAttendanceResult(now);
            CrewStatus crewStatus = crew.calculateCrewStatus(attendanceResult);
            System.out.println("- %s: 결석 %d회, 지각 %d회 (%s)".formatted(crew.getName(),
                    attendanceResult.get(ABSENCE),
                    attendanceResult.get(LATE), crewStatus.getName())
            );
        }
    }

    private static void sortDangerousCrews(LocalDate now, List<Crew> dangerousCrews) {
        dangerousCrews.sort(new Comparator<Crew>() {
            @Override
            public int compare(Crew o1, Crew o2) {
                CrewStatus crewStatus1 = o1.calculateCrewStatus(o1.calculateAttendanceResult(now));
                CrewStatus crewStatus2 = o2.calculateCrewStatus(o2.calculateAttendanceResult(now));
                if (crewStatus1 == crewStatus2) {
                    return o1.getName().compareTo(o2.getName());
                }
                return crewStatus2.getOrder() - crewStatus1.getOrder();
            }
        });
    }
}
