package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceStatusTest {

    @DisplayName("교육시간과 입실시간을 비교하여 출석 상태를 결정한다")
    @ParameterizedTest
    @CsvSource(value = {
            "5,ATTEND", "6,LATE", "29,LATE", "31,ABSENT"
    })
    void 교육시간_입실시간을_비교하여_출석_상태를_결정한다(int minute, AttendanceStatus attendanceStatus) {

        // given
        LocalDateTime arrivalTime = LocalDateTime.of(2025, 2, 28, 10, minute);

        // when
        AttendanceStatus currentAttendanceStatus = AttendanceStatus.getStatusByTime(arrivalTime);

        // then
        assertThat(currentAttendanceStatus).isEqualTo(attendanceStatus);
    }

}
