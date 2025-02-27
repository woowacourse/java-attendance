package attendance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CrewTest {

    @Test
    @DisplayName("닉네임과 출석 기록이 들어오면, 크루의 출석 기록이 추가되고, Attendance를 리턴한다")
    void crewAddAttendanceTest1() {
        Crew crew = new Crew("모루");
        assertThat(crew.addAttendance(LocalDateTime.of(2024, 12, 11, 8, 0)))
                .isInstanceOf(Attendance.class);
    }

    @Test
    @DisplayName("출석 시간이 운영 시간이 아니면 예외")
    void crewAddAttendanceTest2() {
        Crew crew = new Crew("모루");
        assertThatThrownBy(() -> crew.addAttendance(LocalDateTime.of(2024, 12, 11, 6, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("출석일이 운영일이 아니면 예외")
    void crewAddAttendanceTest3() {
        Crew crew = new Crew("모루");
        assertThatThrownBy(() -> crew.addAttendance(LocalDateTime.of(2024, 12, 8, 10, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("크루의 출석 기록에 출석이 추가됐는지 확인")
    void crewAddAttendanceTest4() {
        Crew crew = new Crew("모루");
        crew.addAttendance(LocalDateTime.of(2024, 12, 11, 8, 0));
        crew.addAttendance(LocalDateTime.of(2024, 12, 12, 8, 0));

        assertThat(crew.getAttendances().size()).isEqualTo(2);
    }

    @Nested
    @DisplayName("크루의 출석 기록에서 출석 상태 세기")
    class CountAttendanceStatusTest {
        @Test
        @DisplayName("크루의 출석 기록에서 출석 횟수 세기")
        void countAttendanceStatusTest1() {
            Crew crew = new Crew("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 8, 0));
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 8, 0));

            assertThat(crew.countAttendanceStatus(AttendanceStatus.ATTEND)).isEqualTo(2);
        }

        @Test
        @DisplayName("크루의 출석 기록에서 지각 횟수 세기")
        void countAttendanceStatusTest2() {
            Crew crew = new Crew("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 10, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 13, 10, 6));

            assertThat(crew.countAttendanceStatus(AttendanceStatus.LATE)).isEqualTo(3);
        }

        @Test
        @DisplayName("크루의 출석 기록에서 결석 횟수 세기")
        void countAttendanceStatusTest3() {
            Crew crew = new Crew("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 11, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 11, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 13, 11, 6));

            assertThat(crew.countAttendanceStatus(AttendanceStatus.ABSENCE)).isEqualTo(3);
        }
    }

    @Nested
    @DisplayName("결석 횟수에 따라 경고 메시지 출력")
    class CheckAbsenceRuleTest {
        @Test
        @DisplayName("결석 2번은 경고 대상자")
        void checkWarningTest1() {
            Crew crew = new Crew("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 11, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 11, 6));

            assertThat(crew.checkAbsenceRule()).isEqualTo(AbsenceRule.WARNING);
        }

        @Test
        @DisplayName("결석 3번은 면담 대상자")
        void checkWarningTest2() {
            Crew crew = new Crew("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 11, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 11, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 13, 11, 6));

            assertThat(crew.checkAbsenceRule()).isEqualTo(AbsenceRule.COUNSELING);
        }

        @Test
        @DisplayName("결석 5번은 제적 대상자")
        void checkWarningTest3() {
            Crew crew = new Crew("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 11, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 11, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 13, 11, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 16, 14, 6));
            crew.addAttendance(LocalDateTime.of(2024, 12, 17, 11, 6));

            assertThat(crew.checkAbsenceRule()).isEqualTo(AbsenceRule.EXPULSION);
        }

        @Test
        @DisplayName("지각 3번과 결석 2번은 면담 대상자")
        void checkWarningTest4() {
            Crew crew = new Crew("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 6)); //지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 10, 6)); //지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 13, 10, 6)); //지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 16, 14, 6)); //결석
            crew.addAttendance(LocalDateTime.of(2024, 12, 17, 11, 6)); //결석

            assertThat(crew.checkAbsenceRule()).isEqualTo(AbsenceRule.COUNSELING);
        }

        @Test
        @DisplayName("지각 5번과 결석 2번은 면담 대상자")
        void checkWarningTest5() {
            Crew crew = new Crew("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 9, 13, 6)); //지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 10, 10, 6)); //지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 6)); //지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 10, 6)); //지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 13, 10, 6)); //지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 16, 14, 6)); //결석
            crew.addAttendance(LocalDateTime.of(2024, 12, 17, 11, 6)); //결석

            assertThat(crew.checkAbsenceRule()).isEqualTo(AbsenceRule.COUNSELING);
        }

        @Test
        @DisplayName("지각 6번과 결석 3번은 제적 대상자")
        void checkWarningTest6() {
            Crew crew = new Crew("모루");
            crew.addAttendance(LocalDateTime.of(2024, 12, 6, 10, 6)); //지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 9, 13, 6)); //지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 10, 10, 6)); //지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 6)); //지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 12, 10, 6)); //지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 13, 10, 6)); //지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 16, 14, 6)); //결석
            crew.addAttendance(LocalDateTime.of(2024, 12, 17, 11, 6)); //결석
            crew.addAttendance(LocalDateTime.of(2024, 12, 18, 11, 6)); //결석

            assertThat(crew.checkAbsenceRule()).isEqualTo(AbsenceRule.EXPULSION);
        }
    }
}
