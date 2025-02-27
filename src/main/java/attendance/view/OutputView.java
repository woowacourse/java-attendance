package attendance.view;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.LATE;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatistics;
import attendance.domain.AttendanceStatus;
import attendance.domain.CampusManager;
import attendance.domain.Crew;
import attendance.domain.CrewStatus;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OutputView {
    public static void printNotOperationDate(final LocalDate attendanceDate) {
        int month = attendanceDate.getMonthValue();
        int date = attendanceDate.getDayOfMonth();
        DayOfWeek day = attendanceDate.getDayOfWeek();
        String dayName = day.getDisplayName(TextStyle.FULL, Locale.KOREAN);
        System.out.printf("[ERROR] %02d월 %02d일 %s은 등교일이 아닙니다.\n", month, date, dayName);
    }

    public static void printNotRegisteredCrewNickname() {
        System.out.println("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    public static void printNotOperationTime() {
        System.out.println("[ERROR] 캠퍼스 운영 시간은 매일 08:00 ~ 23:00 입니다.");
    }

    public static void printDuplicatedAttendance() {
        System.out.println("[ERROR] 이미 출석하셨습니다.");
    }

    public static void printAttendance(final Attendance attendance) {
        LocalDate attendanceDate = attendance.getDate();
        int month = attendanceDate.getMonthValue();
        int date = attendanceDate.getDayOfMonth();
        DayOfWeek day = attendanceDate.getDayOfWeek();
        String dayName = day.getDisplayName(TextStyle.FULL, Locale.KOREAN);
        LocalTime attendanceTime = attendance.getTime();
        int hour = attendanceTime.getHour();
        int minute = attendanceTime.getMinute();
        AttendanceStatus status = attendance.getStatus();
        System.out.printf("%02d월 %02d일 %s %02d:%02d (%s)\n", month, date, dayName, hour, minute, status.getName());
    }

    public static void printNoAttendanceToModify() {
        System.out.println("[ERROR] 수정할 출석 기록이 없습니다.");
    }

    public static void printAttendanceModificationResult(
            final Attendance beforeAttendance,
            final Attendance afterAttendance
    ) {
        LocalDate attendanceDate = beforeAttendance.getDate();
        DayOfWeek attendanceDay = attendanceDate.getDayOfWeek();
        LocalTime beforeAttendanceTime = beforeAttendance.getTime();
        LocalTime afterAttendanceTime = afterAttendance.getTime();
        System.out.printf("\n%02d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!\n",
                attendanceDate.getMonthValue(),
                attendanceDate.getDayOfMonth(),
                attendanceDay.getDisplayName(TextStyle.FULL, Locale.KOREAN),
                beforeAttendanceTime.getHour(),
                beforeAttendanceTime.getMinute(),
                beforeAttendance.getStatus().getName(),
                afterAttendanceTime.getHour(),
                afterAttendanceTime.getMinute(),
                afterAttendance.getStatus().getName()
        );
    }

    public static void printMonthlyAttendances(final LocalDate today, final Crew crew, final List<Attendance> attendances) {
        System.out.printf("\n이번 달 %s의 출석 기록입니다.\n", crew.getNickname());
        System.out.println();
        for (int i = today.getDayOfMonth() - 1; i > 0; i--) {
            LocalDate date = today.minusDays(i);
            boolean isOperationDate = CampusManager.isOperationDate(date);
            if (!isOperationDate) {
                continue;
            }
            attendances.stream()
                    .filter(attendance -> attendance.isDateEquals(date))
                    .findAny()
                    .ifPresentOrElse(attendance -> printAttendance(attendance), () -> printNoAttendance(date));
        }
        System.out.println();
    }

    private static void printNoAttendance(final LocalDate noAttendanceDate) {
        int month = noAttendanceDate.getMonthValue();
        int date = noAttendanceDate.getDayOfMonth();
        DayOfWeek day = noAttendanceDate.getDayOfWeek();
        String dayName = day.getDisplayName(TextStyle.FULL, Locale.KOREAN);
        System.out.printf("%02d월 %02d일 %s --:-- (결석)\n", month, date, dayName);
    }

    public static void printAttendanceStatistics(final AttendanceStatistics attendanceStatistics) {
        for (AttendanceStatus status : AttendanceStatus.values()) {
            int statusCount = attendanceStatistics.getStatusCount(status);
            System.out.printf("%s: %d회\n", status.getName(), statusCount);
        }
        System.out.println();
        CrewStatus crewStatus = attendanceStatistics.calculateCrewStatus();
        if (crewStatus.equals(CrewStatus.INTERVIEW)) {
            System.out.println("면담 대상자입니다.");
        }
    }

    public static void printDangerousCrews(final Map<Crew, AttendanceStatistics> dangerousCrews) {
        System.out.println("\n제적 위험자 조회 결과");
        List<Crew> crews = sortDangerousCrews(dangerousCrews);
        for (Crew crew : crews) {
            String nickname = crew.getNickname();
            AttendanceStatistics statistics = dangerousCrews.get(crew);
            int absenceCount = statistics.getStatusCount(ABSENCE);
            int lateCount = statistics.getStatusCount(LATE);
            CrewStatus crewStatus = statistics.calculateCrewStatus();
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n", nickname, absenceCount, lateCount, crewStatus.getName());
        }
    }

    private static List<Crew> sortDangerousCrews(final Map<Crew, AttendanceStatistics> dangerousCrews) {
        List<Crew> crews = new ArrayList<>(dangerousCrews.keySet());
        crews.sort(new Comparator<Crew>() {
            @Override
            public int compare(Crew firstCrew, Crew secondCrew) {
                AttendanceStatistics firstCrewStatistics = dangerousCrews.get(firstCrew);
                AttendanceStatistics secondCrewStatistics = dangerousCrews.get(secondCrew);
                return firstCrewStatistics.compareTo(secondCrewStatistics);
            }
        });
        return crews;
    }
}
