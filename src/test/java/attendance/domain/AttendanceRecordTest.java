package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fixer.AttendanceRecordFixer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRecordTest {

    @DisplayName("현재 출석 기록이 특정 날짜의 기록인지 확인한다.")
    @Test
    void 크루에_대한_출석_기록을_추가한다() {
        String nickname = "쿠키";
        LocalDateTime arrivalDateTime = LocalDateTime.of(2025, 12, 10, 8, 0, 0);
        AttendanceRecord record = new AttendanceRecord(nickname, arrivalDateTime, AttendanceStatusType.ATTENDANCE);

        LocalDate correctDate = arrivalDateTime.toLocalDate();
        assertThat(record.checkSameDate(correctDate)).isTrue();
        LocalDate incorrectDate = arrivalDateTime.toLocalDate().plusMonths(1);
        assertThat(record.checkSameDate(incorrectDate)).isFalse();
    }

    @DisplayName("현재 출석 기록이 결석기록인지 확인한다.")
    @Test
    void 현재_출석_기록이_결석기록인지_확인한다() {
        AttendanceRecord attendanceRecord = AttendanceRecordFixer.makeRecord("쿠키", AttendanceStatusType.ATTENDANCE);
        AttendanceRecord expulsionRecord = AttendanceRecordFixer.makeRecord("쿠키", AttendanceStatusType.EXPULSION);

        assertThat(attendanceRecord.isExpulsion()).isFalse();
        assertThat(expulsionRecord.isExpulsion()).isTrue();
    }
}