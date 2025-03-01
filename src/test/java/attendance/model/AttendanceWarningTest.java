package attendance.model;

import attendance.dto.AttendanceWarning;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("출석 경고 테스트")
class AttendanceWarningTest {

    @DisplayName("")
    @Test
    void name() {
        // given
        Nickname belloNickname = new Nickname("벨로");
        int lateCount = 3;
        int absentCount = 2;
        AttendanceWarning warning = new AttendanceWarning(
                belloNickname,
                1,
                2
        );

        // when
        // them
    }
}
