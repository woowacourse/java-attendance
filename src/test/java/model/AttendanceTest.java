package model;

import converter.StringConverter;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.DataReader;

public class AttendanceTest {

    private final StringConverter stringConverter = new StringConverter();
    private Attendances attendances;

    @BeforeEach
    void beforeEach() {
        List<String> rawAttendances = new DataReader().readAttendances("src/test/resources/attendances.csv");
        Crews crews = stringConverter.convertToCrews(rawAttendances);
        attendances = stringConverter.convertToAttendances(rawAttendances, crews);
    }

    @Test
    @DisplayName("닉네임과 등교 시간을 입력하면 출석할 수 있다.")
    void test() {
        //given
        Crew crew = Crew.of("쿠키");
        LocalDateTime checkInTime = LocalDateTime.of(2024, 12, 13, 9, 35);
        Attendance attendance = Attendance.of(crew, checkInTime);

        //when
        attendances.checkIn(attendance);

        //then
        Assertions.assertThat(attendances.contains(attendance)).isTrue();
    }

    @Test
    @DisplayName("주말 및 공휴일에는 출석할 수 없다.")
    void test2() {
        //given
        Crew crew = Crew.of("쿠키");
        LocalDateTime checkInTime = LocalDateTime.of(2024, 12, 14, 9, 35);
        Attendance attendance = Attendance.of(crew, checkInTime);

        //when & then
        Assertions.assertThatThrownBy(() -> attendances.checkIn(attendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말 및 공휴일에는 출석할 수 없습니다.");
    }
}
