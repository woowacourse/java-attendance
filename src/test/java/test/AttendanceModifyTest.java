package test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Attendance;
import model.AttendanceInitializer;
import model.Attendances;
import model.Crew;
import model.Crews;
import model.DateGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceModifyTest {

    @DisplayName("수정하려는 날짜를 LocalDate 객체로 반환한다.")
    @Test
    void test() { //TODO : 좀 필요없는 테스트임 (a=a 테스트)
        //given
        int rawDate = 3;

        //when
        LocalDate date = DateGenerator.create(rawDate);

        //then
        assertThat(date).isEqualTo(LocalDate.of(2024, 12, rawDate));
    }

    //TODO : 이미 있는 메서드였음
    @DisplayName("수정하려는 날짜에 맞는 Attendance 객체를 찾는다.")
    @Test
    void test1() {
        //given
        LocalDate modifyDate = LocalDate.of(2024, 12, 13);
        Attendances attendances = new Attendances(List.of(
                new Attendance(modifyDate, LocalTime.of(10, 10))
        ));

        //when
        Attendance attendance = attendances.findByDate(modifyDate);

        //then
        assertThat(attendance.equals(new Attendance(modifyDate, LocalTime.of(10, 10))));
    }

}
