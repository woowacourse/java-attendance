package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendancesTest {

    @CsvSource(value = {
            "26,true", "27,false"
    })
    @ParameterizedTest
    void 날짜를_알려주면_출석_기록이_존재하는지_알려준다(int day, boolean expected) {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        Attendances attendances = new Attendances(List.of(attendance));

        assertThat(attendances.hasAttendanceByLocalDate(LocalDate.of(2025, 2, day))).isEqualTo(expected);
    }

}
