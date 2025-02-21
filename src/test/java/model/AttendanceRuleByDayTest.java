package model;

import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRuleByDayTest {

    @Test
    @DisplayName("요일과 시간을 기준으로 model.AttendanceStatus 객체 생성 테스트 (월요일 출석)")
    void test1() {
        Assertions.assertThat(AttendanceRuleByDay.calculateAttendance(1, LocalTime.of(13,0)))
                        .isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    @DisplayName("요일과 시간을 기준으로 model.AttendanceStatus 객체 생성 테스트 (금요일 결석)")
    void test2() {
        Assertions.assertThat(AttendanceRuleByDay.calculateAttendance(5, LocalTime.of(10,31)))
                .isEqualTo(AttendanceStatus.ABSENT);
    }

    @Test
    @DisplayName("정수형을 입력 받고, String 형 요일을 출력하는 메서드 테스트")
    void test3() {
        int friday = 5;
        Assertions.assertThat(AttendanceRuleByDay.
                        findDayByDayOfWeekValue(friday)).
                isEqualTo("금요일");
    }



}