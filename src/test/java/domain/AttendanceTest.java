package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class AttendanceTest {

    @Test
    @DisplayName("크루의 이름을 입력받아 해당 크루의 출석부인지 판단")
    void isSameNameTest() {
        String name = "조로";

        Attendance attendance = Attendance.of(
                Crew.of(name),
                CheckInTimes.of(List.of())
        );

        assertThat(attendance.isSameName("조로")).isTrue();
    }

    @Test
    @DisplayName("크루원이 출석하면 시간을 추가한다")
    void addCheckInTimeTest() {
        //TODO 리팩토링

        //given
        LocalDateTime time = LocalDateTime.of(2024, 12, 16, 10, 0);
        Attendance attendance = Attendance.of(Crew.of("아무거나"), CheckInTimes.of(List.of()));
        //when
        //then
        assertThatCode(() -> attendance.checkIn(time)).doesNotThrowAnyException();
    }
}