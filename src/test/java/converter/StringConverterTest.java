package converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import model.Attendance;
import model.Attendances;
import model.Crew;
import model.Crews;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringConverterTest {

    @Test
    void 닉네임과_시간을_오늘의_출석으로_변환한다() {
        //given
        String nickname = "쿠키";
        StringConverter stringConverter = new StringConverter();
        Crew crew = Crew.of(nickname);
        LocalDate today = LocalDate.of(2024, 12, 2);
        LocalDateTime time = LocalDateTime.of(today, LocalTime.of(9, 44));
        Attendance expected = Attendance.of(crew, time);

        //when
        Attendance actual = stringConverter.convertToAttendance(crew, "09:44", today);

        //then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("평일 출석 확인 테스트")
    void test2() {
        //given
        StringConverter stringConverter = new StringConverter();

        List<String> rawAttendances = List.of(
                "쿠키,2024-12-13 10:08"
        );
        Crew crew1 = Crew.of("쿠키");
        Crews crews = Crews.of(Set.of(crew1));

        Attendance attendance1 = Attendance.of(crew1, LocalDateTime.of(2024, 12, 13, 10, 8));

        //when
        Attendances attendances = stringConverter.convertToAttendances(rawAttendances, crews);

        //then
        Assertions.assertThat(attendances.getAttendances().get(0).getCrew().getNickname()).isEqualTo("쿠키");
        Assertions.assertThat(attendances.getAttendances().get(0).getCheckInTime())
                .isEqualTo(LocalDateTime.of(2024, 12, 13, 10, 8));
        Assertions.assertThat(attendances.getAttendances().get(0)).isEqualTo(attendance1);
    }
}
