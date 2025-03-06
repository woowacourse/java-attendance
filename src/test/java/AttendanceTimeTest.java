import static org.assertj.core.api.Assertions.*;

import domain.AttendanceTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AttendanceTimeTest {

    @DisplayName("입력한 시간과 분으로 출석 시간을 생성한다")
    @Test
    void createByHourAndMinute() {
        // given
        int hour = 10;
        int minute = 30;

        // when
        AttendanceTime attendanceTime = new AttendanceTime(hour, minute);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(attendanceTime)
                    .extracting("hour").isEqualTo(10);
            softly.assertThat(attendanceTime)
                    .extracting("minute").isEqualTo(30);
        });
    }

    @DisplayName("출석시간 생성시 분의 범위를 벗어나면 예외를 발생시킨다")
    @Test
    void minuteOutOfRange() {
        // given
        int hour = 10;
        int minute = 100;

        // when & then
        assertThatIllegalArgumentException().isThrownBy(
                () -> new AttendanceTime(hour, minute)
        ).withMessage("[ERROR] 분의 범위를 벗어났습니다");
    }

    @DisplayName("출석시간 생성시 캠퍼스 운영시간외 출석을 하면 예외를 발생시킨다")
    @Test
    void OperationTimeOutOfRange() {
        // given
        int hour = 6;
        int minute = 30;

        // when & then
        assertThatIllegalArgumentException().isThrownBy(
                () -> new AttendanceTime(hour, minute)
        ).withMessage("[ERROR] 현재 운영시간이 아닙니다");
    }

    @DisplayName("입력한 시간이 출석 시간과 동일하다면 true를 반환한다")
    @Test
    void isEqualHour() {
        // given
        int hour = 10;
        int minute = 3;
        AttendanceTime attendanceTime = new AttendanceTime(hour, minute);
        int compareHour = 10;

        //when
        boolean isEqualsHour = attendanceTime.isEqualHour(compareHour);

        //then
        assertThat(isEqualsHour).isTrue();
    }

    @DisplayName("입력한 시간이 출석 시간과 동일하지 않다면 false를 반환한다")
    @Test
    void isNotEqualHour() {
        // given
        int hour = 10;
        int minute = 3;
        AttendanceTime attendanceTime = new AttendanceTime(hour, minute);
        int compareHour = 9;

        //when
        boolean isEqualsHour = attendanceTime.isEqualHour(compareHour);

        //then
        assertThat(isEqualsHour).isFalse();
    }

    @DisplayName("출석 분이 입력한 분 이후라면 true를 반환한다")
    @Test
    void isAfterMinute() {
        // given
        int hour = 10;
        int minute = 5;
        AttendanceTime attendanceTime = new AttendanceTime(hour, minute);
        int compareMinute = 2;

        //when
        boolean isAfterMinute = attendanceTime.isAfterMinute(compareMinute);

        //then
        assertThat(isAfterMinute).isTrue();
    }

    @DisplayName("출석 분이 입력한 분보다 작다면 false를 반환한다")
    @Test
    void isNotAfterMinute() {
        // given
        int hour = 10;
        int minute = 5;
        AttendanceTime attendanceTime = new AttendanceTime(hour, minute);
        int compareMinute = 7;

        //when
        boolean isAfterMinute = attendanceTime.isAfterMinute(compareMinute);

        //then
        assertThat(isAfterMinute).isFalse();
    }

    @DisplayName("출석 시간이 입력한 시간보다 크다면 true를 반환한다")
    @Test
    void isAfterHour() {
        // given
        int hour = 11;
        int minute = 5;
        AttendanceTime attendanceTime = new AttendanceTime(hour, minute);
        int compareHour = 10;

        //when
        boolean isAfterHour = attendanceTime.isAfterHour(compareHour);

        //then
        assertThat(isAfterHour).isTrue();
    }

    @DisplayName("출석 시간이 입력한 시간보다 같거나 작다면 false를 반환한다")
    @ParameterizedTest
    @ValueSource(ints = {9, 10})
    void isNotAfterHour(int value) {
        // given
        int hour = value;
        int minute = 3;
        AttendanceTime attendanceTime = new AttendanceTime(hour, minute);
        int compareHour = 10;

        //when
        boolean isAfterHour = attendanceTime.isAfterHour(compareHour);

        //then
        assertThat(isAfterHour).isFalse();
    }

    @DisplayName("출석 분이 입력한 분과 같거나 작으면 true를 반환한다")
    @ParameterizedTest
    @ValueSource(ints = {9, 10})
    void isEqualAndBeforeMinute(int value) {
        // given
        int hour = 10;
        int minute = value;
        AttendanceTime attendanceTime = new AttendanceTime(hour, minute);
        int compareMinute = 10;

        //when
        boolean isEqualAndBeforeMinute = attendanceTime.isEqualAndBeforeMinute(compareMinute);

        //then
        assertThat(isEqualAndBeforeMinute).isTrue();
    }

    @DisplayName("출석 분이 입력한 분보다 크다면 false를 반환한다")
    @Test
    void isNotEqualAndBeforeMinute() {
        // given
        int hour = 10;
        int minute = 10;
        AttendanceTime attendanceTime = new AttendanceTime(hour, minute);
        int compareMinute = 5;

        //when
        boolean isEqualAndBeforeMinute = attendanceTime.isEqualAndBeforeMinute(compareMinute);

        //then
        assertThat(isEqualAndBeforeMinute).isFalse();
    }

    @DisplayName("출석 시간이 입력 시간보다 작다면 true를 반환한다")
    @Test
    void isBeforeDateHour() {
        // given
        int hour = 9;
        int minute = 3;
        AttendanceTime attendanceTime = new AttendanceTime(hour, minute);
        int compareHour = 10;

        //when
        boolean isBeforeHour = attendanceTime.isBeforeHour(compareHour);

        //then
        assertThat(isBeforeHour).isTrue();
    }


    @DisplayName("출석 시간이 입력 시간보다 같거나 크다면 false를 반환한다")
    @ParameterizedTest
    @ValueSource(ints = {10, 11})
    void isBeforeDateHour(int value) {
        // given
        int hour = value;
        int minute = 3;
        AttendanceTime attendanceTime = new AttendanceTime(hour, minute);
        int compareHour = 10;

        //when
        boolean isBeforeHour = attendanceTime.isBeforeHour(compareHour);

        //then
        assertThat(isBeforeHour).isFalse();
    }
}
