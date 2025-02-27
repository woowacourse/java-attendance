package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    private AttendanceRepository repository;

    @BeforeEach
    void setUp() {
        Map<String, List<Attendance>> initialAttendances = new HashMap<>();

        LocalDate localDate = LocalDate.of(2024, 12, 5);
        LocalTime localTime = LocalTime.of(10, 0);

        initialAttendances.put("fora", new ArrayList<>(List.of(
                new Attendance("fora", localDate, localTime)
        )));
        initialAttendances.put("mingom", new ArrayList<>(List.of(
                new Attendance("mingom", localDate, localTime)
        )));
        initialAttendances.put("mungoo", new ArrayList<>(List.of(
                new Attendance("mungoo", localDate, localTime)
        )));

        repository = new AttendanceRepository(initialAttendances);
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
        LocalDate localDate = LocalDate.of(2024, 12, 5);
        LocalTime localTime = LocalTime.of(9, 55);

        // when & then
        Assertions.assertThatThrownBy(() -> {
                    repository.checkIn(name, localDate, localTime);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 출석한 크루입니다.");
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

    @Test
    void 주말_및_공휴일에는_출석할_수_없다() {
        // given
        String name = "fora";
        LocalDate localDate = LocalDate.of(2024, 12, 1);
        LocalTime localTime = LocalTime.of(9, 55);

        // when & then
        Assertions.assertThatThrownBy(() -> {
                    repository.checkIn(name, localDate, localTime);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말 및 공휴일에는 출석할 수 없습니다.");
    }

    @Test
    void 존재하는_않는_크루는_출석할_수_없다() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 3);
        LocalTime localTime = LocalTime.of(9, 55);

        // when & then
        Assertions.assertThatThrownBy(() -> {
                    repository.checkIn("dompoo", localDate, localTime);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하는 크루의 닉네임을 입력해주세요.");
    }

    @Test
    void 닉네임과_수정할_날짜와_등교_시간을_입력시_출석기록이_수정된다() {
        // given
        String name = "fora";
        LocalDate localDate = LocalDate.of(2024, 12, 5);
        LocalTime localTime = LocalTime.of(10, 10);

        // when
        repository.update(name, localDate, localTime);
        Attendance attendance = repository.getAttendance(name, localDate);

        // then
        Assertions.assertThat(attendance.getName()).isEqualTo(name);
        Assertions.assertThat(attendance.getLocalTime()).isEqualTo(localTime);
    }

    @Test
    void 존재하는_않는_크루는_출석을_수정할_수_없다() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 3);
        LocalTime localTime = LocalTime.of(9, 55);

        // when & then
        Assertions.assertThatThrownBy(() -> {
                    repository.update("dompoo", localDate, localTime);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하는 크루의 닉네임을 입력해주세요.");
    }

    @Test
    void 주말_및_공휴일에는_출석을_수정할_수_없다() {
        // given
        String name = "fora";
        LocalDate localDate = LocalDate.of(2024, 12, 1);
        LocalTime localTime = LocalTime.of(9, 55);

        // when & then
        Assertions.assertThatThrownBy(() -> {
                    repository.update(name, localDate, localTime);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말 및 공휴일에는 출석할 수 없습니다.");
    }

    @Test
    void 오늘_이후의_날짜는_출석할_수_없다() {
        // given
        String name = "fora";
        LocalDate localDate = LocalDate.of(2025, 7, 25);
        LocalTime localTime = LocalTime.of(9, 55);

        // when & then
        Assertions.assertThatThrownBy(() -> {
                    repository.update(name, localDate, localTime);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수정할 수 없는 날짜입니다.");

    }
}
