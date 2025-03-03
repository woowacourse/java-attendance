package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceAnalyzeTest {
    @Test
    @DisplayName("결석 횟수 결과 반환 메서드 테스트")
    void getAbsenceCountTest() {
        // given
        AttendanceHistory attendanceHistory1 = new AttendanceHistory(LocalDateTime.of(2024, 12, 23, 13, 6));
        AttendanceHistory attendanceHistory2 = new AttendanceHistory(LocalDateTime.of(2024, 12, 24, 10, 36));
        AttendanceHistory attendanceHistory3 = new AttendanceHistory(LocalDateTime.of(2024, 12, 26, 10, 36));
        AttendanceHistory attendanceHistory4 = new AttendanceHistory(LocalDateTime.of(2024, 12, 27, 10, 6));
        AttendanceHistory attendanceHistory5 = new AttendanceHistory(LocalDateTime.of(2024, 12, 30, 13, 4));
        List<AttendanceHistory> histories = List.of(attendanceHistory1, attendanceHistory2, attendanceHistory3,
                attendanceHistory4, attendanceHistory5);
        // when
        AttendanceAnalyze attendanceAnalyze = new AttendanceAnalyze(histories);
        // then
        assertThat(attendanceAnalyze.getAbsenceCount()).isEqualTo(2);
        assertThat(attendanceAnalyze.getAttendanceStatus()).isEqualTo(AttendanceStatus.WARNING);
        assertThat(attendanceAnalyze.isExpulsionTarget()).isTrue();
    }

    @Test
    @DisplayName("지각 횟수 결과 반환 메서드 테스트")
    void getLateCountTest() {
        // given
        AttendanceHistory attendanceHistory1 = new AttendanceHistory(LocalDateTime.of(2024, 12, 23, 13, 6));
        AttendanceHistory attendanceHistory2 = new AttendanceHistory(LocalDateTime.of(2024, 12, 24, 10, 36));
        AttendanceHistory attendanceHistory3 = new AttendanceHistory(LocalDateTime.of(2024, 12, 26, 10, 36));
        AttendanceHistory attendanceHistory4 = new AttendanceHistory(LocalDateTime.of(2024, 12, 27, 10, 6));
        AttendanceHistory attendanceHistory5 = new AttendanceHistory(LocalDateTime.of(2024, 12, 30, 13, 4));
        List<AttendanceHistory> histories = List.of(attendanceHistory1, attendanceHistory2, attendanceHistory3,
                attendanceHistory4, attendanceHistory5);
        // when
        AttendanceAnalyze attendanceAnalyze = new AttendanceAnalyze(histories);
        // then
        assertThat(attendanceAnalyze.getLateCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("출석 횟수 결과 반환 메서드 테스트")
    void getAttendanceCountTest() {
        // given
        AttendanceHistory attendanceHistory1 = new AttendanceHistory(LocalDateTime.of(2024, 12, 23, 13, 6));
        AttendanceHistory attendanceHistory2 = new AttendanceHistory(LocalDateTime.of(2024, 12, 24, 10, 36));
        AttendanceHistory attendanceHistory3 = new AttendanceHistory(LocalDateTime.of(2024, 12, 26, 10, 36));
        AttendanceHistory attendanceHistory4 = new AttendanceHistory(LocalDateTime.of(2024, 12, 27, 10, 6));
        AttendanceHistory attendanceHistory5 = new AttendanceHistory(LocalDateTime.of(2024, 12, 30, 13, 4));
        List<AttendanceHistory> histories = List.of(attendanceHistory1, attendanceHistory2, attendanceHistory3,
                attendanceHistory4, attendanceHistory5);
        // when
        AttendanceAnalyze attendanceAnalyze = new AttendanceAnalyze(histories);
        // then
        assertThat(attendanceAnalyze.getAttendanceCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("제적 결과 반환 테스트")
    void getAttendanceStatusTest() {
        // given
        AttendanceHistory attendanceHistory1 = new AttendanceHistory(LocalDateTime.of(2024, 12, 23, 13, 6));
        AttendanceHistory attendanceHistory2 = new AttendanceHistory(LocalDateTime.of(2024, 12, 24, 10, 36));
        AttendanceHistory attendanceHistory3 = new AttendanceHistory(LocalDateTime.of(2024, 12, 26, 10, 36));
        AttendanceHistory attendanceHistory4 = new AttendanceHistory(LocalDateTime.of(2024, 12, 27, 10, 6));
        AttendanceHistory attendanceHistory5 = new AttendanceHistory(LocalDateTime.of(2024, 12, 30, 13, 4));
        List<AttendanceHistory> histories = List.of(attendanceHistory1, attendanceHistory2, attendanceHistory3,
                attendanceHistory4, attendanceHistory5);
        // when
        AttendanceAnalyze attendanceAnalyze = new AttendanceAnalyze(histories);
        // then
        assertThat(attendanceAnalyze.getAttendanceStatus()).isEqualTo(AttendanceStatus.WARNING);
    }

    @Test
    @DisplayName("페널티 점수 결과 반환 테스트")
    void calculatePenaltyPointsTest() {
        // given
        AttendanceHistory attendanceHistory1 = new AttendanceHistory(LocalDateTime.of(2024, 12, 23, 13, 6));
        AttendanceHistory attendanceHistory2 = new AttendanceHistory(LocalDateTime.of(2024, 12, 24, 10, 36));
        AttendanceHistory attendanceHistory3 = new AttendanceHistory(LocalDateTime.of(2024, 12, 26, 10, 36));
        AttendanceHistory attendanceHistory4 = new AttendanceHistory(LocalDateTime.of(2024, 12, 27, 10, 6));
        AttendanceHistory attendanceHistory5 = new AttendanceHistory(LocalDateTime.of(2024, 12, 30, 13, 4));
        List<AttendanceHistory> histories = List.of(attendanceHistory1, attendanceHistory2, attendanceHistory3,
                attendanceHistory4, attendanceHistory5);
        // when
        AttendanceAnalyze attendanceAnalyze = new AttendanceAnalyze(histories);
        // then
        assertThat(attendanceAnalyze.calculatePenaltyPoints()).isEqualTo(8);
    }
}