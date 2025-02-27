package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    private AttendanceRepository repository;

    @BeforeEach
    void setUp() {
        repository = new AttendanceRepository();
    }

    @Test
    void 닉네임과_등교_시간을_입력시_출석된다() {
        // given
        String name = "fora";
        LocalDate localDate = LocalDate.of(2024, 12, 3);
        LocalTime localTime = LocalTime.of(9, 55);

        // when
        Attendance attendance = new Attendance(name, localDate, localTime);

        // then
        Assertions.assertThat(attendance.getName()).isEqualTo(name);
        Assertions.assertThat(attendance.getLocalTime()).isEqualTo(localTime);
    }

    @Test
    void 출석을_저장한다() {
        // given
        String name = "fora";
        LocalDate localDate = LocalDate.of(2024, 12, 3);
        LocalTime localTime = LocalTime.of(9, 55);

        // when
        repository.checkIn(name, localDate, localTime);
        Attendance attendance = repository.getAttendance(name, localDate);

        // then
        Assertions.assertThat(attendance.getName()).isEqualTo(name);
        Assertions.assertThat(attendance.getLocalTime()).isEqualTo(localTime);
    }

    @Test
    void 이미_출석한_경우_다시_출석할_수_없다() {
        // given
        String name = "fora";
        LocalDate localDate = LocalDate.of(2024, 12, 3);
        LocalTime firstTime = LocalTime.of(9, 55);
        LocalTime secondTime = LocalTime.of(10, 3);

        // when & then
        Assertions.assertThatThrownBy(() -> {
            repository.checkIn(name, localDate, firstTime);
            repository.checkIn(name, localDate, secondTime);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    @DisplayName("출석_시간으로_알맞은_출석_상태를_반환한다")
    class AttendanceStateTest {
        @Test
        void 출석했을_경우_출석_상태를_반환한다() {
            // given
            LocalDate localDate = LocalDate.of(2024, 12, 3);
            LocalTime localTime = LocalTime.of(9, 55);

            // when
            AttendanceState state = AttendanceState.findStateBy(localDate, localTime);

            // then
            Assertions.assertThat(state.getState()).isEqualTo("출석");
        }

        @Test
        void 지각했을_경우_지각_상태를_반환한다() {
            // given
            LocalDate localDate = LocalDate.of(2024, 12, 3);
            LocalTime localTime = LocalTime.of(10, 6);

            // when
            AttendanceState state = AttendanceState.findStateBy(localDate, localTime);

            // then
            Assertions.assertThat(state.getState()).isEqualTo("지각");
        }

        @Test
        void 결석했을_경우_결석_상태를_반환한다() {
            // given
            LocalDate localDate = LocalDate.of(2024, 12, 3);
            LocalTime localTime = LocalTime.of(10, 31);

            // when
            AttendanceState state = AttendanceState.findStateBy(localDate, localTime);

            // then
            Assertions.assertThat(state.getState()).isEqualTo("결석");
        }

    }
}
