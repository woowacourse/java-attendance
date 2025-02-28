package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

class AttendanceBookTest {

    @Test
    @DisplayName("TreeSet 자료형에 맞게 자동 sort 되어 있는지 테스트")
    void addSortFunction() {

        // given
        final AttendanceDateTime attendanceDateTime1 = AttendanceDateTime.of("2025-02-27 10:00");
        final AttendanceDateTime attendanceDateTime2 = AttendanceDateTime.of("2025-02-28 10:00");

        final Attendance attendance1 = Attendance.of(attendanceDateTime1);
        final Attendance attendance2 = Attendance.of(attendanceDateTime2);

        // when
        final TreeSet<Attendance> attendances = new TreeSet<>();
        attendances.add(attendance2);
        attendances.add(attendance1);
        final AttendanceBook attendanceBook = new AttendanceBook(attendances);
        final TreeSet<Attendance> result = attendanceBook.getAttendances();

        // then
        Assertions.assertThat(result).containsExactly(attendance1, attendance2);
    }
}