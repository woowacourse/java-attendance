package attendance.view;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import attendance.domain.Attendances;
import attendance.domain.Crew;
import attendance.domain.Penalty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OutputView {

    public static final String DATE_FORMATTER = "MM월 dd일 EEE요일";
    private static final String TIME_FORMATTER = "HH:mm";
    private static final String ABSENCE_FORMATTER = "MM월 dd일 EEE요일 --:--";

    private OutputView() {
    }

    public static void printAttendanceResult(Attendance attendance) {
        LocalDateTime attendanceDateTime = attendance.getDateTime();
        AttendanceStatus attendanceStatus = attendance.getStatus();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
            DATE_FORMATTER + " " + TIME_FORMATTER);
        String attendanceDate = attendanceDateTime.format(dateTimeFormatter);
        System.out.println(
            System.lineSeparator() + attendanceDate + " (" + attendanceStatus.getKoreanName()
                + ")");
    }

    public static void printModifyingResult(Attendance previousAttendance, Attendance attendance) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMATTER);

        String date = attendance.getDateTime().format(dateFormatter);
        String beforeTime = previousAttendance.getDateTime().format(timeFormatter);
        String beforeStatus = previousAttendance.getStatus().getKoreanName();
        String afterTime = attendance.getDateTime().toLocalTime().format(timeFormatter);
        String afterStatus = attendance.getStatus().getKoreanName();
        System.out.printf("%n%s %s (%s) -> %s (%s) 수정 완료!%n",
            date, beforeTime, beforeStatus, afterTime, afterStatus);
    }

    public static void printAttendanceRecordAndPenalty(List<Attendance> attendances) {
        System.out.printf("%n이번 달 빙티의 출석 기록입니다.%n");
        for (Attendance attendance : attendances) {
            System.out.println(getFormattedAttendanceRecord(attendance));
        }

        int attendanceCount = countAttendanceStatus(attendances, AttendanceStatus.CHECKIN);
        int lateCount = countAttendanceStatus(attendances, AttendanceStatus.LATE);
        int absenceCount = countAttendanceStatus(attendances, AttendanceStatus.ABSENCE);
        printPenaltyOfAttendanceStatus(attendanceCount, lateCount, absenceCount);
    }

    private static void printPenaltyOfAttendanceStatus(int attendanceCount, int lateCount,
        int absenceCount) {
        System.out.printf("%n출석: %d회%n", attendanceCount);
        System.out.printf("지각: %d회%n", lateCount);
        System.out.printf("결석: %d회%n", absenceCount);

        Penalty penalty = Penalty.determine(absenceCount, lateCount);
        if (penalty != Penalty.NONE) {
            System.out.printf("%n%s 대상자입니다.%n", penalty.getKoreanName());
        }
    }

    private static int countAttendanceStatus(List<Attendance> attendances,
        AttendanceStatus attendanceStatus) {
        return Math.toIntExact(attendances.stream()
            .filter(attendance -> attendance.getStatus() == attendanceStatus).count());
    }

    private static String getFormattedAttendanceRecord(Attendance attendance) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
            DATE_FORMATTER + " " + TIME_FORMATTER);
        DateTimeFormatter absenceFormatter = DateTimeFormatter.ofPattern(ABSENCE_FORMATTER);

        String dateTime = attendance.getDateTime().format(dateTimeFormatter);
        String status = " (" + attendance.getStatus().getKoreanName() + ")";
        if (attendance.getStatus() == AttendanceStatus.ABSENCE) {
            dateTime = attendance.getDateTime().format(absenceFormatter);
        }
        return dateTime + status;
    }

    public static void printPenaltyOfCrews(List<Crew> crews, Attendances attendances) {
        System.out.println("제적 위험자 조회 결과");
        List<PenaltyResult> penaltyResults = getPenaltyResults(crews, attendances);
        Collections.sort(penaltyResults);
        for (PenaltyResult penaltyResult : penaltyResults) {
            printPenaltyResult(penaltyResult);
        }
    }

    private static List<PenaltyResult> getPenaltyResults(List<Crew> crews,
        Attendances attendances) {
        return crews.stream()
            .map(crew -> {
                List<Attendance> attendanceOfCrew = attendances.getByCrew(crew,
                    LocalDate.of(2024, 12, 14));
                int absenceCount = countAttendanceStatus(attendanceOfCrew,
                    AttendanceStatus.ABSENCE);
                int lateCount = countAttendanceStatus(attendanceOfCrew, AttendanceStatus.LATE);
                Penalty penalty = Penalty.determine(absenceCount, lateCount);
                return new PenaltyResult(crew.getName(), absenceCount, lateCount, penalty);
            }).collect(Collectors.toList());
    }

    private static void printPenaltyResult(PenaltyResult penaltyResult) {
        if (penaltyResult.penalty != Penalty.NONE) {
            System.out.printf("- %s: %s %d회, %s %d회 (%s)%n", penaltyResult.nickName(),
                AttendanceStatus.ABSENCE.getKoreanName(),
                penaltyResult.absenceCount, AttendanceStatus.LATE.getKoreanName(),
                penaltyResult.lateCount, penaltyResult.penalty.getKoreanName());
            System.out.println();
        }
    }

    public record PenaltyResult(String nickName, int absenceCount, int lateCount,
                                Penalty penalty) implements Comparable<PenaltyResult> {

        @Override
        public int compareTo(PenaltyResult result) {
            if (this.penalty.ordinal() < result.penalty.ordinal()) {
                return -1;
            }
            if (this.penalty.ordinal() == result.penalty.ordinal()) {
                return this.nickName.compareTo(result.nickName);
            }
            return 1;
        }
    }
}
