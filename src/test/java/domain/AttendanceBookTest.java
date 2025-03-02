package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {

    @Test
    void 이미_출석한_상태에서_출석하면_예외를_발생시킨다() {
        AttendanceBook attendanceBook = new AttendanceBook();
        Crew crew = new Crew("에드");
        LocalDate date = LocalDate.of(2025, 2, 28);
        Day day = new Day(date);
        LocalTime time = LocalTime.of(10, 0);
        Attendance attendance = new Attendance(day, time);

        attendanceBook.recordAttendance(crew, attendance);

        assertThatThrownBy(() -> attendanceBook.isAlreadyAttended(crew, date))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("[ERROR]");
    }


}