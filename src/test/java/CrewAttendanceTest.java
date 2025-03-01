import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import model.Attendance;
import model.AttendanceBook;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewAttendanceTest {

    @DisplayName("닉네임을 통해 해당 크루의 출석 기록을 가져올 수 있다.")
    @Test
    void nickname_to_attendances() {
        // given
        Attendance attendRecord1 = new Attendance("율무", LocalDate.of(2024, 12, 3), LocalTime.of(10, 0));
        Attendance attendRecord2 = new Attendance("율무", LocalDate.of(2024, 12, 4), LocalTime.of(10, 4));
        Attendance attendRecord3 = new Attendance("열무", LocalDate.of(2024, 12, 4), LocalTime.of(9, 32));
        AttendanceBook attendanceBook = new AttendanceBook(List.of(attendRecord1, attendRecord2, attendRecord3));
        final var nickname = "율무";

        // when
        final var crewAttendances = attendanceBook.findCrewAttendance(nickname);

        // then
        Assertions.assertThat(crewAttendances)
                .containsExactly(attendRecord1, attendRecord2);
    }

    @DisplayName("없는 닉네임 입력 시 예외를 발생한다.")
    @Test
    void not_exist_nickname_exception() {
        // given
        Attendance attendRecord1 = new Attendance("율무", LocalDate.of(2024, 12, 3), LocalTime.of(10, 0));
        Attendance attendRecord2 = new Attendance("율무", LocalDate.of(2024, 12, 4), LocalTime.of(10, 4));
        AttendanceBook attendanceBook = new AttendanceBook(List.of(attendRecord1, attendRecord2));
        final var nickname = "열무";

        // when
        // then
        Assertions.assertThatThrownBy(() -> attendanceBook.findCrewAttendance(nickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @DisplayName("닉네임을 통해 크루의 결석 횟수를 계산할 수 있다.")
    @Test
    void calculate_absent_count_by_nickname() {
        // given
        Attendance attendRecord1 = new Attendance("율무", LocalDate.of(2024, 12, 3), LocalTime.of(10, 33));
        Attendance attendRecord2 = new Attendance("율무", LocalDate.of(2024, 12, 4), LocalTime.of(10, 31));
        AttendanceBook attendanceBook = new AttendanceBook(List.of(attendRecord1, attendRecord2));
        final var nickname = "율무";

        // when
        final var absentCount = attendanceBook.calculateAbsentCountByNickname(nickname);

        // then
        Assertions.assertThat(absentCount)
                .isEqualTo(2);
    }
}
