package model;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    @DisplayName("주말 및 공휴일에는 출석할 수 없다.")
    void test1() {
        //given
        Crew crew = Crew.of("쿠키");
        LocalDateTime checkInTime = LocalDateTime.of(2024, 12, 14, 9, 35);

        //when & then
        Assertions.assertThatThrownBy(() -> Attendance.of(crew, checkInTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말 및 공휴일에는 출석할 수 없습니다.");
    }

    @Test
    @DisplayName("운영 시작 시간 이전에는 출석을 체크할 수 없다.")
    void test2() {
        //given
        Crew crew = Crew.of("쿠키");
        LocalDateTime checkInTime = LocalDateTime.of(2024, 12, 3, 7, 0);

        //when & then
        Assertions.assertThatThrownBy(() -> Attendance.of(crew, checkInTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지금은 운영 시간이 아닙니다.");
    }

    @Test
    @DisplayName("운영 종료 시간 이후에는 출석을 체크할 수 없다.")
    void test3() {
        //given
        Crew crew = Crew.of("쿠키");
        LocalDateTime checkInTime = LocalDateTime.of(2024, 12, 3, 23, 30);

        //when & then
        Assertions.assertThatThrownBy(() -> Attendance.of(crew, checkInTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지금은 운영 시간이 아닙니다.");
    }

    @Test
    @DisplayName("출석 시간을 5분 초과하면 지각이다.")
    void test4() {
        //given
        Crew crew = Crew.of("쿠키");
        LocalDateTime checkInTime = LocalDateTime.of(2024, 12, 3, 9, 1);

        //when
        Attendance attendance = Attendance.of(crew, checkInTime);

        //then
        Assertions.assertThat(attendance.getAttendanceType()).isEqualTo(AttendanceType.BE_LATE);
    }

    @Test
    @DisplayName("출석 시간을 30분 초과하면 결석이다.")
    void test5() {
        //given
        Crew crew = Crew.of("쿠키");
        LocalDateTime checkInTime = LocalDateTime.of(2024, 12, 3, 9, 31);

        //when
        Attendance attendance = Attendance.of(crew, checkInTime);

        //then
        Assertions.assertThat(attendance.getAttendanceType()).isEqualTo(AttendanceType.ABSENCE);
    }
}
