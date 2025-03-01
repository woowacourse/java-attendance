package attendance.repository;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceDate;
import attendance.domain.AttendancePenalty;
import attendance.domain.AttendanceStatus;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AttendanceBookRepository {

    private final Map<String, AttendanceBook> attendanceBooks;

    public AttendanceBookRepository(Map<String, AttendanceBook> attendanceBooks) {
        validateNotNull(attendanceBooks);
        this.attendanceBooks = new HashMap<>(attendanceBooks);
    }

    private void validateNotNull(final Map<String, AttendanceBook> attendanceBooks) {
        if (attendanceBooks == null) {
            throw new IllegalArgumentException("출석부 목록은 출석부들을 가지고 있어야 합니다.");
        }

        for (Map.Entry<String, AttendanceBook> entry : attendanceBooks.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                throw new IllegalArgumentException(
                    "출석부 목록은 크루의 닉네임과 출석부를 가지고 있어야 합니다.");
            }
        }
    }

    public static AttendanceBookRepository from(final AttendanceBookLoader attendanceBookLoader) {
        return new AttendanceBookRepository(
            attendanceBookLoader.loadAttendanceBooks());
    }

    public Optional<AttendanceBook> findByCrewNickname(String crewNickname) {
        return Optional.ofNullable(attendanceBooks.get(crewNickname));
    }

    public List<AttendanceBook> findAllPenaltyCrewUntilDateOrderByAbsenceCountAndCrewNickname(
        final AttendanceDate untilDate
    ) {
        return attendanceBooks.values()
            .stream()
            .filter(attendanceBook -> hasPenalty(attendanceBook, untilDate))
            .sorted((previous, next) ->
                compareByAbsenceCountAndCrewNickName(previous, next, untilDate))
            .toList();
    }

    private boolean hasPenalty(
        final AttendanceBook attendanceBook,
        final AttendanceDate untilDate
    ) {
        final Map<AttendanceStatus, Integer> statusCount = AttendanceStatus.from(
            attendanceBook.retrieveOrderByDateTimeUntilDate(untilDate));
        return AttendancePenalty.from(statusCount) != AttendancePenalty.NONE;
    }

    private int compareByAbsenceCountAndCrewNickName(
        final AttendanceBook previousAttendanceBook,
        final AttendanceBook nextAttendanceBook,
        final AttendanceDate untilDate
    ) {
        final int previousAbsenceCount = extractAbsenceCount(
            previousAttendanceBook, untilDate);
        final int nextAbsenceCount = extractAbsenceCount(
            nextAttendanceBook, untilDate);

        if (previousAbsenceCount != nextAbsenceCount) {
            return compareByAbsenceCount(previousAbsenceCount,
                nextAbsenceCount);
        }

        return compareByCrewNickname(
            previousAttendanceBook, nextAttendanceBook);
    }

    private int extractAbsenceCount(
        final AttendanceBook attendanceBook,
        final AttendanceDate untilDate
    ) {
        final Map<AttendanceStatus, Integer> statusCount = AttendanceStatus.from(
            attendanceBook.retrieveOrderByDateTimeUntilDate(untilDate)
        );
        
        return AttendancePenalty.calculateAbsenceCount(statusCount);
    }

    private int compareByAbsenceCount(
        final int previousAbsenceCount,
        final int nextAbsenceCount
    ) {
        return Integer.compare(nextAbsenceCount, previousAbsenceCount);
    }

    private int compareByCrewNickname(
        final AttendanceBook previousAttendanceBook,
        final AttendanceBook nextAttendanceBook
    ) {
        return previousAttendanceBook.getCrew()
            .getNickname()
            .compareTo(nextAttendanceBook.getCrew()
                .getNickname());
    }
}
