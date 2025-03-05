package domain;

import exception.AppException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AttendanceBook {
    private final Map<Crew, CheckInHistory> book;

    private AttendanceBook(Map<Crew, CheckInHistory> book) {
        this.book = book;
    }

    public static AttendanceBook of(Map<Crew, CheckInHistory> book) {
        return new AttendanceBook(book);
    }

    public CheckInHistory findHistoryByCrew(Crew crew) {
        return Optional.ofNullable(book.get(crew))
                .orElseThrow(() -> new AppException("해당 이름이 출석부에 존재하지 않습니다."));
    }

    public List<DangerCrew> findDangerCrews(LocalDate now) {
        return book.entrySet().stream()
                .filter(entry -> entry.getValue().getPenaltyStatus(now) != PenaltyStatus.NONE)
                .map(entry ->
                        DangerCrew.of(entry.getKey(), entry.getValue().countLate(now), entry.getValue().countAbsence(now)))
                .toList();
    }

    public void checkIn(CheckInHistory checkInHistory, CheckInDate checkInDate, CheckInTime checkInTime) {
        checkInHistory.checkIn(checkInDate, checkInTime);
    }

    public void modifyCheckInTime(CheckInHistory checkInHistory, CheckInDate checkInDate, CheckInTime checkInTime) {
        checkInHistory.modifyCheckInTime(checkInDate, checkInTime);
    }
}
