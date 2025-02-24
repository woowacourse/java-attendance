package attendance.utils;

import attendance.dto.AttendanceContentDTO;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceReaderTest {

    @DisplayName("주어진 출결 관리에서 출석부와 출결 기록을 가져온다.")
    @Test
    void 주어진_출결_관리에서_출석부와_출결_기록을_가져온다() {

        // given
        List<String> content = new ArrayList<>();
        content.add("nickname,datetime");
        content.add("쿠키,2024-12-13 10:08");
        content.add("빙봉,2024-12-13 10:07");
        content.add("빙티,2024-12-13 10:07");
        content.add("이든,2024-12-13 10:07");
        content.add("이든,2024-12-13 10:10");

        // when
        AttendanceContentDTO result = AttendanceReader.getAttendanceRecordContent(content);

        // then
        Assertions.assertThat(result.names().size()).isEqualTo(4);
        Assertions.assertThat(result.attendances().size()).isEqualTo(5);
    }
}