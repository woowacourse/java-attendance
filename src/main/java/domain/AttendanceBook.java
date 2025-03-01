package domain;

import exception.AppException;

import java.util.Map;

public class AttendanceBook {
    Map<Crew, CheckInHistory> book;

    private AttendanceBook(Map<Crew, CheckInHistory> book) {
        this.book = book;
    }

    public static AttendanceBook of(Map<Crew, CheckInHistory> book) {
        return new AttendanceBook(book);
    }

    public CheckInHistory findByName(String name) {
        Crew targetCrew = Crew.of(name);
        CheckInHistory foundHistory = book.get(targetCrew);
        if (foundHistory == null) {
            throw new AppException("해당 이름이 출석부에 존재하지 않습니다.");
        }
        return foundHistory;
    }
}
