package domain.attendance;

import controller.AttendanceController;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @DisplayName("초기화 시 동일한 날짜의 출석 기록을 등록할 수 없다")
    @Test
    void test4() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 2);

        // when & then
        Assertions.assertThatThrownBy(() -> new Attendances(List.of(
                        LocalDateTime.of(date, LocalTime.of(10, 0)),
                        LocalDateTime.of(date, LocalTime.of(10, 5)))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("동일한 날짜의 출석 기록은 등록할 수 없습니다");
    }

    @DisplayName("동일한 날짜에 두 번 출석할 수 없다")
    @Test
    void test5() {
        // given
        LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 2, 10, 0);
        Attendances attendances = new Attendances(List.of(
                attendDateTime
        ));

        // when & then
        Assertions.assertThatThrownBy(() -> attendances.attend(AttendanceController.END_DATE, attendDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요");
    }

    @DisplayName("미래 날짜에 출석할 수 없다")
    @Test
    void test6() {
        // given
        Attendances attendances = new Attendances(List.of(
                LocalDateTime.of(2024, 12, 2, 10, 0)
        ));
        LocalDate endDate = LocalDate.of(2025, 3, 3);

        // when & then
        Assertions.assertThatThrownBy(
                        () -> attendances.attend(endDate, LocalDateTime.of(endDate.plusDays(1), LocalTime.of(10, 0))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("미래에 출석할 수 없습니다");
    }

}