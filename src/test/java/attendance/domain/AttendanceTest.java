package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.TimeZone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTest {

    @BeforeEach
    void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    @Test
    void 출석_일자를_알려주면_출석이_생성된다() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 26, 10, 0);

        Attendance attendance = new Attendance(localDateTime);

        assertThat(attendance).isEqualTo(new Attendance(localDateTime));
    }

    @CsvSource(value = {
            "2,23,10,0,",
            "3,1,10,0,",
            "2,24,23,1"
    })
    @ParameterizedTest
    void 출석_가능_일자가_아니면_출석을_생성할_수_없다(int month, int day, int hour, int minute) {
        assertThatThrownBy(() -> new Attendance(LocalDateTime.of(2025, month, day, hour, minute)))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
