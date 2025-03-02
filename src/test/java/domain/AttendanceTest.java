package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    @Test
    void 불변_객체로_변환시_수정할_수_없다() {
        Attendance attendance = new Attendance(new Day(LocalDate.of(2025, 2, 28)), LocalTime.of(10, 0));
        Attendance immutableAttendance = attendance.toImmutable();

        assertThatThrownBy(() -> immutableAttendance.modifyTimeTo(LocalTime.of(10, 6)))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @Test
    void 같은_Day_멤버를_가지면_같은_객체로_취급한다() {
        Day day = new Day(LocalDate.of(2025, 2, 28));
        Attendance attendance1 = new Attendance(day, LocalTime.of(10, 0));

        Attendance attendance2 = new Attendance(day, LocalTime.of(10, 5));

        Attendance attendance3 = new Attendance(new Day(LocalDate.of(2025, 2, 27)), LocalTime.of(10, 5));

        assertThat(attendance1).isEqualTo(attendance2);
        assertThat(attendance1).isNotEqualTo(attendance3);
    }

    @Test
    void 같은_Day_멤버를_가지면_같은_해쉬코드를_반환한다() {
        Day day = new Day(LocalDate.of(2025, 2, 28));
        Attendance attendance1 = new Attendance(day, LocalTime.of(10, 0));

        Attendance attendance2 = new Attendance(day, LocalTime.of(10, 5));

        Attendance attendance3 = new Attendance(new Day(LocalDate.of(2025, 2, 27)), LocalTime.of(10, 5));

        assertThat(attendance1.hashCode()).isEqualTo(attendance2.hashCode());
        assertThat(attendance1.hashCode()).isNotEqualTo(attendance3.hashCode());
    }
}