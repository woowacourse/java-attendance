import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;


public class CheckAttendanceTest {

    // 1. 시간에 따른 출석 상태를 반환한다. 처음엔 단순히 10시 출석 기준 날짜와 관개없이 시간만으로 판단한다.
    // 2. 날짜에 따라 휴일이나 공휴일일 겨우 오류처리한다
    // 3. 날짜(월요일)에 따라 다른 시간 정책을 적용한다.

    //1번 과정
    //출석, 결석, 지각,
    @Test
    public void checkAttendanceStatusTest() {
        // given
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 17, 10, 0);
        LocalDateTime lateTime = LocalDateTime.of(2024, 12, 17, 10, 6);

        // when
        AttendanceStatus attendanceStatus = AttendancePolicy.checkAttendanceStatus(attendanceTime);
        AttendanceStatus lateStatus = AttendancePolicy.checkAttendanceStatus(lateTime);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTEND);
        assertThat(lateStatus).isEqualTo(AttendanceStatus.LATE);
    }
    //운영시간 외 처리


}
