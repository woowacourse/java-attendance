package domain;

import exception.AppException;

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
}
