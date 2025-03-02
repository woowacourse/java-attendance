import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class AttendanceFileReaderTest {

    @Test
    void 크루_출석_기록_파일을_불러온다() {
        AttendanceFileReader attendanceFileReader = new AttendanceFileReader();

        AttendanceManager attendanceManager = new AttendanceManager(new SystemDateProvider());
        attendanceFileReader.readFiles(attendanceManager);

        assertThat(attendanceManager.getCrewSize()).isEqualTo(5);
    }

}
