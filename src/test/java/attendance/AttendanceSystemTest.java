package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceSystem;
import attendance.domain.AttendanceType;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

        AttendanceRecord expectedRecord = new AttendanceRecord(crewNickname, arrivalDateTime,
                AttendanceType.ATTENDANCE);
        AttendanceRecord actualRecord = attendanceSystem.findAttendanceRecord(crewNickname,
                arrivalDateTime.toLocalDate()).get();
        assertThat(actualRecord).isEqualTo(expectedRecord);
    }

    @DisplayName("교육시간과 출석정책을 기준으로 월요일의 출석 상태를 결정한다")
    @ParameterizedTest
    @MethodSource()
    void 교육시간과_출석정책을_기준으로_월요일의_출석_상태를_결정한다(LocalDateTime arrivalDateTime, AttendanceType attendanceType) {
        String crewNickname = "쿠키";
        attendanceSystem.addAttendanceRecord(crewNickname, arrivalDateTime);

        AttendanceRecord actualRecord = attendanceSystem.findAttendanceRecord(
                crewNickname, arrivalDateTime.toLocalDate()).get();
        assertThat(actualRecord.getAttendanceType()).isEqualTo(attendanceType);
    }

    static Stream<Arguments> 교육시간과_출석정책을_기준으로_월요일의_출석_상태를_결정한다() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2025, 2, 3, 12, 0, 0), AttendanceType.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2025, 2, 3, 13, 4, 59), AttendanceType.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2025, 2, 3, 13, 5, 0), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(2025, 2, 3, 13, 5, 1), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(2025, 2, 3, 13, 29, 59), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(2025, 2, 3, 13, 30, 0), AttendanceType.ABSENCE),
                Arguments.of(LocalDateTime.of(2025, 2, 3, 13, 30, 1), AttendanceType.ABSENCE)
        );
    }

    @DisplayName("교육시간과 출석정책을 기준으로 화요일에서 금요일의 출석 상태를 결정한다")
    @ParameterizedTest
    @MethodSource()
    void 교육시간과_출석정책을_기준으로_화요일에서_금요일의_출석_상태를_결정한다(LocalDateTime arrivalDateTime, AttendanceType attendanceType) {
        String crewNickname = "쿠키";
        attendanceSystem.addAttendanceRecord(crewNickname, arrivalDateTime);

        AttendanceRecord actualRecord = attendanceSystem.findAttendanceRecord(
                crewNickname, arrivalDateTime.toLocalDate()).get();
        assertThat(actualRecord.getAttendanceType()).isEqualTo(attendanceType);
    }

    static Stream<Arguments> 교육시간과_출석정책을_기준으로_화요일에서_금요일의_출석_상태를_결정한다() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2025, 2, 7, 9, 0, 0), AttendanceType.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2025, 2, 7, 10, 4, 59), AttendanceType.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2025, 2, 7, 10, 5, 0), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(2025, 2, 7, 10, 5, 1), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(2025, 2, 7, 10, 29, 59), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(2025, 2, 7, 10, 30, 0), AttendanceType.ABSENCE),
                Arguments.of(LocalDateTime.of(2025, 2, 7, 10, 30, 1), AttendanceType.ABSENCE)
        );
    }
}