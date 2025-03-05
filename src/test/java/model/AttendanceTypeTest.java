package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTypeTest {

    @DisplayName("출석 날짜와 출석 시간으로 출석 타입을 얻는다")
    @ParameterizedTest
    @CsvSource({
            "2024-12-02, 10:06, NONE",
            "2024-12-02, 13:05, NONE",
            "2024-12-02, 13:06, LATE",
            "2024-12-02, 13:30, LATE",
            "2024-12-02, 13:31, ABSENT",
            "2024-12-04, 09:50, NONE",
            "2024-12-04, 10:05, NONE",
            "2024-12-04, 10:06, LATE",
            "2024-12-04, 10:30, LATE",
            "2024-12-04, 10:31, ABSENT",
    })
    void fromTest(final LocalDate date, final LocalTime time, final AttendanceType type) {
        AttendanceDate attendanceDate = new AttendanceDate(date);
        AttendanceTime attendanceTime = new AttendanceTime(time);

        assertThat(AttendanceType.from(attendanceDate, attendanceTime)).isEqualTo(type);
    }
}
