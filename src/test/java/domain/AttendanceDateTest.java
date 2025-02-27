package domain;

import domain.policy.attend.AttendancePolicy;
import domain.policy.attend.date.AttendanceDatePolicy;
import domain.policy.attend.time.AttendanceTimePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceDateTest {

    private final AttendancePolicy attendancePolicy = new AttendancePolicy(
            new AttendanceDatePolicy(),
            new AttendanceTimePolicy()
    );

    @Test
    @DisplayName("출석 날짜는 출석 정책을 통해서 출석 가능한지 확인할 수 있다.")
    void cannotAttendDateThrowException() {
        // given
        LocalDate holiday = LocalDate.of(2024, 12, 25); // 크리스마스 (공휴일)
        LocalDate weekend = LocalDate.of(2024, 12, 15); // 토요일
        LocalDate weekday = LocalDate.of(2024, 12, 16); // 월요일 (정상 근무일, 늦잠 자는 날)

        // when
        // then
        assertAll(
                () -> assertThatThrownBy(() -> AttendanceDate.of(holiday, attendancePolicy))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("등교일이 아닙니다."),

                () -> assertThatThrownBy(() -> AttendanceDate.of(weekend, attendancePolicy))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("등교일이 아닙니다."),

                () -> assertThatCode(() -> AttendanceDate.of(weekday, attendancePolicy))
                        .doesNotThrowAnyException()
        );
    }

    @Test
    @DisplayName("내부 값이 같다면, 같은 출석 날짜으로 취급한다.")
    void treatedAsTheSameObjectIfValuesAreTheSame() {
        // given
        AttendanceDate attendanceDate1 = AttendanceDate.of(LocalDate.of(2024, 12, 13), attendancePolicy);
        AttendanceDate attendanceDate2 = AttendanceDate.of(LocalDate.of(2024, 12, 13), attendancePolicy);

        // when
        // then
        assertThat(attendanceDate1).isEqualTo(attendanceDate2);
    }
}
