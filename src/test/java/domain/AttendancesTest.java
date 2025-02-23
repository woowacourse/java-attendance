package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.TimeMachine;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttendancesTest {

    private Attendances attendances;

    @BeforeEach
    void setUp() {
        attendances = Attendances.create();
    }

    @Test
    @DisplayName("출석을 추가할 수 있다")
    void addAttendance() {
        // given
        AttendanceDate date = AttendanceDate.from(LocalDate.of(2024, 12, 10));
        AttendanceTime time = AttendanceTime.from(LocalTime.of(9, 0));

        // when
        Attendance attendance = attendances.add(date, time);

        // then
        assertThat(attendance).isNotNull();
        assertThat(attendances.existsByDate(date)).isTrue();
        assertThat(attendances.findByDate(date)).isEqualTo(time);
    }

    @Test
    @DisplayName("출석을 중복 추가하면 예외가 발생한다")
    void whenDuplicateAttendanceAdded() {
        // given
        AttendanceDate date = AttendanceDate.from(LocalDate.of(2024, 12, 10));
        AttendanceTime time = AttendanceTime.from(LocalTime.of(9, 10));
        attendances.add(date, time);

        // when
        // then
        assertThatThrownBy(() -> attendances.add(date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 출석한 경우, 수정 기능을 이용해주세요.");
    }

    @Test
    @DisplayName("출석을 수정할 수 있다")
    void updateAttendance() {
        // given
        AttendanceDate date = AttendanceDate.from(LocalDate.of(2024, 12, 10));
        AttendanceTime oldTime = AttendanceTime.from(LocalTime.of(9, 0));
        AttendanceTime newTime = AttendanceTime.from(LocalTime.of(9, 30));

        attendances.add(date, oldTime);

        // when
        Attendance updatedAttendance = attendances.update(date, newTime);

        // then
        assertThat(updatedAttendance).isNotNull();
        assertThat(attendances.findByDate(date)).isEqualTo(newTime);
    }

    @Test
    @DisplayName("출석 여부를 확인할 수 있다")
    void checkIfAttendanceExists() {
        // given
        AttendanceDate date = AttendanceDate.from(LocalDate.of(2024, 12, 10));
        AttendanceTime time = AttendanceTime.from(LocalTime.of(9, 0));
        attendances.add(date, time);

        // when
        boolean exists = attendances.existsByDate(date);
        boolean notExists = attendances.existsByDate(AttendanceDate.from(LocalDate.of(2024, 12, 11)));

        // then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    @DisplayName("출석 통계를 계산할 수 있다")
    void calculateAttendanceStatistics() {
        // given
        TimeMachine.timeTravelAt(7);
        String nickname = "강산";

        attendances.add(AttendanceDate.from(LocalDate.of(2024, 12, 2)), AttendanceTime.from(LocalTime.of(9, 0)));
        attendances.add(AttendanceDate.from(LocalDate.of(2024, 12, 3)), AttendanceTime.from(LocalTime.of(9, 30)));
        attendances.add(AttendanceDate.from(LocalDate.of(2024, 12, 4)), AttendanceTime.from(LocalTime.of(10, 6)));
        // 5, 6일 출석 X

        // when
        AttendanceStatistics statistics = attendances.calculateStatistics(nickname, TimeMachine.dateOfNow());

        // then
        assertThat(statistics).isNotNull();
        assertThat(statistics.attendCount()).isEqualTo(2);
        assertThat(statistics.lateCount()).isEqualTo(1);
        assertThat(statistics.absentCount()).isEqualTo(2);
    }
}