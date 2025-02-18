package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceStatus;
import attendance.repository.AttendanceFileRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceFileRepositoryTest {
    @Test
    @DisplayName("출석 데이터 불러오기 테스트")
    void testLoadAttendance(){
        AttendanceFileRepository attendanceFileRepository = new AttendanceFileRepository("test.csv");
        attendanceFileRepository.saveFromAttendanceFile();
        AttendanceManager attendanceManager = attendanceFileRepository.getAttendanceManager();

        assertThat(
                attendanceManager.getAttendanceResult("투다",LocalDate.of(2024,12,13)).attendanceStatus()
        ).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    @DisplayName("출석 데이터 지각 테스트")
    void testLate(){
        AttendanceFileRepository attendanceFileRepository = new AttendanceFileRepository("test.csv");
        attendanceFileRepository.saveFromAttendanceFile();
        AttendanceManager attendanceManager = attendanceFileRepository.getAttendanceManager();

        assertThat(
                attendanceManager.getAttendanceResult("투다", LocalDate.of(2024,12,14)).attendanceStatus()
        ).isEqualTo(AttendanceStatus.RATE);
    }
}
