package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.model.Attendance;
import attendance.model.AttendanceDetail;
import attendance.model.Crew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @DisplayName("크루를 생성한다.")
    @Test
    void test_Crew() {
        String name = "멍구";
        Crew crew = new Crew(name);

        assertThat(crew).isNotNull();
    }

    @DisplayName("생성된 크루의 이름을 확인한다.")
    @Test
    void test_CrewName() {
        String name = "멍구";
        Crew crew = new Crew(name);

        assertThat(crew.getName()).isEqualTo(name);
    }

    @DisplayName("크루의 이름이 5자 이내가 아니라면 예외가 발생한다.")
    @Test
    void error_CrewName2() {
        String name = "123456";

        assertThatThrownBy(() -> new Crew(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("크루의 이름이 공백인 경우 예외가 발생한다.")
    @Test
    void error_CrewName3() {
        String name = " ";

        assertThatThrownBy(() -> new Crew(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("크루가 가지고 있는 기록을 확인한다.")
    @Test
    void test_checkHistory() {
        Crew crew = new Crew("멍구");
        crew.attend(new AttendanceDetail(LocalDateTime.of(2024, 12, 10, 10, 0)));

        assertThat(crew.getAttendanceHistory().getAttendanceCount()).isEqualTo(1);
    }

    @DisplayName("크루가 가지고 있는 기록을 수정한 후 반영되었는지 확인한다.")
    @Test
    void test_checkHistory2() {
        Crew crew = new Crew("멍구");
        crew.attend(new AttendanceDetail(LocalDateTime.of(2024, 12, 10, 10, 0)));

        crew.getAttendanceHistory().findAttendanceDetail(LocalDate.of(2024, 12, 10))
                .modify(LocalTime.of(10, 6));

        AttendanceDetail attendanceDetail = crew.getAttendanceHistory()
                .findAttendanceDetail(LocalDate.of(2024, 12, 10));

        Assertions.assertThat(attendanceDetail.getAttendanceDateTime()
                        .toLocalTime())
                .isEqualTo(LocalTime.of(10, 6));
        Assertions.assertThat(
                        attendanceDetail.getAttendance())
                .isEqualTo(Attendance.지각);
    }

}
