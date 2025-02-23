import domain.AttendanceBook;
import domain.Crew;
import dto.AttendanceRecordResponse;
import dto.TotalRecordsResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class CheckAttendanceRecordTest {
    @Test
    void 닉네임을_입력하면_전날까지의_크루_출석_기록을_출력해야_한다() {
        AttendanceBook attendanceBook = new AttendanceBook();

        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6)));
        attendanceBook.addNewCrew(crew1);

        attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 4), LocalTime.of(9, 7)));
        attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 5), LocalTime.of(14, 7)));

        List<AttendanceRecordResponse> attendanceRecords = crew1.getAttendanceRecords();

        TotalRecordsResponse totalRecordsResponse = TotalRecordsResponse.fromAttendanceRecords(attendanceRecords);
    }
}
