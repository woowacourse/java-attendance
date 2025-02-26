package domain;

import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.FileReaderUtil;

class AttendanceTimeFactoryTest {

    private static final String FILE_PATH = "src/test/resources/attendance.csv";

    @Test
    @DisplayName("파일을 읽어 출석 기록 객체를 만들 수 있다.")
    void read_file_then_make_attendance_sheet() {
        // given
        AttendanceSheetsFactory attendanceSheetsFactory = new AttendanceSheetsFactory(
                new FileReaderUtil(FILE_PATH));
        List<AttendanceSheet> expected = List.of(new AttendanceSheet("테스트",
                AttendanceDateTime.from(LocalDateTime.of(2024, 12, 13, 10, 8))));
        // when
        AttendanceSheets attendanceSheets = attendanceSheetsFactory.create();

        // then
        Assertions.assertThat(attendanceSheets.findAttendanceByNickname("테스트"))
                .hasSameElementsAs(expected);
    }
}
