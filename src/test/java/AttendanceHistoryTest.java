import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.AttendanceHistory;
import domain.AttendanceResult;
import domain.Attendances;
import domain.Crew;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    private AttendanceHistory attendanceHistory;
    private Crew crew;

    @BeforeEach
    void setUp() {
        Map<Crew, Attendances> attendanceMap = new HashMap<>();
        crew = new Crew("벡터");
        attendanceMap.put(crew, new Attendances(new ArrayList<>())); // ArrayList 사용하여 변경 가능하게 설정
        attendanceHistory = new AttendanceHistory(attendanceMap);
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록 - 출석")
    void attendanceTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 10, 0);
        AttendanceResult attendanceResult = attendanceHistory.checkAttendance(crew, attendanceTime);
        assertThat(attendanceResult).isEqualTo(AttendanceResult.ATTENDANCE);
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록 - 지각")
    void lateTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 10, 6);
        AttendanceResult attendanceResult = attendanceHistory.checkAttendance(crew, attendanceTime);
        assertThat(attendanceResult).isEqualTo(AttendanceResult.LATE);
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록 - 결석")
    void absentTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 10, 35);
        AttendanceResult attendanceResult = attendanceHistory.checkAttendance(crew, attendanceTime);
        assertThat(attendanceResult).isEqualTo(AttendanceResult.ABSENT);
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록(월요일) - 출석")
    void mondayAttendanceTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 13, 0);
        AttendanceResult attendanceResult = attendanceHistory.checkAttendance(crew, attendanceTime);
        assertThat(attendanceResult).isEqualTo(AttendanceResult.ATTENDANCE);
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록(월요일) - 지각")
    void mondayLateTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 13, 10);
        AttendanceResult attendanceResult = attendanceHistory.checkAttendance(crew, attendanceTime);
        assertThat(attendanceResult).isEqualTo(AttendanceResult.LATE);
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록(월요일) - 결석")
    void mondayAbsentTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 13, 35);
        AttendanceResult attendanceResult = attendanceHistory.checkAttendance(crew, attendanceTime);
        assertThat(attendanceResult).isEqualTo(AttendanceResult.ABSENT);
    }

    @Test
    @DisplayName("주말 출석 예외 처리")
    void testSundayException() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 7, 10, 0);
        assertThatThrownBy(() -> attendanceHistory.checkAttendance(crew, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("크리스마스 예외처리")
    void validateHolidayException() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 25, 10, 0);
        assertThatThrownBy(() -> attendanceHistory.checkAttendance(crew, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("운영시간 외 출석 - 이른 시간")
    void testValidateOperatingTime_Early() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 7, 0);
        assertThatThrownBy(() -> attendanceHistory.checkAttendance(crew, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("운영시간 외 출석 - 늦은 시간")
    void testValidateOperatingTime_Late() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 23, 5);
        assertThatThrownBy(() -> attendanceHistory.checkAttendance(crew, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("중복 출석 예외 처리")
    void testDuplicateAttendanceException() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 10, 0);
        attendanceHistory.checkAttendance(crew, attendanceTime);
        assertThatThrownBy(() -> attendanceHistory.checkAttendance(crew, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 이미 출석했습니다.");
    }
}