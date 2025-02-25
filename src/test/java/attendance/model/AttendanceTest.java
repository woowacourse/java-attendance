package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("출석 테스트")
class AttendanceTest {

    @DisplayName("주말인 경우 출석을 생성할때 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenWeekendAttendance() {
        Crew crew = new Crew("포비");
        LocalDateTime sunday = LocalDateTime.parse("2024-12-01 11:01", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertThatThrownBy(() -> new Attendance(crew, sunday))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말인 경우 출석할 수 없습니다.");
    }

    @DisplayName("법정 공휴일인 경우 출석을 생성할때 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenHolidayAttendance() {
        Crew crew = new Crew("포비");
        LocalDateTime christmas = LocalDateTime.parse("2024-12-25 11:01",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertThatThrownBy(() -> new Attendance(crew, christmas))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("법정 공휴일에는 출석할 수 없습니다.");
    }

    @DisplayName("해당 출석이 이미 출석했는지 알 수 있다.")
    @Test
    void isAlreadyAttendance() {
        Attendance attendance2 = new Attendance(new Crew("포비"), LocalDateTime.of(2024, 12, 13, 12, 1));
        Attendance attendance1 = new Attendance(new Crew("포비"), LocalDateTime.of(2024, 12, 13, 11, 1));

        boolean result = attendance1.isAlreadyAttendance(attendance2);

        assertThat(result).isTrue();
    }

    @DisplayName("크루와 날짜를 통해 이미 출석했는지 알 수 있다.")
    @Test
    void isAlreadyAttendanceWithCrewAndDate() {
        Attendance attendance1 = new Attendance(new Crew("포비"), LocalDateTime.of(2024, 12, 13, 11, 1));

        boolean result = attendance1.isAlreadyAttendance(new Crew("포비"), LocalDate.of(2024, 12, 13));

        assertThat(result).isTrue();
    }

    @DisplayName("출석 타입을 알 수 있다.")
    @Test
    void getAttendanceType() {
        //given
        Attendance attendance = new Attendance(new Crew("포비"), LocalDateTime.of(2024, 12, 13, 11, 1));

        //when
        AttendanceType attendanceType = attendance.getAttendanceType();

        //then
        assertThat(attendanceType).isEqualTo(AttendanceType.ABSENCE);
    }

    @DisplayName("동일한 출석인지 알 수 있다.")
    @Test
    void test() {
        //given
        Attendance attendance1 = new Attendance(new Crew("포비"), LocalDateTime.of(2024, 12, 13, 12, 1));
        Attendance attendance2 = new Attendance(new Crew("포비"), LocalDateTime.of(2024, 12, 13, 12, 1));

        //when
        boolean result = attendance1.equals(attendance2);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("특정 일자보다 이후에 출석을 했는지 알 수 있다.")
    @Test
    void isAfter() {
        //given
        Attendance attendance1 = new Attendance(new Crew("포비"), LocalDateTime.of(2024, 12, 13, 12, 1));

        //when
        boolean result = attendance1.isAfter(LocalDate.of(2024, 12, 12));

        //then
        assertThat(result).isTrue();
    }
}
