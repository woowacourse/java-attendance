package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.constants.AttendanceStatus;
import domain.constants.ExpulsionStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {

        @DisplayName("오늘 전날까지의 빈 Attendance를 생성하여 초기화한다.")
        @Test
        public void of() throws Exception {
            // given
            final String crewName = "name";
            final LocalDate today = LocalDate.of(2024, 12, 13);
            final int expectedSizeExceptHoliday = 9;

            // when
            final Crew actual = Crew.of(crewName, today);

            // then
            assertThat(actual.getAttendances())
                    .hasSize(expectedSizeExceptHoliday)
                    .allSatisfy(attendance -> assertThat(attendance.isEmpty()).isTrue());
        }

        @DisplayName("주어진 LocalDateTime에 해당하는 Attendance를 추가한다.")
        @Test
        public void addAttendance() throws Exception {
            // given
            final Crew crew = new Crew("name", new ArrayList<>());
            final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 13, 10, 0);

            // when
            final Attendance actual = crew.addAttendance(localDateTime);

            // then
            assertThat(crew.getAttendances())
                    .hasSize(1);
            assertThat(actual.getDateTime())
                    .hasYear(2024)
                    .hasMonth(Month.DECEMBER)
                    .hasDayOfMonth(13)
                    .hasHour(10)
                    .hasMinute(0);
        }

        @DisplayName("오늘 출석이 존재하는지 여부를 올바르게 비교한다.")
        @Test
        public void isAlreadyTodayAttendance() throws Exception {
            // given
            final Crew crew = new Crew("name", new ArrayList<>());
            final LocalDate today = LocalDate.of(2024, 12, 13);

            // when
            final boolean actual = crew.isAlreadyTodayAttendance(today);

            // then
            assertThat(actual).isFalse();
        }

        @DisplayName("주어진 날짜에 해당하는 출석을 yyyy-MM-dd HH:mm으로 올바르게 수정한다.")
        @Test
        public void updateAttendanceByDateTime() throws Exception {
            // given
            final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 13, 10, 30);
            final Attendance before = Attendance.of(localDateTime);
            final Crew crew = new Crew("name", List.of(before));
            final String targetDateTime = "2024-12-13 11:00";

            // when
            crew.updateAttendanceByDateTime(targetDateTime);

            // then
            assertThat(crew.getAttendances().getFirst().getDateTime())
                    .hasYear(2024)
                    .hasMonth(Month.DECEMBER)
                    .hasDayOfMonth(13)
                    .hasHour(11)
                    .hasMinute(0);
        }

        @DisplayName("주어진 LocalDate에 해당하는 출석을 LocalTime으로 올바르게 변경한다.")
        @Test
        public void updateAttendanceByDateAndTime2() throws Exception {
            // given
            final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 13, 10, 30);
            final Attendance before = Attendance.of(localDateTime);
            final LocalDate targetDate = LocalDate.of(2024, 12, 13);
            final LocalTime targetTime = LocalTime.of(11, 30);
            final Crew crew = new Crew("name", List.of(before));

            // when
            final UpdatedAttendanceSnapshot actual = crew.updateAttendanceByDateAndTime(targetTime,
                    targetDate);

            // then
            assertThat(actual.getBefore()).isSameAs(before);
            assertThat(actual.getAfter().getDateTime())
                    .hasYear(2024)
                    .hasMonth(Month.DECEMBER)
                    .hasDayOfMonth(13)
                    .hasHour(11)
                    .hasMinute(30);
            assertThat(actual.getAfter()).isSameAs(crew.getAttendances().getFirst());
        }

        @DisplayName("해당 크루의 출석 통계를 올바르게 계산한다.")
        @Test
        public void calculateAttendanceStatistics() throws Exception {
            // given
            final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 13, 10, 30);
            final Attendance before = Attendance.of(localDateTime);
            final Crew crew = new Crew("name", List.of(before));
            final AttendanceStatus expected = AttendanceStatus.LATE;

            // when
            final Map<AttendanceStatus, Integer> actual = crew.calculateAttendanceStatistics();

            // then
            assertThat(actual.get(expected))
                    .isEqualTo(1);
        }

        @DisplayName("제적 상태를 계산한다.")
        @Test
        public void calculateExpulsionStatus() throws Exception {
            // given
            final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 13, 10, 30);
            final Attendance before = Attendance.of(localDateTime);
            final Crew crew = new Crew("name", List.of(before));

            // when
            final ExpulsionStatus actual = crew.calculateExpulsionStatus();

            // then
            assertThat(actual).isSameAs(ExpulsionStatus.NORMAL);
        }

        @DisplayName("지각을 결석으로 치환하여 계산한 결석 수를 계산한다.")
        @Test
        public void calculateExpulsionCount() throws Exception {
            // given
            final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 13, 10, 30);
            final Attendance before = Attendance.of(localDateTime);
            final Crew crew = new Crew("name", List.of(before));

            // when
            final int actual = crew.calculateExpulsionCount();

            // then
            assertThat(actual).isEqualTo(0);
        }

        @DisplayName("주어진 날짜에 해당하는 출석을 올바르게 찾는다.")
        @Test
        public void findAttendanceByDate() throws Exception {
            // given
            final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 13, 10, 30);
            final Attendance before = Attendance.of(localDateTime);
            final Crew crew = new Crew("name", List.of(before));

            // when
            final Attendance actual = crew.findAttendanceByDate(localDateTime.toLocalDate());

            // then
            assertThat(actual).isSameAs(before);
        }

        @DisplayName("expulsion count가 더 크다면 음수, 대상이 더 크다면 양수를 반환하고, 같다면 이름을 비교한다.")
        @Test
        public void compareByExpulsionCount() throws Exception {
            // given
            final LocalDateTime absenceTime = LocalDateTime.of(2024, 12, 13, 11, 30);
            final LocalDateTime normalTime = LocalDateTime.of(2024, 12, 13, 10, 0);
            final Attendance absence = Attendance.of(absenceTime);
            final Attendance normal = Attendance.of(normalTime);
            final Crew absenceCrew = new Crew("name", List.of(absence));
            final Crew normalCrew = new Crew("name", List.of(normal));

            // when
            final int actual = absenceCrew.compareByExpulsionCount(normalCrew);

            // then
            assertThat(actual).isNegative();
        }
    }
}
