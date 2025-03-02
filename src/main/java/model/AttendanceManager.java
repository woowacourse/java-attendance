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

    public Crew findCrewByNickname(final Nickname nickname) {
        return attendanceBooks.keySet().stream()
                .filter(crew -> crew.getNickname().equals(nickname))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 닉네임입니다."));
    }

    public void saveAttendance(final Crew crew, final Attendance attendance) {
        final AttendanceBook attendanceBook = attendanceBooks.get(crew);
        if (attendanceBook.isSameByDate(attendance)) {
            throw new IllegalArgumentException("이미 출석 기록이 존재합니다. 수정 기능을 이용해주세요.");
        }
        attendanceBook.add(attendance);
    }

    public AttendanceBook findAttendanceBookByCrew(final Crew crew) {
        return attendanceBooks.get(crew);
    }

    public void updateAttendanceRecord(final int toDayOfMonth) {
        for (final Crew crew : attendanceBooks.keySet()) {
            final AttendanceBook attendanceBook = attendanceBooks.get(crew);
            final int fromDayOfMonth = attendanceBook.getLastlyAttendance();
            attendanceBook.updateRecordFromTo(fromDayOfMonth, toDayOfMonth);
        }
    }
}
