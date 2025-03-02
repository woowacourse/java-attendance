package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class AttendanceHistoryTest {

    @Nested
    @DisplayName("출석을 생성하여 저장한다.")
    class Attendance {

        @DisplayName("새로운 출석 기록을 올바르게 생성하여 저장한다.")
        @Test
        public void attendance() throws Exception {
            // given
            final Crew owner = new Crew("owner");
            final var attendanceHistory = new AttendanceHistory(owner);
            final var attendanceDateTime = LocalDateTime.of(2024, 12, 13, 10, 5);

            // when
            final var actual = attendanceHistory.attendance(attendanceDateTime);

            // then
            assertSoftly((s) -> {
                s.assertThat(actual.getAttendanceDate())
                        .hasYear(2024)
                        .hasMonthValue(12)
                        .hasDayOfMonth(13);
                s.assertThat(actual.getAttendanceTime()).isNotEmpty();
                s.assertThat(actual.getAttendanceTime().get())
                        .hasHour(10)
                        .hasMinute(5);
            });
        }

        @DisplayName("등교 날짜가 아닌 경우라면 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(ints = {14, 15, 25})
        public void attendance(final int dayOfMonth) throws Exception {
            // given
            final Crew owner = new Crew("owner");
            final var attendanceHistory = new AttendanceHistory(owner);
            final var attendanceDateTime = LocalDateTime.of(2024, 12, dayOfMonth, 10, 5);

            // when & then
            assertThatThrownBy(() -> attendanceHistory.attendance(attendanceDateTime))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("이미 출석한 경우라면 예외가 발생한다.")
        @Test
        public void alreadyAttendance() throws Exception {
            // given
            final Crew owner = new Crew("owner");
            final var attendanceHistory = new AttendanceHistory(owner);
            final var attendanceDateTime = LocalDateTime.of(2024, 12, 13, 10, 5);
            attendanceHistory.attendance(attendanceDateTime);

            // when & then
            assertThatThrownBy(() -> attendanceHistory.attendance(attendanceDateTime))
                    .isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("출석 시간이 아니라면, 예외가 발생한다.")
        @Test
        public void notAttendanceTimeAttendance() throws Exception {
            // given
            final Crew owner = new Crew("owner");
            final var attendanceHistory = new AttendanceHistory(owner);
            final var attendanceDateTime = LocalDateTime.of(2024, 12, 13, 1, 5);

            // when & then
            assertThatThrownBy(() -> attendanceHistory.attendance(attendanceDateTime))
                    .isInstanceOf(IllegalArgumentException.class);
        }

    }
    
    @Nested
    @DisplayName("날짜를 기준으로 출석 기록을 반환한다.")
    class FindAttendanceHistoryByDate {

        @DisplayName("출석 기록을 날짜 기준으로 올바르게 찾아 반환한다.")
        @Test
        public void findByDate() throws Exception {
            // given
            final Crew owner = new Crew("owner");
            final var attendanceHistory = new AttendanceHistory(owner);
            final var attendanceDateTime = LocalDateTime.of(2024, 12, 13, 10, 5);
            attendanceHistory.attendance(attendanceDateTime);

            // when
            final AttendanceRecord actual = attendanceHistory.findByDate(LocalDate.of(2024, 12, 13));

            // then
            assertSoftly(s -> {
                s.assertThat(actual.getAttendanceDate())
                        .hasYear(2024)
                        .hasMonthValue(12)
                        .hasDayOfMonth(13);
                s.assertThat(actual.getAttendanceTime()).isNotEmpty();
                s.assertThat(actual.getAttendanceTime().get())
                        .hasHour(10)
                        .hasMinute(5);
            });
        }

        @DisplayName("등교 날짜가 아닌 날짜의 출석 기록을 찾는다면, 예외가 발생한다.")
        @Test
        public void findByDateFromNotAttendanceDay() throws Exception {
            // given
            final Crew owner = new Crew("owner");
            final var attendanceHistory = new AttendanceHistory(owner);
            final var findDate = LocalDate.of(2024, 12, 14);

            // when & then
            assertThatThrownBy(() -> attendanceHistory.findByDate(findDate))
                    .isInstanceOf(IllegalArgumentException.class);
        }
        
    }
    
    @Nested
    @DisplayName("기존의 출석 기록을 새로운 시간으로 수정한다.")
    class UpdateRecordTimeByDate {
        
        @DisplayName("기존의 출석 기록을 새로운 시간으로 올바르게 수정한다.")
        @Test
        public void updateTimeByDate() throws Exception {
            // given
            final Crew owner = new Crew("owner");
            final var attendanceHistory = new AttendanceHistory(owner);
            final var beforeTime = LocalDateTime.of(2024, 12, 13, 10, 5);
            attendanceHistory.attendance(beforeTime);
            final var afterTime = LocalDateTime.of(2024, 12, 13, 11, 30);

            // when
            final AttendanceRecord before = attendanceHistory.updateTimeByDate(afterTime);
            final AttendanceRecord after = attendanceHistory.findByDate(LocalDate.of(2024, 12, 13));

            // then
            assertSoftly(s -> {
                s.assertThat(before.getAttendanceTime()).isNotEmpty();
                s.assertThat(before.getAttendanceTime().get())
                        .hasHour(10)
                        .hasMinute(5);
                s.assertThat(after.getAttendanceTime()).isNotEmpty();
                s.assertThat(after.getAttendanceTime().get())
                        .hasHour(11)
                        .hasMinute(30);
            });
        }

        @DisplayName("등교 날짜가 아닌 날짜의 출석을 수정한다면, 예외가 발생한다.")
        @Test
        public void updateTimeByDateFromNotAttendanceDay() throws Exception {
            // given
            final Crew owner = new Crew("owner");
            final var attendanceHistory = new AttendanceHistory(owner);
            final var updateDateTime = LocalDateTime.of(2024, 12, 14, 10, 5);

            // when & then
            assertThatThrownBy(() -> attendanceHistory.updateTimeByDate(updateDateTime))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("수정하려는 시간이 등교 시간이 아니라면, 예외가 발생한다.")
        @Test
        public void updateTimeByDateNotSchoolTime() throws Exception {
            // given
            final Crew owner = new Crew("owner");
            final var attendanceHistory = new AttendanceHistory(owner);
            final var updateDateTime = LocalDateTime.of(2024, 12, 13, 23, 5);

            // when & then
            assertThatThrownBy(() -> attendanceHistory.updateTimeByDate(updateDateTime))
                    .isInstanceOf(IllegalArgumentException.class);
        }

    }
    
    @Nested
    @DisplayName("주어진 날짜 이전날까지의 출석 기록을 반환한다.")
    class FindAllAttendanceHistoryUntilBeforeToday {

        @DisplayName("주어진 날짜 이전날까지의 출석 기록을 올바르게 찾아 반환한다.")
        @Test
        public void findAllUntilBeforeToday() throws Exception {
            // given
            final Crew owner = new Crew("owner");
            final var attendanceHistory = new AttendanceHistory(owner);
            final LocalDate targetDate = LocalDate.of(2024, 12, 3);

            // when
            final List<AttendanceRecord> actual = attendanceHistory.findAllUntilBeforeToday(targetDate);

            // then
            assertSoftly((s) -> {
                s.assertThat(actual).hasSize(1);
                s.assertThat(actual.getFirst().getAttendanceDate())
                        .hasDayOfMonth(2);
                s.assertThat(actual.getFirst().getAttendanceTime()).isEmpty();
            });
        }
        
    }
    
    @Nested
    @DisplayName("주어진 날짜의 이전날까지의 출석 통계를 계산한다.")
    class CalculateAttendanceStatusStatistics {

        @DisplayName("전날까지의 출석 통계를 올바르게 계산하여 반환한다.")
        @Test
        public void calculateAttendanceStatusStatistics() throws Exception {
            // given
            final Crew owner = new Crew("owner");
            final var attendanceHistory = new AttendanceHistory(owner);
            attendanceHistory.updateTimeByDate(LocalDateTime.of(2024, 12, 2, 10, 5));
            attendanceHistory.updateTimeByDate(LocalDateTime.of(2024, 12, 3, 10, 30));
            attendanceHistory.updateTimeByDate(LocalDateTime.of(2024, 12, 4, 10, 31));
            final LocalDate targetDate = LocalDate.of(2024, 12, 5);
            final Map<AttendanceStatus, Integer> expected = Map.of(
                    AttendanceStatus.ATTENDANCE, 1, AttendanceStatus.LATE, 1, AttendanceStatus.ABSENCE, 1);

            // when
            final Map<AttendanceStatus, Integer> actual = attendanceHistory.calculateAttendanceStatusStatistics(
                    targetDate);

            // then
            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }
        
    }
    
    @Nested
    @DisplayName("출석 기록이 제적 위험이 존재하는지 여부를 반환한다.")
    class IsRiskOfExpulsion {

        @DisplayName("출석 기록이 제적 위험이 존재하는지를 올바르게 반환한다.")
        @Test
        public void isRiskOfExpulsion() throws Exception {
            // given
            final Crew owner = new Crew("owner");
            final var attendanceHistory = new AttendanceHistory(owner);
            final LocalDate targetDate = LocalDate.of(2024, 12, 13);

            // when
            final boolean actual = attendanceHistory.isRiskOfExpulsion(targetDate);

            // then
            assertThat(actual).isTrue();
        }
        
    }
    
    @Nested
    @DisplayName("제적 위험 상태를 계산한다.")
    class CalculateRiskOfExpulsion {

        @DisplayName("제적 위험 상태를 올바르게 계산하여 반환한다.")
        @ParameterizedTest
        @MethodSource("provideDayOfMonthAndRiskOfExpulsion")
        public void calculateRiskOfExpulsion(final int dayOfMonth, final RiskOfExpulsionStatus expected)
                throws Exception {
            // given
            final Crew owner = new Crew("owner");
            final var attendanceHistory = new AttendanceHistory(owner);
            final LocalDate targetDate = LocalDate.of(2024, 12, dayOfMonth);

            // when
            final RiskOfExpulsionStatus actual = attendanceHistory.calculateRiskOfExpulsionStatus(targetDate);

            // then
            assertThat(actual).isEqualByComparingTo(expected);
        }

        private static Stream<Arguments> provideDayOfMonthAndRiskOfExpulsion() {
            return Stream.of(
                    Arguments.of(3, RiskOfExpulsionStatus.NORMAL),
                    Arguments.of(4, RiskOfExpulsionStatus.WARNING),
                    Arguments.of(5, RiskOfExpulsionStatus.INTERVIEW),
                    Arguments.of(7, RiskOfExpulsionStatus.INTERVIEW),
                    Arguments.of(10, RiskOfExpulsionStatus.EXPULSION)
            );
        }
        
    }
    
    @Nested
    @DisplayName("주어진 날짜에 출석이 존재하는지 여부를 반환한다.")
    class IsAlreadyAttendance {

        @DisplayName("출석이 존재하는 경우 true 아니라면 false를 반환한다.")
        @Test
        public void isAlreadyAttendance() throws Exception {
            // given
            final Crew owner = new Crew("owner");
            final var attendanceHistory = new AttendanceHistory(owner);
            final LocalDateTime date = LocalDateTime.of(2024, 12, 2, 10, 5);
            attendanceHistory.attendance(date);
            final LocalDate targetDate = LocalDate.of(2024, 12, 3);

            // when
            final boolean trueActual = attendanceHistory.isAlreadyAttendance(date.toLocalDate());
            final boolean falseActual = attendanceHistory.isAlreadyAttendance(targetDate);

            // then
            assertThat(trueActual).isTrue();
            assertThat(falseActual).isFalse();
        }

    }

}
