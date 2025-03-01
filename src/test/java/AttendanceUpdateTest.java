import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceUpdateTest {

    @DisplayName("출석 기록을 수정할 수 있다.")
    @Test
    void update() {
        // given
        AttendanceBook attendanceBook = new AttendanceBook(List.of(
                new Attendance("율무", LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)),
                new Attendance("율무", LocalDate.of(2024, 12, 4), LocalTime.of(10, 4))
        ));
        final var updateNickname = "율무";
        final var updateDate = LocalDate.of(2024, 12, 3);
        final var updateTime = LocalTime.of(9, 58);

        // when
        final var updateAttendance = attendanceBook.update(updateNickname, updateDate, updateTime);

        // then
        Assertions.assertThat(updateAttendance)
                .isEqualTo(attendanceBook.findAttendance(updateNickname, updateDate, updateTime));
    }

    @DisplayName("출석 기록이 없는 날은 수정할 수 없다.")
    @Test
    void update_none_attendance() {
        // given
        AttendanceBook attendanceBook = new AttendanceBook(List.of(
                new Attendance("율무", LocalDate.of(2024, 12, 4), LocalTime.of(10, 4))
        ));
        final var updateNickname = "율무";
        final var updateDate = LocalDate.of(2024, 12, 3);
        final var updateTime = LocalTime.of(9, 58);

        // when
        // then
        Assertions.assertThatThrownBy(() -> attendanceBook.update(updateNickname, updateDate, updateTime))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
