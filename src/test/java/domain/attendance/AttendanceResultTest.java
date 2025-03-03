package domain.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.crew.CrewStatus;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceResultTest {

    @ParameterizedTest
    @CsvSource({"1,PASS", "2,WARNING", "3,CONSULT", "6,EXPEL"})
    @DisplayName("크루 전체 출결 기록 상태 계산 기능 테스트")
    void 크루_전체_출결_기록_상태_계산_기능_테스트(int absentCount, String crewStatus) {
        // given
        AttendanceResult attendanceResult = generateAttendanceResult(absentCount);
        // when & then
        assertEquals(CrewStatus.valueOf(crewStatus), attendanceResult.getCrewStatus());
    }

    private AttendanceResult generateAttendanceResult(int absentCount) {
        Map<AttendanceStatus, Integer> attendanceStatus = new HashMap<>();
        attendanceStatus.put(AttendanceStatus.ATTEND, 0);
        attendanceStatus.put(AttendanceStatus.LATE, 0);
        attendanceStatus.put(AttendanceStatus.ABSENT, absentCount);
        return new AttendanceResult(attendanceStatus);
    }
}
