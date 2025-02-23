package attendance.view;

import static attendance.domain.AttendanceType.*;

import attendance.domain.AttendanceHistories;
import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceTime;
import attendance.domain.AttendanceType;
import attendance.domain.Crew;
import attendance.domain.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Map;

public class OutputView {

    private static final String ATTENDANCE_RESULT_MESSAGE = "%d월 %d일 %s %02d:%02d (%s)";
    private static final String ATTENDANCE_ABSENCE_MESSAGE = "%d월 %d일 %s --:-- (%s)";
    private static final String ATTENDANCE_INFO_MESSAGE = "이번 달 %s의 출석 기록입니다.";
    private static final String MODIFY_ATTENDANCE_RESULT_MESSAGE = "%d월 %d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!";

    /*
    public void printAttendanceResult(AttendanceHistory attendanceHistory) {
        LocalDateTime attendanceTime = attendanceHistory.getAttendanceTime();
        DayOfWeek dayOfWeek = attendanceTime.getDayOfWeek();
        AttendanceType attendanceType = attendanceHistory.getAttendanceType();
        System.out.println(ATTENDANCE_RESULT_MESSAGE.formatted(
                attendanceTime.getMonthValue(),
                attendanceTime.getDayOfMonth(),
                dayOfWeek,
                attendanceTime.getHour(),
                attendanceTime.getMinute(),
                attendanceType.getName())
        );
    }
     */


    public void printModifyAttendanceResult(AttendanceHistory attendanceHistory,
        AttendanceHistory modifyAttendanceHistory) {
        LocalDateTime attendanceTime = attendanceHistory.getAttendanceTime().getAttendanceTime();
        LocalDateTime modifyAttendanceTime = modifyAttendanceHistory.getAttendanceTime()
            .getAttendanceTime();
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
                .getAttendanceTime();

            int month = attendanceTime.getMonthValue();
            int day = attendanceTime.getDayOfMonth();
            DayOfWeek dayOfWeek = DayOfWeek.calculateDayOfWeek(attendanceTime.toLocalDate());
            int hour = attendanceTime.getHour();
            int minute = attendanceTime.getMinute();
            AttendanceType attendanceType = attendanceHistory.getAttendanceType();
            if (hour == 0 && minute == 0) {
                System.out.println(ATTENDANCE_ABSENCE_MESSAGE.formatted(
                    month, day, dayOfWeek.getName(), ABSENCE.getName()));
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

        LocalDateTime localDateTime = attendanceTime.getAttendanceTime();
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
        System.out.println("면담 대상자입니다.\n");
    }
    
    /*
    public void printDangerousCrews(LocalDate now, List<Crew> dangerousCrews) {
        System.out.println("\n제적 위험자 조회 결과");
        sortDangerousCrews(now, dangerousCrews);
        for (Crew crew : dangerousCrews) {
            Map<AttendanceType, Integer> attendanceResult = crew.calculateAttendanceResult(now);
            CrewStatus crewStatus = crew.calculateCrewStatus(attendanceResult);
            System.out.println("- %s: 결석 %d회, 지각 %d회 (%s)".formatted(
                    crew.getName(),
                    attendanceResult.get(ABSENCE),
                    attendanceResult.get(LATE),
                    crewStatus.getName())
            );
        }
        System.out.println();
    }

     */
    /*

    private void sortDangerousCrews(LocalDate now, List<Crew> dangerousCrews) {
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

     */
}
