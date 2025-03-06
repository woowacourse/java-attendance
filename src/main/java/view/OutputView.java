package view;


import domain.AbsenceLevel;
import domain.AttendanceHistory;
import domain.AttendanceResult;
import domain.Attendances;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class OutputView {
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("M월 d일 E요일");
    private final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("M월 d일 E요일 HH:mm");

    public void outputMenu(LocalDate today) {
        System.out.printf("\n오늘은 %s입니다. 기능을 선택해 주세요.%n" +
                "1. 출석 확인\n" +
                "2. 출석 수정\n" +
                "3. 크루별 출석 기록 확인\n" +
                "4. 제적 위험자 확인\n" +
                "Q. 종료\n", DATE_FORMAT.format(today));
    }

    public void outputAttendance(LocalDateTime dateTime) {
        System.out.println(AttendanceFormat(dateTime));
    }

    public void outputResult(LocalDateTime oldAttendanceDateTime, LocalDateTime newAttendanceDateTime) {
        System.out.printf("%n%s -> %s (%s) 수정 완료!%n", AttendanceFormat(oldAttendanceDateTime)
                , TIME_FORMAT.format(newAttendanceDateTime),
                AttendanceResult.getAttendanceResult(newAttendanceDateTime).getName()
        );
    }

    public void outputAttendances(String crew, Attendances attendances, LocalDate today) {
        System.out.printf("%n%s의 출석 기록입니다.%n%n", crew);

        Stream.iterate(LocalDate.of(2024, 12, 1), date -> date.plusDays(1))
                .limit(LocalDate.of(2024, 12, 1).until(today).getDays() + 1)
                .forEach(date -> outPutAttendanceTime(attendances, date));
    }

    public void outputCountOfAttendances(String crew, AttendanceHistory attendanceHistory, LocalDate today
    ) {
        System.out.printf("%n출석: %d회%n" + "지각: %d회%n" + "결석 : %d회%n",
                attendanceHistory.getAttendanceCount(crew, today), attendanceHistory.getLateCount(crew, today),
                attendanceHistory.getAbsentCount(crew, today));
    }

    public void outputAbsenceLevel(AbsenceLevel absenceLevel) {
        System.out.printf("%n%s 대상자입니다.%n", absenceLevel.getName());
    }

    public void outputDangerousCrews(List<String> dangerousCrews, AttendanceHistory attendanceHistory,
                                     LocalDate today) {
        System.out.printf("%n제적 위험자 조회 결과%n");
        dangerousCrews.forEach(crew -> outputDangerousCrews(crew, attendanceHistory, today));
    }

    private void outputDangerousCrews(String crew, AttendanceHistory attendanceHistory, LocalDate today) {
        int absentCount = attendanceHistory.getAbsentCount(crew, today);
        int lateCount = attendanceHistory.getLateCount(crew, today);
        System.out.printf("- %s: %s %d회, %s %d회 (%s)%n", crew, AttendanceResult.ABSENT.getName(), absentCount,
                AttendanceResult.LATE.getName(), lateCount,
                AbsenceLevel.getAbsenceLevel(lateCount, absentCount).getName());
    }

    private void outPutAttendanceTime(Attendances attendances, LocalDate date) {
        try {
            LocalDateTime attendanceDateTime = attendances.get(date);
            System.out.printf(AttendanceFormat(attendanceDateTime));
        } catch (NoSuchElementException e) {
            System.out.printf(absentFormat(date));
        }
        System.out.println();
    }

    private String AttendanceFormat(LocalDateTime attendanceTime) {

        AttendanceResult attendanceResult = AttendanceResult.getAttendanceResult(attendanceTime);
        return String.format("%s (%s)", DATE_TIME_FORMAT.format(attendanceTime), attendanceResult.getName());
    }

    private String absentFormat(LocalDate date) {
        return String.format("%s --:-- (결석)", DATE_FORMAT.format(date));
    }
}
