package domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceDateTest {
    @DisplayName("휴일일 때 출석일자 생성이 안 된다")
    @Test
    void test() {
        // given
        LocalDateTime christmasDateTime = LocalDateTime.of(2024, 12, 25, 0, 0);

        // when && then
        assertThatThrownBy(() -> new AttendanceDate(christmasDateTime))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("");
    }

    @DisplayName("주말일 때 출석일자 생성이 안 된다")
    @Test
    void test1(){
        LocalDateTime weekendDateTime = LocalDateTime.of(
                2025,
                2,
                16,
                0,
                0);

        assertThatThrownBy(() -> new AttendanceDate(weekendDateTime))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("");
    }

    @DisplayName("평일일 때 출석일자 생성")
    @Test
    void test2(){
        LocalDateTime weekDaysDateTime = LocalDateTime.of(
                2025,
                2,
                17,
                0,
                0);

        assertThat(new AttendanceDate(weekDaysDateTime)).isInstanceOf(AttendanceDate.class);
    }

    @DisplayName("Attendance Date 5분 이내 출석여부 확인")
    @Test
    void test3(){
        //given
        LocalDateTime localDateTime = LocalDateTime.of(
                2025,
                2,
                17,
                13,
                5);
        AttendanceDate attendanceDate = new AttendanceDate(localDateTime);

        //when
        AttendanceState attendanceState = attendanceDate.calculateAttendanceState();

        //then
        assertThat(attendanceState).isEqualTo(AttendanceState.ATTENDANCE);
    }
}
