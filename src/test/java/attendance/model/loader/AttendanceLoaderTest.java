package attendance.model.loader;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.loader.AttendanceLoader.RawAttendanceEntry;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceLoaderTest {

    @DisplayName("파일의 데이터 개수만큼 데이터가 load된다")
    @Test
    void test_load_size() {
        // given
        AttendanceLoader attendanceLoader = new AttendanceLoader();

        // when
        attendanceLoader.load();

        // then
        List<RawAttendanceEntry> allEntries = attendanceLoader.getAllEntries();
        assertThat(allEntries).hasSize(41);
    }
}