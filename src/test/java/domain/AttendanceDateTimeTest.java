package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class AttendanceDateTimeTest {

    /*
    일단 8시~23시 출석이라는 행위를 할 수 있고
    주말 공휴일은 출석이라는 행위를 하는게 불가능


    등교 날짜와 등교 시간을 가지고 출석 객체를 만든다.
    이때 상태는
        월요일은 13시 시작 화~금은 10시 시작
        5분까지는 출석 인정
        6분~30분까지는 지각
        31분부터 결석

    날짜와 시간을 통해서 출석 가능한 시간에 출석 객체를 생성한다
    생성된_출석_객체는_입력으로_주어진_날짜와_시간을_가진다.

    _8시_전에_출석하려고_하면_예외가_발생한다
    _23시_후에_출석하려고_하면_예외가_발생한다
    주말에_출석하려고_하면_예외가_발생한다
    공휴일에_출석하려고_하면_예외가_발생한다

    월요일_8시~13시_5분_사이에_출석하면_출석이다
    월요일_13시_5분_~_13시_30분_사이에_출석하면_지각이다
    월요일_13시_31분_부터는_출석하면_결석이다
    화~금_8시~10시_5분_사이에_출석하면_출석이다
    화~금_10시_5분_~_10시_30분_사이에_출석하면_지각이다
    화~금_10시_31분_부터는_출석하면_결석이다

     */

    @Test
    void 날짜와_시간을_통해서_출석_가능한_시간에_출석시간을_생성한다() {
        LocalDate date = LocalDate.of(2025, 2, 26);
        LocalTime time = LocalTime.of(10, 0);
        var attendance = new AttendanceDateTime(LocalDateTime.of(date, time));
        assertThat(attendance).isNotNull();
    }

    @Test
    void 출석시간은_생성_시_주어진_날짜와_시간을_가진다() {
        LocalDate date = LocalDate.of(2025, 2, 26);
        LocalTime time = LocalTime.of(10, 0);

        var attendance = new AttendanceDateTime(LocalDateTime.of(date, time));

        assertThat(attendance.getDate()).isEqualTo(date);
        assertThat(attendance.getTime()).isEqualTo(time);
    }

    @Test
    void _평일8시_전에_출석시간을_생성하려하면_예외가_발생한다() {
        LocalDateTime weekday_0759 = LocalDateTime.of(2025, 2, 26, 7, 59);
        assertThatThrownBy(() -> new AttendanceDateTime(weekday_0759))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void _평일23시_후에_출석시간을_생성하려하면_예외가_발생한다() {
        LocalDateTime weekday_2301 = LocalDateTime.of(2025, 2, 26, 23, 1);
        assertThatThrownBy(() -> new AttendanceDateTime(weekday_2301))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주말날짜로_출석시간을_생성하려하면_예외가_발생한다() {
        LocalDateTime weekend = LocalDateTime.of(2025, 2, 23, 10, 0);
        assertThatThrownBy(() -> new AttendanceDateTime(weekend))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 공휴일에_출석하려고_하면_예외가_발생한다() {
        LocalDateTime weekend = LocalDateTime.of(2025, 5, 5, 10, 0);
        assertThatThrownBy(() -> new AttendanceDateTime(weekend))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
