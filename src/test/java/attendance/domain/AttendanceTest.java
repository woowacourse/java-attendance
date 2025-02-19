package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}