package domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {
    @DisplayName("닉네임과 등교 시간을 입력하면 출석할 수 있다.")
    @Test
    void test1() {
        AttendanceBook attendanceBook = new AttendanceBook();
        CrewName crewName = new CrewName("미미");
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 13, 9, 59));
        AttendanceRecord expectedAttendanceRecord = new AttendanceRecord();
        expectedAttendanceRecord.add(attendance);

        attendanceBook.addAttendance(crewName, attendance);

        assertThat(attendanceBook.findAttendanceRecordBy(crewName)).isEqualTo(expectedAttendanceRecord);
    }
}
