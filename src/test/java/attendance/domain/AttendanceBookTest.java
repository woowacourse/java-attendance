package attendance.domain;

import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {

    @DisplayName("명단에 없는 크루원이면 예외를 발생한다.")
    @Test
    void 명단에_없는_크루원이면_예외를_발생한다() {

        //given
        Set<String> names = Set.of("a", "b", "c");
        AttendanceBook attendanceBook = new AttendanceBook(names);

        //when

        //then
        Assertions.assertThatThrownBy(() -> attendanceBook.validateCrewName("d"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석부에 없는 크루원입니다.");
    }
}
