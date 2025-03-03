package console;

import crew.Crew;
import history.AttendanceHistory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import type.AttendanceType;
import type.AttendanceTypeCount;
import type.PenaltyResultOfCrew;
import type.PenaltyType;

public class OutputView {
    public static void printRegisteredHistory(AttendanceHistory attendanceHistory) {
        LocalDateTime attendAt = attendanceHistory.getAttendAt();
        String parsedAttendAt = Parser.parseDateTimeToString(attendAt);
        String parsedAttendanceType = Parser.parseAttendanceType(
                AttendanceType.findAttendanceTypeByDateTime(attendAt));

        System.out.printf("%s (%s)%n", parsedAttendAt, parsedAttendanceType);
    }

    public static void printAttendanceHistories(Crew crew, Map<LocalDateTime, AttendanceType> historiesOfCrew) {
        System.out.printf("이번 달 %s의 출석 기록입니다.%n%n", crew.getName());

        List<Entry<LocalDateTime, AttendanceType>> sortedHistories = new ArrayList<>(historiesOfCrew.entrySet());
        sortedHistories.sort(Map.Entry.comparingByKey());

        sortedHistories.forEach(entry -> {
            AttendanceType attendanceType = entry.getValue();

            String attendAt = Parser.parseDateTimeToString(entry.getKey());
            if (attendanceType.equals(AttendanceType.NO_DATA)) {
                String parseDate = Parser.parseDateToString(entry.getKey().toLocalDate());
                attendAt = parseDate + " --:--";

            }

            String parseAttendanceType = Parser.parseAttendanceType(attendanceType);
            System.out.printf("%s (%s)%n", attendAt, parseAttendanceType);
        });

        System.out.println();
    }

    public static void printPenaltyResultOfCrew(PenaltyResultOfCrew penaltyResultOfCrew) {
        List<AttendanceType> attendanceTypeOrder = List.of(AttendanceType.PRESENT, AttendanceType.LATE,
                AttendanceType.ABSENCE);

        attendanceTypeOrder.forEach(attendanceType -> {
            System.out.printf("%s: %d회%n", Parser.parseAttendanceType(attendanceType),
                    penaltyResultOfCrew.attendanceTypeCount().getCountByType(attendanceType));
        });

        System.out.println();

        PenaltyType penaltyType = penaltyResultOfCrew.penaltyType();
        if (penaltyType.isAtExpulsionCandidateState()) {
            String parsePenaltyType = Parser.parsePenaltyType(penaltyType);
            System.out.printf("%s 대상자입니다.%n%n", parsePenaltyType);
        }

    }

    public static void printExpulsionCandidates(List<PenaltyResultOfCrew> expulsionCandidates) {
        System.out.println("제적 위험자 조회 결과");

        expulsionCandidates.forEach(expulsionResult -> {
            Crew crew = expulsionResult.crew();
            AttendanceTypeCount attendanceTypeCount = expulsionResult.attendanceTypeCount();

            String parseExpulsionCandidate = Parser.parseExpulsionCandidate(crew, attendanceTypeCount);
            System.out.println(parseExpulsionCandidate);
        });

        System.out.println();
    }

    public static void printUpdatedHistory(AttendanceHistory oldHistory, AttendanceHistory newHistory) {
        LocalDateTime oldAttendAt = oldHistory.getAttendAt();

        String parseOldDateTime = Parser.parseDateTimeToString(oldAttendAt);
        AttendanceType oldAttendanceType = AttendanceType.findAttendanceTypeByDateTime(oldAttendAt);
        String parseOldAttendanceType = Parser.parseAttendanceType(oldAttendanceType);

        LocalDateTime newAttendAt = newHistory.getAttendAt();

        String parseNewDateTime = Parser.parseTimeToString(newAttendAt.toLocalTime());
        AttendanceType newAttendanceType = AttendanceType.findAttendanceTypeByDateTime(newAttendAt);
        String parseNewAttendanceType = Parser.parseAttendanceType(newAttendanceType);

        System.out.printf("%s (%s) -> %s (%s) 수정 완료!%n", parseOldDateTime, parseOldAttendanceType, parseNewDateTime,
                parseNewAttendanceType);
    }

    public static void printErrorMessage(String message) {
        System.out.printf("[ERROR] %s%n", message);
    }
}
