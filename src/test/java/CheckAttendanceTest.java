import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class CheckAttendanceTest {

    // 1. 시간에 따른 출석 상태를 반환한다. 처음엔 단순히 10시 출석 기준 날짜와 관개없이 시간만으로 판단한다.
    // 2. 운영 시간이 아닐 경우 예외처리한다.
    // 3. 날짜에 따라 휴일이나 공휴일일 겨우 예외처리한다
    // 4. 날짜(월요일)에 따라 다른 시간 정책을 적용한다.

    //1번 과정
    //출석, 결석, 지각,
    @Test
    @DisplayName("출석, 지각, 결석 테스트 (월요일 고려 x)")
    public void checkAttendanceStatusTest() {
        // given
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 17, 10, 0);
        LocalDateTime lateTime = LocalDateTime.of(2024, 12, 17, 10, 6);
        LocalDateTime absentTime = LocalDateTime.of(2024, 12, 17, 10, 40);

        // when
        AttendanceStatus attendanceStatus = AttendancePolicy.checkAttendanceStatus(attendanceTime);
        AttendanceStatus lateStatus = AttendancePolicy.checkAttendanceStatus(lateTime);
        AttendanceStatus absentStatus = AttendancePolicy.checkAttendanceStatus(absentTime);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTEND);
        assertThat(lateStatus).isEqualTo(AttendanceStatus.LATE);
        assertThat(absentStatus).isEqualTo(AttendanceStatus.ABSENT);
    }

    // 운영시간 외 처리
    @Test
    @DisplayName("운영 시간 예외 처리 테스트")
    public void isOperatingTimeTest() {
        //given
        LocalDateTime inOperatingTime = LocalDateTime.of(2024, 12, 17, 7, 0);
        LocalDateTime beforeOperatingTime = LocalDateTime.of(2024, 12, 17, 7, 0);
        LocalDateTime afterOperatingTime = LocalDateTime.of(2024, 12, 17, 23, 44);
        //when
        Boolean isOperating = AttendancePolicy.isOperatingTime(inOperatingTime);
        Boolean notOperating = AttendancePolicy.isOperatingTime(beforeOperatingTime);
        Boolean notOperating2 = AttendancePolicy.isOperatingTime(afterOperatingTime);

        assertThat(isOperating).isTrue();
        assertThat(notOperating).isFalse();
        assertThat(notOperating2).isFalse();


    }


}
