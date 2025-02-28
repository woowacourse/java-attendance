package model;

import java.util.Map;

public class AttendanceManager {

    final private Map<Crew, AttendanceBook> attendanceBooks;

    public AttendanceManager(final Map<Crew, AttendanceBook> attendanceBookMap) {
        this.attendanceBooks = attendanceBookMap;
    }

    public Map<Crew, AttendanceBook> getAttendanceBooks() {
        return attendanceBooks;
    }

    public Crew findByNickname(final Nickname nickname) {
        return attendanceBooks.keySet().stream()
                .filter(crew -> crew.getNickname().equals(nickname))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 닉네임입니다."));
    }
}
