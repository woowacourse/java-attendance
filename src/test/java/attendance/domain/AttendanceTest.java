package attendance.domain;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    @DisplayName("명단에 없는 크루원이면 예외를 발생한다.")
    @Test
    void 명단에_없는_크루원이면_예외를_발생한다() {

        //given
        List<String> names = List.of("a", "b", "c");
        AttendanceBook attendanceBook = new AttendanceBook(names);
        //when

        //then
        Assertions.assertThatThrownBy(() -> attendanceBook.checkName("d"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석부에 없는 크루원입니다.");
    }

}