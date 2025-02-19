package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTest {

    @DisplayName("주어진 출결 기록과 같은 날짜라면 true를 반환한다")
    @Test
    void 주어진_출결_기록과_같은_날짜라면_true를_반환한다() {

        //given
        Attendance attendance = new Attendance("체체", LocalDateTime.now());
        Attendance attendance2 = new Attendance("체체", LocalDateTime.now());

        //when
        boolean isEqual = attendance.isAlreadyAttendance(attendance2);

        //then
        assertThat(isEqual).isTrue();
    }

    @DisplayName("주어진 출결 기록과 다른 날짜라면 false를 반환한다")
    @Test
    void 주어진_출결_기록과_다른_날짜라면_false를_반환한다() {

        //given
        Attendance attendance = new Attendance("체체", LocalDateTime.now());
        Attendance attendance2 = new Attendance("체체", LocalDateTime.now().plusHours(24));

        //when
        boolean isEqual = attendance.isAlreadyAttendance(attendance2);

        //then
        assertThat(isEqual).isFalse();
    }

    @DisplayName("주어진 시간으로 출석 기록을 변경한다.")
    @Test
    void 주어진_시간으로_출석_기록을_변경한다() {

        // given
        LocalDateTime now = LocalDateTime.now();

        Attendance attendance = new Attendance("체체", now);
        // when
        attendance.modifyAttendanceTime(now.plusHours(1));
        // then
        assertThat(attendance.getAttendanceTime()).isEqualTo(now.plusHours(1));
    }

    @ParameterizedTest
    @CsvSource(value = {"2024,12,13,10,0,출석", "2024,12,13,10,6,지각", "2024,12,13,10,31,결석",
            "2024,12,9,12,30,출석", "2024,12,9,13,6,지각", "2024,12,9,13,31,결석"})
    void 시간에_맞는_출결_상태를_갖는다(int year, int month, int day, int hour, int minute, String result) {

        // given
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);
        Attendance attendance = new Attendance("체체", dateTime);

        // when && then
        assertThat(attendance.getAttendanceStatus()).isEqualTo(result);
    }

    @DisplayName("주말에는 출석하지 않는다.")
    @Test
    void 주말에는_출석하지_않는다() {

        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 2, 16, 10, 10);

        // when & then
        String message = String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.",
                dateTime.getMonthValue(), dateTime.getDayOfMonth(), dateTime.getDayOfWeek().getDisplayName(
                        TextStyle.FULL, Locale.KOREAN));
        assertThatThrownBy(() -> new Attendance("체체", dateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }

    @DisplayName("캠퍼스 운영 시간에만 출석한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "7,59",
            "23,1"
    }, delimiter = ',')
    void 캠퍼스_운영_시간에만_출석한다(int hour, int minute) {

        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 2, 19, hour, minute);

        // when & then
        assertThatThrownBy(() -> new Attendance("체체", dateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석 가능한 시간이 아닙니다.");
    }
}