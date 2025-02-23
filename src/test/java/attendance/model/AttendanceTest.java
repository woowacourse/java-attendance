package attendance.model;

import static attendance.model.AttendanceTestFixtures.createAttendanceInRawDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("출석 테스트")
class AttendanceTest {

    @DisplayName("주말인 경우 출석을 생성할때 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenWeekendAttendance() {
        // given
        Crew pobi = AttendanceTestFixtures.POBI;
        LocalDateTime weekend = AttendanceTestFixtures.WEEKEND_SUNDAY;

        // when & then
        assertThatThrownBy(() -> new Attendance(pobi, weekend))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말인 경우 출석할 수 없습니다.");
    }

    @DisplayName("법정 공휴일인 경우 출석을 생성할때 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenHolidayAttendance() {
        // given
        Crew pobi = AttendanceTestFixtures.POBI;
        LocalDateTime legalHoliday = AttendanceTestFixtures.CHRISTMAS;

        // when & then
        assertThatThrownBy(() -> new Attendance(pobi, legalHoliday))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("법정 공휴일에는 출석할 수 없습니다.");
    }

    @DisplayName("크루와 출석 날짜가 같은지 비교할 수 있다.")
    @Test
    void equalsTest() {
        // given
        Crew pobi = AttendanceTestFixtures.POBI;
        String dateTime = "2024-12-02 10:01";

        // when
        Attendance attendance1 = createAttendanceInRawDateTime(pobi, dateTime);
        Attendance attendance2 = createAttendanceInRawDateTime(pobi, dateTime);

        // then
        assertThat(attendance1)
                .isEqualTo(attendance2);
    }
}
