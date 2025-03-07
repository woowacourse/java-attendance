import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.Attendance;
import domain.AttendanceBook;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceCheckTest {

    private AttendanceBook attendanceBook;

    @BeforeEach
    void setUp() {
        attendanceBook = new AttendanceBook();

        String name = "빙봉";
        LocalDate initialDate = LocalDate.of(2024, 12, 13);
        LocalTime initialTime = LocalTime.of(12, 59);
        attendanceBook.addCrew(name, initialDate, initialTime);
    }

    @DisplayName("등록된 닉네임을 입력한 경우 예외가 발생하지 않는다.")
    @Test
    void should_NotThrowException_When_ValidNameIsGiven() {
        String name = "빙봉";
        LocalDate date = LocalDate.of(2024, 12, 16);
        LocalTime time = LocalTime.of(13, 0);

        assertThatCode(() -> attendanceBook.attendCrew(name, date, time))
                .doesNotThrowAnyException();
    }

    @DisplayName("등록되지 않은 닉네임을 입력한 경우 예외를 발생한다.")
    @Test
    void should_ThrowException_When_InvalidNameIsGiven() {
        String name = "하루";
        LocalDate date = LocalDate.of(2024, 12, 13);
        LocalTime time = LocalTime.of(13, 0);

        assertThatThrownBy(() -> attendanceBook.attendCrew(name, date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContainingAll("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @DisplayName("출석 날짜와 시간을 기반으로 출석 기록을 저장한다.")
    @Test
    void should_StoreAttendance_When_GivenDateAndTime() {
        String name = "빙봉";
        LocalDate date = LocalDate.of(2024, 12, 16);
        LocalTime time = LocalTime.of(13, 0);
        Attendance attendance = new Attendance(date, time);

        assertThat(attendanceBook.attendCrew(name, date, time)).isEqualTo(attendance);
    }

    @DisplayName("이미 출석한 경우 수정 기능 안내 예외를 발생한다.")
    @Test
    void should_ThrowException_When_AttendanceIsAlreadyMarked() {
        String name = "빙봉";
        LocalDate date = LocalDate.of(2024, 12, 13);
        LocalTime time = LocalTime.of(13, 0);

        assertThatThrownBy(() -> attendanceBook.attendCrew(name, date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContainingAll("[ERROR] 이미 출석한 경우 수정 기능을 사용하세요.");
    }
}
