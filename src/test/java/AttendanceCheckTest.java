import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceCheckTest {

    @DisplayName("닉네임, 출석 시간을 입력하면 출석할 수 있다.")
    @Test
    void attend() {
        // given
        AttendanceBook attendanceBook = new AttendanceBook(new ArrayList<>());
        String nickname = "율무";
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.of(10, 0);

        // when
        Attendance attendance = attendanceBook.check(nickname, date, time);

        // then
        Assertions.assertThat(attendance)
                .isEqualTo(new Attendance(nickname, date, time));
    }

    @DisplayName("이미 출석했으면 출석할 수 없다.")
    @Test
    void already_attend() {
        // given
        List<Attendance> attendances = List.of(
                new Attendance("율무", LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)),
                new Attendance("율무", LocalDate.of(2024, 12, 4), LocalTime.of(10, 4))
        );
        AttendanceBook attendanceBook = new AttendanceBook(attendances);
        String nickname = "율무";
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.of(10, 0);

        // when
        // then
        Assertions.assertThatThrownBy(() -> attendanceBook.check(nickname, date, time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("출석하면 출석 기록을 찾을 수 있다.")
    @Test
    void find_attendance() {
        // given
        Attendance attendance = new Attendance("율무", LocalDate.of(2024, 12, 3), LocalTime.of(10, 0));
        AttendanceBook attendanceBook = new AttendanceBook(List.of(attendance));

        // when
        Attendance find = attendanceBook.findAttendance("율무", LocalDate.of(2024, 12, 3), LocalTime.of(10, 0));

        // then
        Assertions.assertThat(find)
                .isEqualTo(attendance);
    }

    @DisplayName("없는 출석 기록을 찾을 수 없다.")
    @Test
    void find_attendance_exception() {
        // given
        Attendance attendance = new Attendance("율무", LocalDate.of(2024, 12, 4), LocalTime.of(10, 3));
        AttendanceBook attendanceBook = new AttendanceBook(List.of(attendance));

        // when
        // then
        Assertions.assertThatThrownBy(
                        () -> attendanceBook.findAttendance("율무", LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
