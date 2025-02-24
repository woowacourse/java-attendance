package converter;

import java.time.LocalDateTime;
import java.util.List;
import model.Attendance;
import model.Attendances;
import model.Crew;
import model.Crews;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringConverterTest {

    @Test
    @DisplayName("평일 출석 확인 테스트")
    void test1() {
        //given
        StringConverter stringConverter = new StringConverter();

        List<String> rawAttendances = List.of(
                "쿠키,2024-12-13 10:08"
        );
        Crew crew1 = Crew.of("쿠키");
        Crews crews = Crews.of(List.of(crew1));

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