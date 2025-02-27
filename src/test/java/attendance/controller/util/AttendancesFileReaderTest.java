package attendance.controller.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AttendancesFileReaderTest {

    @Test
    void 전체_파일_읽기_null_이면_안된다() {
        assertThat(AttendancesFileReader.read()).isNotNull();
    }
}
