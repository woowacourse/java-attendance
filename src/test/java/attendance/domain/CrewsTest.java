package attendance.domain;

import attendance.file.AttendanceFileReader;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CrewsTest {

    private static String path = "src/test/resources/testAttendances.csv";

    @Test
    void 등록되지_않은_닉네임을_입력하면_예외가_발생한다() {
        Crews crews = AttendanceFileReader.read(path).crews();
        String nickName = "듀이";

        assertThatThrownBy(() -> crews.getCrew(nickName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("\n[ERROR] 등록되지 않은 닉네임입니다.");
    }
}
