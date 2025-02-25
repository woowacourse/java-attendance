package view;

import domain.Attendance;
import domain.AttendanceStandard;
import domain.Crew;
import domain.PenaltyStatus;
import domain.constant.StandardDate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class OutputView {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public void printOptionMessage(LocalDate today) {
        String dayOfWeekName = AttendanceStandard.getNameByDayOfWeek(today.getDayOfWeek());
        System.out.printf("""
                오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """, today.getMonth().getValue(), today.getDayOfMonth(), dayOfWeekName);
    }

    public void printAttendanceInformation(Attendance attendance) {
        LocalDate todayDate = StandardDate.TODAY.getDate();
        String dayOfWeekName = AttendanceStandard.getNameByDayOfWeek(todayDate.getDayOfWeek());
        String attendanceTime = covertLocalTimeToString(attendance.getAttendanceTime());
        String attendanceStatusName = getAttendanceStatusName(attendance);
        System.out.printf("%d월 %02d일 %s %s (%s)\n", todayDate.getMonth().getValue(), todayDate.getDayOfMonth(), dayOfWeekName, attendanceTime, attendanceStatusName);
    }

    public void printUpdatedAttendanceHistory(Attendance originalAttendance, Attendance updatedAttendance) {
        LocalDate date = updatedAttendance.getDay().getDate();
        String dayOfWeekName = AttendanceStandard.getNameByDayOfWeek(date.getDayOfWeek());

        String originalAttendanceTime = covertLocalTimeToString(originalAttendance.getAttendanceTime());
        String editedAttendanceTime = covertLocalTimeToString(updatedAttendance.getAttendanceTime());
        String originAttendanceStatusName = getAttendanceStatusName(originalAttendance);
        String editedAttendanceStatusName = getAttendanceStatusName(updatedAttendance);

        System.out.printf("%d월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!\n",
                date.getMonth().getValue(), date.getDayOfMonth(), dayOfWeekName,
                originalAttendanceTime, originAttendanceStatusName,
                editedAttendanceTime, editedAttendanceStatusName);
    }

    public void printCrewAttendanceHistoryMessage(String nickname) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n\n", nickname);
    }

    public void printAttendanceHistoryWithCrew(Crew crew) {

        printTotalAttendanceHistory(crew);
        System.out.println();
        printCountWithAttendanceStatus(crew.calculateAttendanceCount(), crew.calculateLateCount(), crew.calculateAbsentCount());

        if (crew.getPenaltyStatus() != PenaltyStatus.NONE) {
            System.out.println(crew.getPenaltyStatus().getName() + " 대상자입니다.\n");
        }
    }

    private void printCountWithAttendanceStatus(int attendanceCount, int lateCount, int absentCount) {
        System.out.printf("출석: %d회\n", attendanceCount);
        System.out.printf("지각: %d회\n", lateCount);
        System.out.printf("결석: %d회\n\n", absentCount);
    }

    private void printTotalAttendanceHistory(Crew crew) {
        List<Attendance> attendances = crew.getAttendances();
        attendances.sort(Comparator.comparing(attendance -> attendance.getDay().getDate()));

        for (Attendance attendance : attendances) {
            printAttendanceHistory(attendance);
        }
    }

    private void printAttendanceHistory(Attendance attendance) {
        LocalDate date = attendance.getDay().getDate();
        String attendanceTime = "--:--";
        String dayOfWeekName = AttendanceStandard.getNameByDayOfWeek(date.getDayOfWeek());
        String attendanceStatusName = getAttendanceStatusName(attendance);

        if (attendance.getAttendanceTime() != null) {
            attendanceTime = covertLocalTimeToString(attendance.getAttendanceTime());
        }

        System.out.printf("%d월 %02d일 %s %s (%s)\n", date.getMonth().getValue(), date.getDayOfMonth(), dayOfWeekName, attendanceTime, attendanceStatusName);
    }

    private String getAttendanceStatusName(Attendance attendance) {
        if (attendance.isAbsent()) {
            return "결석";
        }

        if (attendance.isLate()) {
            return "지각";
        }

        return "출석";
    }

    public void printPenaltyCrews(List<Crew> penaltyCrews) {
        System.out.println("제적 위험자 조회 결과");

        sortCrews(penaltyCrews);
        for (Crew crew : penaltyCrews) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                    crew.getNickName(), crew.calculateAbsentCount(),
                    crew.calculateLateCount(), crew.getPenaltyStatus().getName());
        }
        System.out.println();

    }

    private void sortCrews(List<Crew> crews) {
        crews.sort(
                Comparator.comparing((Crew crew) -> crew.getPenaltyStatus().getThreshold(), Comparator.reverseOrder())
                        .thenComparing(crew -> crew.calculateLateCount() + crew.calculateAbsentCount(), Comparator.reverseOrder())
                        .thenComparing(Crew::getNickName));
    }

    private String covertLocalTimeToString(LocalTime localTime) {
        if (localTime == null) {
            return "--:--";
        }

        return localTime.format(formatter);
    }

}
