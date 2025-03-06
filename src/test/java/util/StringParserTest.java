package util;

import domain.AttendanceTime;
import java.time.LocalDate;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringParserTest {

    @DisplayName("파일 형식으로 된 문자열을 출석 정보 형태로 파싱한다")
    @Test
    void parseToAttendanceInfo() {
        // given
        String rawAttendanceInfo = "쿠키,2024-12-13 10:08";
        StringParser parser = new StringParser();
        // when
        AttendanceInfo attendanceInfo = parser.parseToAttendanceInfo(rawAttendanceInfo);
        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(attendanceInfo.crewName()).isEqualTo("쿠키");
            softly.assertThat(attendanceInfo.attendanceDate()).isEqualTo(LocalDate.of(2024, 12, 13));
            softly.assertThat(attendanceInfo.attendanceTime()).isEqualTo(new AttendanceTime(10, 8));
        });
    }
}
