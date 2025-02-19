package converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    void test1() {
        //given
        StringConverter stringConverter = new StringConverter();

        //when
        Attendance attendance = stringConverter.convertToAttendance("이름", "09:44");

        //then
        Assertions.assertThat(attendance.getCrew().getNickname()).isEqualTo("이름");
        Assertions.assertThat(attendance.getCheckInTime())
                .isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 44)));
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