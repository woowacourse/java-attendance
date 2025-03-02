package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceRecordGeneratorTest {


    @Test
    @DisplayName("연도, 월, 일 기준으로 출석 기록을 초기화 한다")
    void test1() {
        //given
        final int year = 2024;
        final int month = 12;
        final int day = 1;
        final LocalDate localDate = LocalDate.of(year, month, day);

        //when
        final Map<LocalDate, AttendanceRecord> attendanceMap = AttendanceRecordGenerator.generate(localDate);

        //then
        assertThat(attendanceMap).isNotEmpty();

    }
}
