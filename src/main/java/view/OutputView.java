package view;

import domain.Attendance;
import domain.Attendances;
import domain.Crew;
import domain.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class OutputView {

    public void printOptionMessage() {
        LocalDate today = LocalDate.now();
        String dayOfWeekName = DayOfWeek.getNameById(today.getDayOfWeek().getValue());
        System.out.printf("오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.\n" +
                        "1. 출석 확인\n" +
                        "2. 출석 수정\n" +
                        "3. 크루별 출석 기록 확인\n" +
                        "4. 제적 위험자 확인\n" +
                        "Q. 종료\n",
                today.getMonth().getValue(), today.getDayOfMonth(), dayOfWeekName);
    }

    public void printAttendanceInformation(Attendance attendance) {
        LocalDate today = LocalDate.now();
        String dayOfWeekName = DayOfWeek.getNameById(today.getDayOfWeek().getValue());
        String attendanceTime = covertLocalTimeToString(attendance.getAttendanceTime());
        String attendanceStatusName = getAttendanceStatusName(attendance);
        System.out.printf("%d월 %02d일 %s %s (%s)\n", today.getMonth().getValue(), today.getDayOfMonth(), dayOfWeekName,
                attendanceTime, attendanceStatusName);
    }

    public void printUpdatedAttendanceHistory(Attendance originalAttendance, Attendance editedAttendance) {

        LocalDate date = editedAttendance.getDay().getDate();
        String dayOfWeekName = DayOfWeek.getNameById(date.getDayOfWeek().getValue());

        String originalAttendanceTime = covertLocalTimeToString(originalAttendance.getAttendanceTime());
        String editedAttendanceTime = covertLocalTimeToString(editedAttendance.getAttendanceTime());

        String originAttendanceStatusName = getAttendanceStatusName(originalAttendance);
        String editedAttendanceStatusName = getAttendanceStatusName(editedAttendance);

        System.out.printf("%d월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!\n",
                date.getMonth().getValue(), date.getDayOfMonth(), dayOfWeekName,
                originalAttendanceTime, originAttendanceStatusName,
                editedAttendanceTime, editedAttendanceStatusName);
    }

    public void printCrewAttendanceHistoryMessage(String nickname) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n\n", nickname);
    }

    public void printAttendanceHistoryWithCrew(Attendances attendances) {
        int lateCount = attendances.getLateCount();
        int absentCount = attendances.getAbsentCount();
        int attendanceCount = attendances.getTotalAttendanceCount() - lateCount - absentCount;

        attendances.getAttendances().stream()
                .map(this::formatAttendanceRecord)
                .forEach(System.out::print);

        System.out.println();

        printAttendanceSummary(attendanceCount, lateCount, absentCount);

        System.out.println(attendances.getPenaltyStatus().getName() + " 대상자입니다.\n");
    }

    private String formatAttendanceRecord(Attendance attendance) {
        LocalDate date = attendance.getDay().getDate();
        String attendanceTime = Optional.ofNullable(attendance.getAttendanceTime())
                .map(this::covertLocalTimeToString)
                .orElse("--:--");
        String dayOfWeekName = DayOfWeek.getNameById(date.getDayOfWeek().getValue());
        String attendanceStatusName = getAttendanceStatusName(attendance);

        return String.format("%d월 %02d일 %s %s (%s)\n",
                date.getMonth().getValue(), date.getDayOfMonth(),
                dayOfWeekName, attendanceTime, attendanceStatusName);
    }

    private void printAttendanceSummary(int attendanceCount, int lateCount, int absentCount) {
        System.out.printf("출석: %d회\n", attendanceCount);
        System.out.printf("지각: %d회\n", lateCount);
        System.out.printf("결석: %d회\n\n", absentCount);
    }


    private String getAttendanceStatusName(Attendance attendance) {
        if (attendance.getAbsent()) {
            return "결석";
        }

        if (attendance.getLate()) {
            return "지각";
        }
        return "출석";
    }

    public void printPenaltyCrews(List<Crew> crews) {
        System.out.println("제적 위험자 조회 결과");
        sortCrews(crews);
        for (Crew crew : crews) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n", crew.getNickName(), crew.getAbsentCount(),
                    crew.getLateCount(),
                    crew.getPenaltyStatus().getName());
        }
        System.out.println();

    }

    private void sortCrews(List<Crew> crews) {
        crews.sort(
                Comparator.comparing((Crew crew) -> crew.getPenaltyStatus().getThreshold(), Comparator.reverseOrder())
                        .thenComparing(crew -> crew.getLateCount() + crew.getAbsentCount(), Comparator.reverseOrder())
                        .thenComparing(Crew::getNickName));
    }

    private String covertLocalTimeToString(LocalTime localTime) {

        if (localTime == null) {
            return "--:--";
        }

        return localTime.format(TimeFormat.DATE_TIME_FORMATTER);
    }
}
