package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LectureTimeTest {

    @Test
    @DisplayName("월요일에 맞는 교육 시간을 반환한다.")
    void fromTest() {
        LectureTime monday = LectureTime.from(LocalDate.of(2025, 02, 03));
        assertThat(monday.getStartTime()).isEqualTo(LocalTime.of(13, 00));
        assertThat(monday.getEndTime()).isEqualTo(LocalTime.of(18, 00));
    }
}
