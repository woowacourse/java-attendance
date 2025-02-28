package attendance.repository;

import attendance.domain.AttendanceBook;
import java.util.HashMap;
import java.util.Map;

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
}
