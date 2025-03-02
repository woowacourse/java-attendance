package domain;

import exception.AppException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AttendanceBook {
    Map<Crew, CheckInHistory> book;

    private AttendanceBook(Map<Crew, CheckInHistory> book) {
        this.book = book;
    }

    public static AttendanceBook of(Map<Crew, CheckInHistory> book) {
        return new AttendanceBook(book);
    }

    public CheckInHistory findHistoryByName(String name) {
        return Optional.ofNullable(book.get(Crew.of(name)))
                .orElseThrow(() -> new AppException("해당 이름이 출석부에 존재하지 않습니다."));
    }

    public List<DangerCrew> findDangerCrews(LocalDate now) {
        List<DangerCrew> dangerCrews = new ArrayList<>();
        for (Map.Entry<Crew, CheckInHistory> entry : book.entrySet()) {
            if (entry.getValue().getPenaltyStatus(now) != PenaltyStatus.NONE) {
                DangerCrew.of(entry.getKey(), entry.getValue().countLate(now), entry.getValue().countAbsence(now));
            }
        }
        return dangerCrews;
    }
}
