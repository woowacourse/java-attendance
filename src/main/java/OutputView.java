import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class OutputView {
    public static void printAttendanceHistory(AttendanceHistory attendanceHistory) {
        LocalDateTime attendAt = attendanceHistory.getAttendAt();
        String parsedAttendAt = InputParser.parseDateTimeToString(attendAt);
        String parsedAttendanceType = InputParser.parseAttendanceType(
                AttendanceType.findAttendanceTypeByDateTime(attendAt));

        System.out.printf("%s (%s)%n", parsedAttendAt, parsedAttendanceType);
    }

    public static void printAttendanceHistories(Crew crew, Map<LocalDateTime, AttendanceType> historiesOfCrew) {
        System.out.printf("이번 달 %s의 출석 기록입니다.%n%n", crew.getName());

        List<Entry<LocalDateTime, AttendanceType>> sortedHistories = new ArrayList<>(historiesOfCrew.entrySet());
        sortedHistories.sort(Map.Entry.comparingByKey());
        sortedHistories.forEach(entry -> {
            String attendAt = InputParser.parseDateTimeToString(entry.getKey());
            String attendanceType = InputParser.parseAttendanceType(entry.getValue());
            System.out.printf("%s (%s)%n", attendAt, attendanceType);
        });

        System.out.println();
    }

    public static void printPenaltyResultOfCrew(PenaltyResultOfCrew penaltyResultOfCrew) {
        List<AttendanceType> attendanceTypeOrder = List.of(AttendanceType.PRESENT, AttendanceType.LATE,
                AttendanceType.ABSENCE);

        attendanceTypeOrder.forEach(attendanceType -> {
            System.out.printf("%s: %d회%n", InputParser.parseAttendanceType(AttendanceType.PRESENT),
                    penaltyResultOfCrew.attendanceTypeCount().getCountByType(attendanceType));
        });

        System.out.println();

        PenaltyType penaltyType = penaltyResultOfCrew.penaltyType();
        if (penaltyType.isAtExpulsionCandidateState()) {
            String parsePenaltyType = InputParser.parsePenaltyType(penaltyType);
            System.out.printf("%s 대상자입니다.%n%n", parsePenaltyType);
        }

    }

    public static void printExpulsionCandidates(List<PenaltyResultOfCrew> expulsionCandidates) {
        System.out.println("제적 위험자 조회 결과");

        expulsionCandidates.forEach(expulsionResult -> {
            Crew crew = expulsionResult.crew();
            AttendanceTypeCount attendanceTypeCount = expulsionResult.attendanceTypeCount();

            String parseExpulsionCandidate = InputParser.parseExpulsionCandidate(crew, attendanceTypeCount);
            System.out.println(parseExpulsionCandidate);
        });

        System.out.println();
    }

    public static void printUpdateHistory(AttendanceHistory oldHistory, AttendanceHistory newHistory) {
        LocalDateTime oldAttendAt = oldHistory.getAttendAt();

        String parseOldDateTime = InputParser.parseDateTimeToString(oldAttendAt);
        AttendanceType oldAttendanceType = AttendanceType.findAttendanceTypeByDateTime(oldAttendAt);
        String parseOldAttendanceType = InputParser.parseAttendanceType(oldAttendanceType);

        LocalDateTime newAttendAt = newHistory.getAttendAt();

        String parseNewDateTime = InputParser.parseTimeToString(newAttendAt.toLocalTime());
        AttendanceType newAttendanceType = AttendanceType.findAttendanceTypeByDateTime(newAttendAt);
        String parseNewAttendanceType = InputParser.parseAttendanceType(newAttendanceType);

        System.out.printf("%s (%s) -> %s (%s) 수정 완료!%n", parseOldDateTime, parseOldAttendanceType, parseNewDateTime,
                parseNewAttendanceType);
    }

    public static void printErrorMessage(String message) {
        System.out.printf("[ERROR] %s%n", message);
    }
}
