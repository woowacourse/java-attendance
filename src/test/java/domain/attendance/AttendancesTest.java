package domain.attendance;

import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AttendancesTest {
    @DisplayName("오늘 날짜에 출석 정보가 존재하는지 확인한다")
    @Test
    void test() {
        // given
        LocalDateTime day = LocalDateTime.of(2025, 2, 27, 10, 0);
        Attendances attendances = new Attendances(List.of(day, day.plusDays(1)));

        // when
        boolean hasDate = attendances.has(day.toLocalDate());

        // then
        Assertions.assertThat(hasDate).isTrue();
    }

    @DisplayName("크루의 출석 기록을 확인한다")
    @Nested
    class CrewsInfo {
        @DisplayName("크루의 전체 출석일을 계산한다")
        @Test
        void test() {
            // given
            LocalDateTime attendanceDate = LocalDateTime.of(2025, 2, 25, 10, 0);
            Attendances attendances = new Attendances(
                    List.of(attendanceDate, attendanceDate.plusDays(1), attendanceDate.plusDays(2)));

            // when
            int attendanceCount = attendances.countAttendance();

            // then
            Assertions.assertThat(attendanceCount).isEqualTo(3);
        }

        @DisplayName("크루의 전체 지각일을 계산한다")
        @Test
        void test2() {
            // given
            LocalDateTime attendanceDate = LocalDateTime.of(2025, 2, 25, 10, 30);
            Attendances attendances = new Attendances(
                    List.of(attendanceDate, attendanceDate.plusDays(1), attendanceDate.plusDays(2)));

            // when
            int tardyCouont = attendances.countTardy();

            // then
            Assertions.assertThat(tardyCouont).isEqualTo(3);
        }

        @DisplayName("크루의 전체 결석일을 계산한다")
        @Test
        void test3() {
            // given
            LocalDateTime attendanceDate = LocalDateTime.of(2025, 2, 25, 10, 31);
            Attendances attendances = new Attendances(
                    List.of(attendanceDate, attendanceDate.plusDays(1), attendanceDate.plusDays(2)));

            // when
            int absenceCount = attendances.countAbsence(attendanceDate.plusDays(3).toLocalDate());

            // then
            Assertions.assertThat(absenceCount).isEqualTo(3);
        }
    }
}