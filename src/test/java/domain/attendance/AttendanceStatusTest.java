package domain.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceStatusTest {

    @ParameterizedTest
    @CsvSource({"4,59,ATTEND", "5,0,LATE", "29, 59,LATE", "30,0,ABSENT"})
    @DisplayName("월요일 출결 상태 계산 기능 테스트")
    void 월요일_출결_상태_계산_기능_테스트(int minute, int second, String attendanceStatus) {
        // given
        LocalDateTime attendanceTime = LocalDateTime.of(2025, 2, 24, 13, minute, second);
        // when & then
        assertEquals(AttendanceStatus.valueOf(attendanceStatus), AttendanceStatus.findStatus(attendanceTime));
    }

    @ParameterizedTest
    @CsvSource({"4,59,ATTEND", "5,0,LATE", "29, 59,LATE", "30,0,ABSENT"})
    @DisplayName("평일 출결 상태 계산 기능 테스트")
    void 평일_출결_상태_계산_기능_테스트(int minute, int second, String attendanceStatus) {
        // given
        LocalDateTime attendanceTime = LocalDateTime.of(2025, 2, 25, 10, minute, second);
        // when & then
        assertEquals(AttendanceStatus.valueOf(attendanceStatus), AttendanceStatus.findStatus(attendanceTime));
    }

    @ParameterizedTest
    @CsvSource({"4,59,출석", "5,0,지각", "30,0,결석"})
    @DisplayName("출결 상태 설명 기능 테스트")
    void 출결_상태_설명_기능_테스트(int minute, int second, String attendanceStatus) {
        // given
        LocalDateTime attendanceTime = LocalDateTime.of(2025, 2, 25, 10, minute, second);
        // when & then
        assertEquals(attendanceStatus, AttendanceStatus.findStatus(attendanceTime).getDescription());
    }
}
