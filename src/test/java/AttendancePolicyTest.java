import static org.assertj.core.api.Assertions.*;

import domain.AttendancePolicy;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AttendancePolicyTest {

    @DisplayName("입력 날짜가 출석 가능다면 true를 반환한다")
    @Test
    void isPossibleAttendance() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 13);

        // when
        boolean isPossibleAttendance = AttendancePolicy.isPossibleAttendance(localDate);

        // then
        assertThat(isPossibleAttendance).isTrue();
    }

    @DisplayName("입력 날짜가 출석 가능하지 않다면 false를 반환한다")
    @Test
    void isNotPossibleAttendance() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 14);

        // when
        boolean isPossibleAttendance = AttendancePolicy.isPossibleAttendance(localDate);

        // then
        assertThat(isPossibleAttendance).isFalse();
    }

    @DisplayName("입력 날짜가 공휴일일 경우 false를 반환한다")
    @Test
    void isNotPossibleAttendanceWithHoliday() {
        // given
        LocalDate holiday = LocalDate.of(2024, 12, 25);

        // when
        boolean isPossibleAttendance = AttendancePolicy.isPossibleAttendance(holiday);

        // then
        assertThat(isPossibleAttendance).isFalse();
    }

    @DisplayName("입력 날짜의 출석 시작 시각을 조회할 수 있다")
    @Test
    void retrieveAttendanceStartHour() {
        // given
        LocalDate attendanceDate = LocalDate.of(2024, 12, 13);

        // when
        int startHour = AttendancePolicy.retrieveStartHourByDate(attendanceDate);

        // then
        assertThat(startHour).isEqualTo(10);
    }

    @DisplayName("출석 시작 시각 조회시 입력 날짜가 공휴일 혹은 주말이라면 예외를 발생시킨다")
    @ParameterizedTest
    @ValueSource(ints = {14, 25})
    void retrieveAttendanceEndHourWithHoliday(int notSchoolDate) {
        // given
        LocalDate holiday = LocalDate.of(2024, 12, notSchoolDate);

        // when && then
        assertThatThrownBy(
                () -> AttendancePolicy.retrieveStartHourByDate(holiday)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 주말 혹은 공휴일에는 출석 시각을 조회할 수 없습니다");
    }

    @DisplayName("입력 날짜가 등교일이 아니라면 예외를 발생시킨다")
    @Test
    void validateSchoolDay() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 14);
        // when & then
        Assertions.assertThatThrownBy(
                () -> AttendancePolicy.validateSchoolDay(date)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
