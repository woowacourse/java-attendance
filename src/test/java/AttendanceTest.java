import static org.assertj.core.api.Assertions.assertThat;

import domain.Attendance;
import domain.AttendanceTime;
import domain.Crew;
import java.time.LocalDateTime;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceTest {

    @ParameterizedTest
    @CsvSource({
            "쿠키, 2, true",
            "쿠키, 3, false",
            "빙봉, 2, false",
            "빙봉, 3, false"
    })
    void 다른_출석기록과_크루_및_날짜를_비교하여_일치하는지_판단한다(String nickname, int date, boolean expected) {
        //given
        Crew crew1 = new Crew("쿠키");
        AttendanceTime time1 = new AttendanceTime(LocalDateTime.of(2024, 12, 2, 9, 30));
        Attendance attendance = new Attendance(crew1, time1);
        Crew crew2 = new Crew(nickname);
        AttendanceTime time2 = new AttendanceTime(LocalDateTime.of(2024, 12, date, 9, 30));
        //when
        boolean actual = attendance.isSameCrewAndTime(new Attendance(crew2, time2));
        //then
        assertThat(expected).isEqualTo(actual);
    }
}
