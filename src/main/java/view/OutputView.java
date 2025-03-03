package view;

import domain.AttendanceState;
import domain.Calender;
import domain.Crew;
import domain.DateProvider;
import domain.Dismissal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;

public class OutputView {

    public void printWellComeMessage(final DateProvider dateProvider) {

        String wellComeMessageFormat = String.format(
                "\n오늘은 %02d월 %02d일 %s입니다. 기능을 선택해 주세요.",
                dateProvider.getMonth(),
                dateProvider.getDayOfMonth(),
                dateProvider.getDayOfWeek().getDescription());

        System.out.println(wellComeMessageFormat);
    }

    public void printAttendanceRecord(final LocalDateTime attendanceDateTime, final AttendanceState attendanceState) {
        String attendanceRecordFormat = String.format
                ("\n%02d월 %02d일 %s %02d:%02d (%s)",
                        attendanceDateTime.getMonth().getValue(),
                        attendanceDateTime.getDayOfMonth(),
                        Calender.findBy(attendanceDateTime).getDescription(),
                        attendanceDateTime.getHour(),
                        attendanceDateTime.getMinute(),
                        attendanceState.getState());

        System.out.println(attendanceRecordFormat);

    }

    public void printUpdateAttendanceRecord(final LocalDateTime beforeAttendance,
                                            final AttendanceState beforeAttendanceState,
                                            final LocalDateTime afterAttendance,
                                            final AttendanceState afterAttendanceState) {
        String updateAttendanceResultFormat = String.format(
                "\n%02d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!",
                beforeAttendance.getMonthValue(),
                beforeAttendance.getDayOfMonth(),
                Calender.findBy(beforeAttendance).getDescription(),
                beforeAttendance.getHour(),
                beforeAttendance.getMinute(),
                beforeAttendanceState.getState(),
                afterAttendance.getHour(),
                afterAttendance.getMinute(),
                afterAttendanceState.getState());

        System.out.println(updateAttendanceResultFormat);

    }

    public void printAttendanceHistory(final String name, final Map<LocalDateTime, AttendanceState> history) {
        StringJoiner sj = new StringJoiner("\n");
        String attendanceHistoryInfoFormat = String.format("\n이번 달 %s의 출석 기록입니다.", name);
        for (Entry<LocalDateTime, AttendanceState> attendance : history.entrySet()) {
            LocalDateTime key = attendance.getKey();
            if (key.getHour() != 0) {
                String historyFormat = String.format("%02d월 %02d일 %s %02d:%02d (%s)", key.getMonthValue(),
                        key.getDayOfMonth(),
                        Calender.findBy(key).getDescription(),
                        key.getHour(),
                        key.getMinute(),
                        attendance.getValue().getState());
                sj.add(historyFormat);
                continue;
            }
            String historyAbsenceFormat = String.format(
                    "%02d월 %02d일 %s --:-- (%s)",
                    key.getMonthValue(),
                    key.getDayOfMonth(),
                    Calender.findBy(key).getDescription(),
                    attendance.getValue().getState());
            sj.add(historyAbsenceFormat);
        }

        System.out.println(attendanceHistoryInfoFormat);
        System.out.println(sj);
    }

    public void printAttendanceStateCounts(final Map<AttendanceState, Integer> attendanceStateCounts,
                                           final Dismissal dismissal) {
        StringJoiner sj = new StringJoiner("\n");

        for (Entry<AttendanceState, Integer> attendanceState : attendanceStateCounts.entrySet()) {
            AttendanceState key = attendanceState.getKey();
            String stateCountFormat = String.format(
                    "%s: %d회",
                    key.getState(),
                    attendanceState.getValue());

            sj.add(stateCountFormat);
        }
        String dismissalFormat = String.format(
                "\n%s 대상자입니다.",
                dismissal.getDescription());
        sj.add(dismissalFormat);

        System.out.println("\n" + sj);
    }

    public void printAbsenceRecord(final Map<Crew, Map<AttendanceState, Integer>> absenceRecord) {
        StringJoiner sj = new StringJoiner("\n");

        for (Entry<Crew, Map<AttendanceState, Integer>> absence : absenceRecord.entrySet()) {
            Crew crew = absence.getKey();

            Map<AttendanceState, Integer> attendanceStateCount = absence.getValue();

            Integer absenceCount = attendanceStateCount.get(AttendanceState.ABSENCE);
            Integer lateCount = attendanceStateCount.get(AttendanceState.LATE);
            String absenceResultFormat = String.format(
                    "- %s: %s %d회, %s %d회, (%s)",
                    crew.getName(),
                    AttendanceState.ABSENCE.getState(),
                    absenceCount, AttendanceState.LATE.getState(),
                    lateCount,
                    Dismissal.findDismissalBy(lateCount, absenceCount).getDescription());

            sj.add(absenceResultFormat);
        }
        System.out.println("제적 위험자 조회 결과");
        System.out.println("\n" + sj);
    }

    public void printExit() {
        System.out.println("프로그램을 종료합니다.");
    }
}
