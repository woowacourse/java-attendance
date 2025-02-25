package test;

import java.time.LocalDate;
import model.DateGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceModifyTest {

    @DisplayName("수정하려는 날짜를 LocalDate 객체로 반환한다.")
    @Test
    void test() {
        //given
        int rawDate = 3;

        //when
        LocalDate date = DateGenerator.create(rawDate);

        //then
        Assertions.assertThat(date).isEqualTo(LocalDate.of(2024, 12, rawDate));
    }

}
