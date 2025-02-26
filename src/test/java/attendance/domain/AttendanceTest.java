package attendance.domain;


import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.fixture.LocalDateTestFixture;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("출석 정보")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AttendanceTest {
    @Test
    void 출석_정보를_저장한다() {
        int hour = 10;
        int minute = 30;
        LocalTime time = LocalTime.of(hour, minute);
        LocalDate date = LocalDateTestFixture.createRegularDate();
        Attendance attendance = new Attendance(time, date);
        LocalTime attendTime = attendance.time();

        assertThat(attendTime.getHour()).isEqualTo(10);
        assertThat(attendTime.getMinute()).isEqualTo(30);
        assertThat(attendance.status()).isEqualTo(AttendanceStatus.LATENESS);
    }
}
