package attendance.model.loader;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.TestUtil;
import attendance.model.Crews;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceProcessorTest {

    @DisplayName("파일 정보가 크루 인원대로 Crews에 로드된다")
    @Test
    void test_attendanceRecords() {
        // given
        Crews crews = new Crews(new ArrayList<>());
        AttendanceProcessor processor = new AttendanceProcessor(
                new AttendanceLoader(), TestUtil.getClock(), new CrewRegistry(crews)
        );

        // when
        processor.processAttendanceRecords();

        // then
        assertThat(crews.getCrews()).hasSize(5);
    }

}