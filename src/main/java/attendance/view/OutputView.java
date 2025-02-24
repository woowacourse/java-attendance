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
import java.util.Locale;
import java.util.stream.Collectors;

public class OutputView {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 EEE요일", Locale.KOREAN);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN);
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 EEE요일 HH:mm", Locale.KOREAN);
    public static final DateTimeFormatter ABSENCE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 EEE요일 --:--", Locale.KOREAN);

    private OutputView() {
    }

    public static void printAttendanceResult(Attendance attendance) {
        LocalDateTime attendedTime = attendance.getAttendedTime();
        AttendanceStatus attendanceStatus = attendance.getStatus();

        String attendanceDate = attendedTime.format(DATE_TIME_FORMATTER);
        System.out.printf("%n%s (%s)%n", attendanceDate, attendanceStatus.getName());
    }

    public static void printModifyingResult(Attendance before, Attendance after) {
        String date = after.getAttendedTime().format(DATE_FORMATTER);

        String beforeTime = before.getAttendedTime().format(TIME_FORMATTER);
        String beforeStatus = before.getStatus().getName();

        String afterTime = after.getAttendedTime().toLocalTime().format(TIME_FORMATTER);
        String afterStatus = after.getStatus().getName();

        System.out.printf("%n%s %s (%s) -> %s (%s) 수정 완료!%n",
            date, beforeTime, beforeStatus, afterTime, afterStatus);
    }

    public static void printAttendanceRecordAndPenalty(List<Attendance> attendancesOfCrew) {
        System.out.printf("%n이번 달 빙티의 출석 기록입니다.%n");
        for (Attendance attendance : attendancesOfCrew) {
            System.out.println(getFormattedAttendanceRecord(attendance));
        }

        int attendanceCount = countAttendanceStatus(attendancesOfCrew, AttendanceStatus.CHECKIN);
        int lateCount = countAttendanceStatus(attendancesOfCrew, AttendanceStatus.LATE);
        int absenceCount = countAttendanceStatus(attendancesOfCrew, AttendanceStatus.ABSENCE);
        printPenaltyOfAttendanceStatus(attendanceCount, lateCount, absenceCount);
    }

    private static void printPenaltyOfAttendanceStatus(int attendanceCount, int lateCount,
        int absenceCount) {
        System.out.printf("%n출석: %d회%n", attendanceCount);
        System.out.printf("지각: %d회%n", lateCount);
        System.out.printf("결석: %d회%n", absenceCount);

        Penalty penalty = Penalty.determine(absenceCount, lateCount);
        if (penalty != Penalty.NONE) {
            System.out.printf("%n%s 대상자입니다.%n", penalty.getName());
        }
    }

    private static int countAttendanceStatus(List<Attendance> attendances,
        AttendanceStatus attendanceStatus) {
        return Math.toIntExact(
            attendances.stream()
                .filter(attendance -> attendance.getStatus() == attendanceStatus)
                .count()
        );
    }

    private static String getFormattedAttendanceRecord(Attendance attendance) {
        String dateTime = attendance.getAttendedTime().format(DATE_TIME_FORMATTER);
        String status = String.format(" (%s)", attendance.getStatus().getName());
        if (attendance.getStatus() == AttendanceStatus.ABSENCE) {
            dateTime = attendance.getAttendedTime().format(ABSENCE_FORMATTER);
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
                List<Attendance> attendanceOfCrew = attendances.getAttendances(crew, LocalDate.now());
                int absenceCount = countAttendanceStatus(attendanceOfCrew, AttendanceStatus.ABSENCE);
                int lateCount = countAttendanceStatus(attendanceOfCrew, AttendanceStatus.LATE);
                Penalty penalty = Penalty.determine(absenceCount, lateCount);
                return new PenaltyResult(crew.getNickName(), absenceCount, lateCount, penalty);
            })
            .collect(Collectors.toList());
    }

    private static void printPenaltyResult(PenaltyResult penaltyResult) {
        if (penaltyResult.penalty != Penalty.NONE) {
            System.out.printf("- %s: %s %d회, %s %d회 (%s)", penaltyResult.nickName(),
                AttendanceStatus.ABSENCE.getName(),
                penaltyResult.absenceCount, AttendanceStatus.LATE.getName(),
                penaltyResult.lateCount, penaltyResult.penalty.getName());
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
