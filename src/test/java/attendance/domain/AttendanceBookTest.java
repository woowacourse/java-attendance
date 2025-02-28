package attendance.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class AttendanceBookTest {

    @Test
    public void 출석부_생성() {
        //given
        Crew crew = new Crew("우가");
        Crews crews = new Crews(List.of(crew));
        AttendanceTime attendanceTime = new AttendanceTime(LocalDateTime.of(2025, 2, 28, 9, 59));
        AttendanceRecord attendanceRecord = new AttendanceRecord(List.of(attendanceTime));

        Map<Crew, AttendanceRecord> originalAttendanceBook = new HashMap<>();
        originalAttendanceBook.put(crews.findCrew("우가"), attendanceRecord);

        assertDoesNotThrow(() -> new AttendanceBook(originalAttendanceBook));
    }
}
