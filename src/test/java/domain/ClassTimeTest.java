package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ClassTimeTest {
    @Test
    @DisplayName("수업 시작 시간을 정상적으로 반환")
    void getClassStartTimeTest() {
        //given
        LocalDate mondayCheckInDate = LocalDate.of(2024, 12, 2);
        LocalDate tuesdayCheckInDate = LocalDate.of(2024, 12, 3);
        //when
        LocalTime mondayClassStartTime = ClassTime.getClassStartTime(mondayCheckInDate);
        LocalTime tuesdayClassStartTime = ClassTime.getClassStartTime(tuesdayCheckInDate);

        //then
        assertAll(
                () -> assertThat(mondayClassStartTime).isEqualTo(LocalTime.of(13, 0)),
                () -> assertThat(tuesdayClassStartTime).isEqualTo(LocalTime.of(10, 0))
        );
    }
}