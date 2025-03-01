package util;

import static org.assertj.core.api.Assertions.assertThat;

import domain.dto.AttendanceRecordDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceParserTest {

    @Test
    @DisplayName("List<String[]> 값을 바탕으로 List<AttendanceRecordDto> 반환")
    void parseAttendanceRecordTest() {
        // given
        String[] record1 = new String[]{"차니", "2024-12-02 13:00"};
        String[] record2 = new String[]{"포비", "2024-12-02 13:06"};
        List<String[]> records = List.of(record1, record2);

        // when
        List<AttendanceRecordDto> parsed = AttendanceParser.parse(records);

        // then
        assertThat(parsed).hasSize(2);
    }
}
