package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.AttendanceHistory;
import domain.AttendanceRecord;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AttendanceHistoryTest {

    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {

        @DisplayName("새로운 출석 기록을 생성하여 저장한다.")
        @Test
        public void attendance() throws Exception {
            // given
            final Crew owner = new Crew("owner");
            final var attendanceHistory = new AttendanceHistory(owner);
            final var attendanceDateTime = LocalDateTime.of(2024, 12, 13, 10, 5);

            // when
            final var actual = attendanceHistory.attendance(attendanceDateTime);

            // then
            assertThat(actual.getDateTime())
                    .hasYear(2024)
                    .hasMonthValue(12)
                    .hasDayOfMonth(13)
                    .hasHour(10)
                    .hasMinute(5);
        }

        @DisplayName("출석 기록을 날짜 기준으로 찾아온다.")
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
            assertThat(actual.getDateTime())
                    .hasYear(2024)
                    .hasMonthValue(12)
                    .hasDayOfMonth(13)
                    .hasHour(10)
                    .hasMinute(5);
        }

        @DisplayName("기존의 출석 기록을 새로운 시간으로 수정한다.")
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
            assertThat(before.getDateTime())
                    .hasHour(10)
                    .hasMinute(5);
            assertThat(after.getDateTime())
                    .hasHour(11)
                    .hasMinute(30);
        }

        @DisplayName("기록이 존재하지 않는다면, empty record를 반환한다.")
        @Test
        public void findEmpty() throws Exception {
            // given
            final Crew owner = new Crew("owner");
            final var attendanceHistory = new AttendanceHistory(owner);
            final LocalDate targetDate = LocalDate.of(2024, 12, 13);

            // when
            final AttendanceRecord actual = attendanceHistory.findByDate(targetDate);

            // then
            assertThat(actual.isEmpty()).isTrue();
        }


        @DisplayName("주어진 날짜 이전날까지의 출석 기록을 반환한다.")
        @Test
        public void findAllUntilBeforeToday() throws Exception {
            // given
            final Crew owner = new Crew("owner");
            final var attendanceHistory = new AttendanceHistory(owner);
            final LocalDate targetDate = LocalDate.of(2024, 12, 3);

            // when
            final List<AttendanceRecord> actual = attendanceHistory.findAllUntilBeforeToday(targetDate);

            // then
            assertThat(actual).hasSize(1);
            assertThat(actual.getFirst().getDateTime())
                    .hasDayOfMonth(2);
            assertThat(actual.getFirst().isEmpty()).isTrue();
        }

    }

    @Nested
    @DisplayName("실패 테스트")
    class FailCases {

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

        @DisplayName("등교 날짜가 아닌 날짜의 출석 기록을 찾는다면, 예외가 발생한다.")
        @Test
        public void findByDate() throws Exception {
            // given
            final Crew owner = new Crew("owner");
            final var attendanceHistory = new AttendanceHistory(owner);
            final var findDate = LocalDate.of(2024, 12, 14);

            // when & then
            assertThatThrownBy(() -> attendanceHistory.findByDate(findDate))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("등교 날짜가 아닌 날짜의 출석을 수정한다면, 예외가 발생한다.")
        @Test
        public void updateTimeByDate() throws Exception {
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
}
