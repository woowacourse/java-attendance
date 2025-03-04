package model;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceDateTimeTest {

    @ParameterizedTest
    @DisplayName("String 형태의 input 값을 LocalDateTime으로 형변환이 잘 되는 지")
    @ValueSource(strings = {"2025-02-06 09:06", "2025-2-06 09:06", "2025-02-6 09:06", "2025-02-06 9:06",
            "2025-02-06 09:6"})
    void ofFromDateTimeString(String dateTimeInput) {

        // given
        final LocalDateTime expected = LocalDateTime.of(2025, 2, 6, 9, 6);

        // when
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(dateTimeInput);
        LocalDateTime dateTime = attendanceDateTime.getDateTime();

        // then
        Assertions.assertThat(dateTime).isEqualTo(expected);
    }
}
