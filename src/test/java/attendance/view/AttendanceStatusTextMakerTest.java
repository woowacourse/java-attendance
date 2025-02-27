package attendance.view;

import attendance.domain.AttendanceStatusChecker.AttendanceStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

public class AttendanceStatusTextMakerTest {

    @CsvSource({
            "ATTENDANCE, 출석",
            "LATE, 지각",
            "ABSENT, 결석",
    })
    @Test
    void 출석상태를_알려주면_그에_해당하는_문자열을_알려준다(AttendanceStatus attendanceStatus, String expected) {
        // Given
        AttendanceStatusTextMaker attendanceStatusTextMaker = new AttendanceStatusTextMaker();

        // When
        String actual = attendanceStatusTextMaker.make(attendanceStatus);

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}
