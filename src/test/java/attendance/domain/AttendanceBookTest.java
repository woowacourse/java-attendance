package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {

    private List<String> names;
    private String name;
    private AttendanceBook attendanceBook;

    @BeforeEach
    void setUp() {
        names = new ArrayList<>();
        name = "체체";
        names.add(name);

        attendanceBook = new AttendanceBook(names);
    }

    @DisplayName("입력된 이름이 출석부에 없다면 예외를 발생한다.")
    @Test
    void 입력된_이름이_출석부에_없다면_예외를_발생한다() {

        // given

        // when & then
        assertThatThrownBy(() -> attendanceBook.hasCrew("추추"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석부에 존재하지 않는 닉네임입니다.");
    }

    @DisplayName("입력된 이름이 출석부에 있다면 예외가 발생하지 않는다.")
    @Test
    void 입력된_이름이_출석부에_있다면_예외가_발생하지_않는다() {

        // given

        // when & then
        assertThatCode(() -> {
            attendanceBook.hasCrew("체체");
        }).doesNotThrowAnyException();
    }
}
