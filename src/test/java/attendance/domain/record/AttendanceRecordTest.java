package attendance.domain.record;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceRecordTest {

    @DisplayName("현재 출석 기록이 특정 날짜의 기록인지 확인한다.")
    @Test
    void 크루에_대한_출석_기록을_추가한다() {
        String nickname = "쿠키";
        LocalDateTime arrivalDateTime = LocalDateTime.of(2024, 12, 10, 8, 0, 0);
        AttendanceRecord record = new AttendanceRecord(nickname, arrivalDateTime, AttendanceType.ATTENDANCE);

        LocalDate correctDate = arrivalDateTime.toLocalDate();
        assertThat(record.checkSameDate(correctDate)).isTrue();
        LocalDate incorrectDate = arrivalDateTime.toLocalDate().plusMonths(1);
        assertThat(record.checkSameDate(incorrectDate)).isFalse();
    }

    @DisplayName("현재 출석 기록이 결석기록인지 확인한다.")
    @Test
    void 현재_출석_기록이_결석기록인지_확인한다() {
        AttendanceRecord attendanceRecord = makeRecord("쿠키", AttendanceType.ATTENDANCE);
        AttendanceRecord expulsionRecord = makeRecord("쿠키", AttendanceType.EXPULSION);

        assertThat(attendanceRecord.isExpulsion()).isFalse();
        assertThat(expulsionRecord.isExpulsion()).isTrue();
    }

    @DisplayName("현재 출석 기록의 월을 확인한다.")
    @Test
    void 현재_출석_기록의_월을_확인한다() {
        String nickname = "쿠키";
        LocalDateTime arrivalDateTime = LocalDateTime.of(2024, 12, 10, 8, 0, 0);
        AttendanceRecord record = new AttendanceRecord(nickname, arrivalDateTime, AttendanceType.ATTENDANCE);

        assertThat(record.isInMonth(2024, Month.DECEMBER)).isTrue();
    }

    @DisplayName("현재 출석이 기간내의 기록인지 확인한다.")
    @ParameterizedTest
    @CsvSource({"9,false", "10,true", "11,true", "12,true", "13,false"})
    void 현재_출석_기록의_월을_확인한다(int dayOfMonth, boolean isInPeriod) {
        String nickname = "쿠키";
        LocalDateTime arrivalDateTime = LocalDateTime.of(2024, 12, dayOfMonth, 8, 0, 0);
        AttendanceRecord record = new AttendanceRecord(nickname, arrivalDateTime, AttendanceType.ATTENDANCE);

        boolean actualResult =
                record.isInPeriod(LocalDate.of(2024, 12, 10), LocalDate.of(2024, 12, 12));
        assertThat(actualResult).isEqualTo(isInPeriod);
    }

    public static AttendanceRecord makeRecord(
            String nickname, AttendanceType attendanceType
    ) {
        LocalDateTime arrivalDateTime = LocalDateTime.of(2024, 12, 9, 8, 10, 0);
        return new AttendanceRecord(nickname, arrivalDateTime, attendanceType);
    }
}