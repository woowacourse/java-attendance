package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.constants.AttendanceStatus;
import domain.constants.ErrorMessage;
import domain.constants.ExpulsionStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceSystemTest {
    private final LocalDate TODAY = LocalDate.of(2024, 12, 13);

    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {

        @DisplayName("주어진 이름에 해당하는 크루를 오늘날짜, 주어진 시간으로 올바르게 출석체크한다.")
        @Test
        public void attendance() throws Exception {
            // given
            final LocalTime targetTime = LocalTime.of(10, 0);
            final String crewName = "name";
            final Crew crew = Crew.of(crewName, TODAY);
            final AttendanceSystem attendanceSystem = new AttendanceSystem(List.of(crew));

            // when
            final Attendance actual = attendanceSystem.attendance(crewName, targetTime, TODAY);

            // then
            assertThat(actual.getDateTime())
                    .hasYear(2024)
                    .hasMonth(Month.DECEMBER)
                    .hasDayOfMonth(13)
                    .hasHour(10)
                    .hasMinute(0);
            assertThat(crew.isAlreadyTodayAttendance(TODAY)).isTrue();
        }

        @DisplayName("주어진 이름에 해당하는 크루의 오늘 출석 여부를 올바로 비교한다.")
        @Test
        public void isAlreadyTodayAttendance() throws Exception {
            // given
            final String crewName = "name";
            final Crew crew = Crew.of(crewName, TODAY);
            final AttendanceSystem attendanceSystem = new AttendanceSystem(List.of(crew));

            // when
            final boolean actual = attendanceSystem.isAlreadyTodayAttendance(crewName, TODAY);

            // then
            assertThat(actual).isFalse();
        }

        @DisplayName("제적 위험 대상자인 크루들을 올바로 반환한다.")
        @Test
        public void calculateRiskOfExpulsionCrews() throws Exception {
            // given
            final String crewName = "name";
            final Crew crew = Crew.of(crewName, TODAY);
            final AttendanceSystem attendanceSystem = new AttendanceSystem(List.of(crew));

            // when
            final List<Crew> actual = attendanceSystem.calculateRiskOfExpulsionCrews();

            // then
            assertThat(actual)
                    .hasSize(1)
                    .first()
                    .isSameAs(crew);
        }

        @DisplayName("오늘 날짜가 등교 날짜인지를 올바로 비교한다.")
        @Test
        public void isAttendanceDay() throws Exception {
            // given
            final String crewName = "name";
            final Crew crew = Crew.of(crewName, TODAY);
            final AttendanceSystem attendanceSystem = new AttendanceSystem(List.of(crew));

            // when
            final boolean actual = attendanceSystem.isAttendanceDay(TODAY);

            // then
            assertThat(actual).isTrue();
        }

        @DisplayName("크루의 출석을 올바로 수정한다.")
        @Test
        public void updateAttendanceByCrewNameAndDay() throws Exception {
            // given
            final String crewName = "name";
            final Crew crew = Crew.of(crewName, TODAY);
            final AttendanceSystem attendanceSystem = new AttendanceSystem(List.of(crew));
            final LocalTime targetTime = LocalTime.of(11, 30);
            final int targetDayOfMonth = 2;

            // when
            final UpdatedAttendanceSnapshot actual = attendanceSystem.updateAttendanceByCrewNameAndDay(
                    targetTime, crewName, targetDayOfMonth, TODAY);

            // then
            assertThat(actual.getAfter().getDateTime())
                    .hasDayOfMonth(2)
                    .hasHour(11)
                    .hasMinute(30);
            assertThat(actual.getBefore().getDateTime())
                    .hasDayOfMonth(2)
                    .hasHour(23)
                    .hasMinute(59);
        }

        @DisplayName("크루의 오늘 출석을 올바로 수정한다.")
        @Test
        public void updateTodayAttendance() throws Exception {
            // given
            final String crewName = "name";
            final Crew crew = Crew.of(crewName, TODAY);
            final LocalDateTime before = LocalDateTime.of(2024, 12, 13, 10, 0);
            crew.addAttendance(before);
            final AttendanceSystem attendanceSystem = new AttendanceSystem(List.of(crew));
            final LocalTime targetTime = LocalTime.of(11, 30);

            // when
            final UpdatedAttendanceSnapshot actual = attendanceSystem.updateTodayAttendance(crewName,
                    targetTime, TODAY);

            // then
            assertThat(actual.getAfter().getDateTime())
                    .hasHour(11)
                    .hasMinute(30);
            assertThat(actual.getBefore().getDateTime())
                    .hasHour(10)
                    .hasMinute(0);
        }

        @DisplayName("이름에 해당하는 크루의 제적 상태를 반환한다.")
        @Test
        public void calculateExpulsionStatusByCrew() throws Exception {
            // given
            final String crewName = "name";
            final Crew crew = Crew.of(crewName, TODAY);
            final AttendanceSystem attendanceSystem = new AttendanceSystem(List.of(crew));

            // when
            final ExpulsionStatus actual = attendanceSystem.calculateExpulsionStatusByCrew(crewName);

            // then
            assertThat(actual).isSameAs(ExpulsionStatus.EXPULSION);
        }

        @DisplayName("이름에 해당하는 크루의 출석 통계를 반환한다.")
        @Test
        public void calculateAttendanceStatisticsByCrew() throws Exception {
            // given
            final String crewName = "name";
            final Crew crew = Crew.of(crewName, TODAY);
            final AttendanceSystem attendanceSystem = new AttendanceSystem(List.of(crew));

            // when
            final Map<AttendanceStatus, Integer> actual = attendanceSystem.calculateAttendanceStatisticsByCrew(
                    crewName);

            // then
            assertThat(actual.get(AttendanceStatus.ABSENCE)).isEqualTo(9);
        }
    }

    @Nested
    @DisplayName("실패 테스트")
    class FailCases {

        @DisplayName("존재하지 않는 크루 이름이라면, 예외가 발생한다.")
        @Test
        public void validateCrewByName() throws Exception {
            // given
            final AttendanceSystem attendanceSystem = new AttendanceSystem(List.of());
            final String notFoundCrewName = "crewName";

            // when & then
            assertThatThrownBy(() -> attendanceSystem.validateCrewByName(notFoundCrewName))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ErrorMessage.CREW_NOT_FOUND.getMessage());
        }

        @DisplayName("수정할 수 없는 출석 일자라면, 예외가 발생한다.")
        @Test
        public void validateUpdateAttendanceDay() throws Exception {
            // given
            final String crewName = "name";
            final Crew crew = Crew.of(crewName, TODAY);
            final AttendanceSystem attendanceSystem = new AttendanceSystem(List.of());
            final LocalDate christmas = LocalDate.of(2024, 12, 25);

            // when & then
            assertThatThrownBy(() -> attendanceSystem.validateUpdateAttendanceDay(crewName, 25, christmas))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ErrorMessage.INVALID_DATE_FORMAT.getMessage());
        }

    }

}
