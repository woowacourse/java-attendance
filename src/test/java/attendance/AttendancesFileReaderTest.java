package attendance;

import attendance.reader.AttendancesFileReader;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AttendancesFileReaderTest {

    @Test
    void 전체_파일_읽기_null_이면_안된다() {
        AttendancesFileReader attendancesFileReader = new AttendancesFileReader();
        Map<String, List<LocalDateTime>> read = attendancesFileReader.read();
        assertThat(read).isNotNull();
    }
}
