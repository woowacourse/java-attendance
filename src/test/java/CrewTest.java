import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.Attendance;
import domain.Crew;
import domain.ERROR_MESSAGE;
import domain.Penalty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CrewTest {
    @Nested
    @DisplayName("크루 출석 기록 저장 테스트")
    class AddAttendanceTest {

        @DisplayName("정상 출석 확인")
        @Test
        void test1() {
            // given
            Crew crew = new Crew("빙봉");
            LocalDateTime attendedTime = LocalDateTime.of(2024, 12, 3, 8, 25);

            // when
            crew.addAttendanceWithDateTime(attendedTime);

            // then
            assertThat(crew.getAttendanceHistory()).extracting("dateTime").containsExactly(attendedTime);
        }

        @DisplayName("정상 출석 확인2")
        @Test
        void test2() {
            // given
            Crew crew = new Crew("빙봉");
            Attendance attendance1 = new Attendance(LocalDateTime.of(2024, 12, 3, 8, 25));
            Attendance attendance2 = new Attendance(LocalDateTime.of(2024, 12, 4, 10, 25));

            // when
            crew.addAttendance(attendance1);
            crew.addAttendance(attendance2);

            // then
            assertThat(crew.getAttendanceHistory()).containsExactly(attendance1, attendance2);
        }

        @DisplayName("출석 정보 변경")
        @Test
        void test3() {
            // given
            Crew crew = new Crew("빙봉");

            Attendance attendance1 = new Attendance(LocalDateTime.of(2024, 12, 3, 8, 25));
            crew.addAttendance(attendance1);    // 출석 처리

            // when
            crew.modifyAttendedTime(3, LocalTime.of(10, 25));   // 지각으로 변경
            // then
            Assertions.assertAll(
                    () -> assertThat(crew.getAttendCount()).isEqualTo(0),
                    () -> assertThat(crew.getLateCount()).isEqualTo(1)
            );
        }


        @Nested
        @DisplayName("크루 출석 기록 저장 오류 테스트")
        class AbnormalAddAttendanceTest {

            @DisplayName("이미 출석한 경우")
            @Test
            void test1() {
                // given
                Crew crew = new Crew("띠용");
                Attendance attendanceBefore = new Attendance(LocalDateTime.of(2024, 12, 3, 8, 25));
                Attendance attendanceAfter = new Attendance(LocalDateTime.of(2024, 12, 3, 10, 25));
                crew.addAttendance(attendanceBefore);

                // when & then
                assertThatThrownBy(() -> crew.addAttendance(attendanceAfter))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage(ERROR_MESSAGE.ALREADY_ATTENDED.getMessage());
            }
        }

        @Nested
        @DisplayName("크루 출석 상태 갯수 테스트")
        class CountAttendanceStatusTest {
            @DisplayName("출석 상태별 개수 반환")
            @Test
            void test1() {
                // given
                Crew crew = new Crew("빙봉");

                // when
                crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 2, 8, 25)); // 출석
                crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 3, 8, 25)); // 출석
                crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 4, 8, 25)); // 출석
                crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 5, 10, 25)); // 지각
                crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 6, 10, 25)); // 지각
                crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 9, 15, 0)); // 결석

                // then
                assertAll(
                        () -> assertThat(crew.getAttendCount()).isEqualTo(3),
                        () -> assertThat(crew.getLateCount()).isEqualTo(2),
                        () -> assertThat(crew.getAbsentCount()).isEqualTo(1)
                );
            }

            @DisplayName("출석하지 않은 날을 결석으로 반환")
            @Test
            void test2() {
                // given
                Crew crew = new Crew("빙봉");

                // when
                crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 2, 8, 25)); // 출석
                crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 3, 8, 25)); // 출석
                // 4일 결석
                crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 5, 10, 25)); // 지각
                // 6일 결석
                crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 9, 15, 0)); // 결석

                crew.fillEmptyDateWithAbsent(LocalDate.of(2024, 12, 9));

                // then
                assertAll(
                        () -> assertThat(crew.getAttendCount()).isEqualTo(2),
                        () -> assertThat(crew.getLateCount()).isEqualTo(1),
                        () -> assertThat(crew.getAbsentCount()).isEqualTo(3)
                );
            }
        }

        @Nested
        @DisplayName("크루 패널티 상태 체크 테스트")
        class CrewPenaltyTest {

            @DisplayName("패널티 X 상태")
            @Test
            void test1() {
                // given
                Crew crew = new Crew("띠용");

                // when
                crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 2, 8, 25)); // 출석
                crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 3, 8, 25)); // 출석
                crew.fillEmptyDateWithAbsent(LocalDate.of(2024, 12, 3));

                // then
                assertThat(crew.getPenalty()).isEqualTo(Penalty.NONE);
            }


            @DisplayName("경고 상태")
            @Test
            void test2() {
                // given
                Crew crew = new Crew("띠용");

                // when
                crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 2, 8, 25)); // 출석
                crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 3, 8, 25)); // 출석
                // 4일 결석
                // 5일 결석
                crew.fillEmptyDateWithAbsent(LocalDate.of(2024, 12, 5));
                // 2 결석 -> 경고

                // then
                assertThat(crew.getPenalty()).isEqualTo(Penalty.WARNING);
            }

            @DisplayName("면담 상태")
            @Test
            void test3() {
                // given
                Crew crew = new Crew("띠용");

                // when
                crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 2, 8, 25)); // 출석
                // 5일 결석
                // 4일 결석
                // 6일 결석
                crew.fillEmptyDateWithAbsent(LocalDate.of(2024, 12, 8));
                // 3 결석 -> 면담

                // then
                assertThat(crew.getPenalty()).isEqualTo(Penalty.COUNSELLING);
            }

            @DisplayName("제적 상태")
            @Test
            void test4() {
                // given
                Crew crew = new Crew("띠용");

                // when
                // 2일 결석
                // 3일 결석
                // 4일 결석
                // 6일 결석
                // 9일 결석
                // 10일 결석
                crew.fillEmptyDateWithAbsent(LocalDate.of(2024, 12, 11));
                // 6 결석 -> 제적

                // then
                assertThat(crew.getPenalty()).isEqualTo(Penalty.EXPEL);
            }

            @DisplayName("지각 3회는 결석 1회로 간주")
            @Test
            void test5() {
                // given
                Crew crew = new Crew("띠용");

                // when
                crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 2, 13, 6)); // 지각
                crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 3, 10, 6)); // 지각
                crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 4, 10, 6)); // 지각
                // 5일 결석
                crew.fillEmptyDateWithAbsent(LocalDate.of(2024, 12, 5));

                // then
                assertThat(crew.getPenalty()).isEqualTo(Penalty.WARNING);
                // 3 지각 1 결석 -> 경고
            }


        }

    }
}