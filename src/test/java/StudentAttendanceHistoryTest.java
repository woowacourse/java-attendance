import java.time.LocalDateTime;
import model.AttendanceDateTime;
import model.StudentAttendanceHistory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentAttendanceHistoryTest {
    StudentAttendanceHistory studentAttendanceHistory = new StudentAttendanceHistory();
    AttendanceDateTime addAttendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 12, 12));
    AttendanceDateTime deleteAttendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 12, 12));

    @Test
    @DisplayName("날짜를 추가하는 메서드 테스트")
    void test1() {
        studentAttendanceHistory.addAttendanceDateTime(addAttendanceDateTime);
        Assertions.assertTrue(
                studentAttendanceHistory.isContatinAttendanceDateTime(addAttendanceDateTime)
        );
    }

    @Test
    @DisplayName("날짜를 제거하는 메서드 테스트")
    void test2() {
        studentAttendanceHistory.removeAttendanceDateTime(deleteAttendanceDateTime);
        Assertions.assertFalse(
                studentAttendanceHistory.isContatinAttendanceDateTime(deleteAttendanceDateTime)
        );
    }
}
