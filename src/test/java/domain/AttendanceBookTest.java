package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {
    AttendanceBook attendanceBook;

    @BeforeEach
    void setUp() {
        Attendance attendance1 = new Attendance(LocalDate.of(2024, 12, 2), LocalTime.of(9, 55));
        Attendance attendance2 = new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0));
        Attendance attendance3 = new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 10));
        Attendance attendance4 = new Attendance(LocalDate.of(2024, 12, 5), LocalTime.of(10, 55));
        attendanceBook = new AttendanceBook(List.of(attendance1, attendance2, attendance3, attendance4));
    }

    @Test
    void 이미_출석한_경우_다시_출석할_수_없다() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 5);

        // when & then
        Assertions.assertThatThrownBy(() -> {
                    attendanceBook.validateDuplicateCheckIn(localDate);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 출석한 크루입니다.");
    }

    @Test
    void 주말_및_공휴일에는_출석할_수_없다() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 1);

        // when & then
        Assertions.assertThatThrownBy(() -> {
                    attendanceBook.validateWeekDay(localDate);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말 및 공휴일에는 출석할 수 없습니다.");
    }

    @Test
    void 오늘_이후의_날짜는_출석할_수_없다() {
        // given
        LocalDate localDate = LocalDate.of(2025, 7, 25);

        // when & then
        Assertions.assertThatThrownBy(() -> {
                    attendanceBook.validateAfterToday(localDate);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수정할 수 없는 날짜입니다.");
    }

}
