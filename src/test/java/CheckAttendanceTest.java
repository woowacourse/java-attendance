import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class CheckAttendanceTest {

    // 1. 시간에 따른 출석 상태를 반환한다. 처음엔 단순히 10시 출석 기준 날짜와 관개없이 시간만으로 판단한다.
    // 2. 운영 시간이 아닐 경우 예외처리한다.
    // 3. 날짜에 따라 휴일이나 공휴일일 겨우 예외처리한다
    // 4. 날짜(월요일)에 따라 다른 시간 정책을 적용한다.
    // 5. refact : Attendance 객체 중심 코드를 위한 테스트 수정

    //1번 과정
    //출석, 결석, 지각,
    @Test
    @DisplayName("출석 확인 처리 - 출석, 지각, 결석")
    public void checkAttendanceStatusTest() {
        // given
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 17, 10, 0);
        LocalDateTime lateTime = LocalDateTime.of(2024, 12, 17, 10, 6);
        LocalDateTime absentTime = LocalDateTime.of(2024, 12, 17, 10, 40);
        // when
        Attendance attendance = new Attendance(attendanceTime);
        Attendance late = new Attendance(lateTime);
        Attendance absent = new Attendance(absentTime);
        // then
        assertThat(attendance.getAttendanceStatus()).isEqualTo(AttendanceStatus.ATTEND);
        assertThat(late.getAttendanceStatus()).isEqualTo(AttendanceStatus.LATE);
        assertThat(absent.getAttendanceStatus()).isEqualTo(AttendanceStatus.ABSENT);

    }
    @Test
    @DisplayName("월요일 출석 확인 처리 - 출석, 지각, 결석")
    public void checkMondayAttendanceStatus() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 16, 13, 0);
        LocalDateTime lateTime = LocalDateTime.of(2024, 12, 16, 13, 6);
        LocalDateTime absentTime = LocalDateTime.of(2024, 12, 16, 13, 40);
        // when
        Attendance attendance = new Attendance(attendanceTime);
        Attendance late = new Attendance(lateTime);
        Attendance absent = new Attendance(absentTime);
        // then
        assertThat(attendance.getAttendanceStatus()).isEqualTo(AttendanceStatus.ATTEND);
        assertThat(late.getAttendanceStatus()).isEqualTo(AttendanceStatus.LATE);
        assertThat(absent.getAttendanceStatus()).isEqualTo(AttendanceStatus.ABSENT);

    }

    // 운영시간 외 처리
    @Test
    @DisplayName("운영 시간 예외 처리 테스트")
    public void isOperatingTimeTest() {
        //given
        LocalDateTime beforeOperatingTime = LocalDateTime.of(2024, 12, 17, 7, 0);
        LocalDateTime afterOperatingTime = LocalDateTime.of(2024, 12, 17, 23, 40);
        //when

        assertThatThrownBy(() -> new Attendance(beforeOperatingTime))
                .hasMessage("운영시간이 아닙니다.");
        assertThatThrownBy(() -> new Attendance(afterOperatingTime))
                .hasMessage("운영시간이 아닙니다.");

    }

    @Test
    @DisplayName("휴일 처리 예외 테스트(주말, 공휴일 예외 처리")
    public void isHoliday() {
        LocalDateTime notHoliday = LocalDateTime.of(2024, 12, 17, 10, 0);
        LocalDateTime weekend = LocalDateTime.of(2024, 12, 21, 10, 0);
        LocalDateTime christmas = LocalDateTime.of(2024, 12, 25, 10, 0);

        assertThatThrownBy(() -> new Attendance(weekend))
                .hasMessageContaining("12월 21일 토요일은 운영일이 아닙니다..");
        assertThatThrownBy(() -> new Attendance(christmas))
                .hasMessageContaining("12월 25일 수요일은 운영일이 아닙니다.");
    }




}
