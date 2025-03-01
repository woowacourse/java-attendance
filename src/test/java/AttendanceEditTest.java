import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.Attendance;
import domain.AttendanceBook;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceEditTest {

    AttendanceBook attendanceBook = new AttendanceBook();

    @BeforeEach
    void initial() {
        String name = "빙봉";
        LocalDate initialDate = LocalDate.of(2024, 12, 13);
        LocalTime initialTime = LocalTime.of(12, 59);

        attendanceBook.addCrew(name, initialDate, initialTime);
    }

    @DisplayName("수정 날짜와 시간을 기반으로 출석 기록을 변경한다")
    @Test
    void should_UpdateAttendanceRecord_When_GivenModifiedDateTime() {
        String name = "빙봉";
        LocalDate date = LocalDate.of(2024, 12, 13);
        LocalTime initialTime = LocalTime.of(12, 59);
        Attendance expectedInitalAttendance = new Attendance(date, initialTime);
        LocalTime editTime = LocalTime.of(13, 6);
        Attendance expectedUpdatedAttendance = new Attendance(date, editTime);

        assertThat(expectedInitalAttendance).isEqualTo(attendanceBook.findAttendance(name, date));
        assertThat(expectedUpdatedAttendance).isEqualTo(attendanceBook.editCrew(name, date, editTime));
    }

    @DisplayName("등록되지 않은 닉네임을 입력한 경우 예외를 발생한다.")
    @Test
    void should_ThrowException_When_InvalidNameIsGiven() {
        String name = "하루";
        LocalDate date = LocalDate.of(2024, 12, 13);
        LocalTime time = LocalTime.of(13, 0);

        assertThatThrownBy(() -> attendanceBook.editCrew(name, date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContainingAll("[ERROR] 등록되지 않은 닉네임입니다.");
    }
}
