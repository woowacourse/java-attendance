import java.time.LocalDate;
import java.time.LocalTime;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class FileParserTest {
    
    @Test
    void 출석_기록을_정상적으로_파싱한다() {
        // given
        String attendanceData = "moko,2025-02-03,13:00";

        AttendanceRecord attendanceRecord = FileParser.parseAttendanceHistory(attendanceData);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(attendanceRecord.nickname()).isEqualTo("moko");
            softly.assertThat(attendanceRecord.date()).isEqualTo(LocalDate.of(2025, 02, 03));
            softly.assertThat(attendanceRecord.time()).isEqualTo(LocalTime.of(13, 00));
        });
    }
}
