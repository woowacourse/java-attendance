package attendance.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendanceStatusTest {

    @CsvSource(value = {"2024-12-12T10:00:00,CHECKIN", "2024-12-12T10:06:00,LATE", "2024-12-12T10:31:00,ABSENCE"},
            delimiterString = ",")
    @ParameterizedTest
    void 출석일자와_시간을_입력하면_맞는_상태를_반환한다(String dateTime, AttendanceStatus expectedStatus) {
        LocalDateTime attendanceDateTime = LocalDateTime.parse(dateTime);

        assertThat(AttendanceStatus.determineStatus(attendanceDateTime)).isEqualTo(expectedStatus);
    }
}
