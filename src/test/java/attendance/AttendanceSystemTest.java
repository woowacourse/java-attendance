package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceSystem;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceSystemTest {

    AttendanceSystem attendanceSystem;

    @BeforeEach
    void beforeEach() {
        attendanceSystem = new AttendanceSystem();
    }

    @DisplayName("닉네임과 출석 시간으로 출석 기록을 추가할 수 있다")
    @Test
    void 닉네임과_출석_시간으로_출석_기록을_추가할_수_있다() {
        String crewNickname = "쿠키";
        LocalDateTime arrivalDateTime = LocalDateTime.of(2025, 2, 4, 8, 50, 0);

        attendanceSystem.addAttendanceRecord(crewNickname, arrivalDateTime);

        AttendanceRecord expectedRecord = new AttendanceRecord(crewNickname, arrivalDateTime);
        AttendanceRecord actualRecord = attendanceSystem.findAttendanceRecord(crewNickname,
                arrivalDateTime.toLocalDate()).get();
        assertThat(actualRecord).isEqualTo(expectedRecord);
    }

}