import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {
    @Test
    @DisplayName("특정 학생의 출석 시간 가져오기 구현")
    void 특정_학생의_출석_시간_가져오기_구현() throws IOException {
        String name = "빙티";
        FileInformationProvider fileProvider = new FileInformationProvider();
        AttendanceBook attendanceBook = new AttendanceBook(fileProvider.loadStudentAttendance());
        Map<LocalDate, LocalTime> result = attendanceBook.findStudentAttendanceTimeRecord(name);

        Map<LocalDate, LocalTime> expect = Map.of(
                LocalDate.of(2024,12,3), LocalTime.of(10,7),
                LocalDate.of(2024,12,2), LocalTime.of(13,0)
        );
        assertThat(expect).isEqualTo(result);
    }
}
