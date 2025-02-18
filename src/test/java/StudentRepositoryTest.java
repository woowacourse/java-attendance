import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentRepositoryTest {
    @Test
    @DisplayName("존재하지 않는 학생을 입력시 예외처리 한다.")
    void test1() {
        String input = "포비";

        /*InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        OutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));*/

        StudentRepository studentRepository = new StudentRepository();
        Assertions.assertTrue(studentRepository.notExistStudent("포비"));
    }

    @Test
    @DisplayName("등교시간 잘못 입력시 예외처리 한다.")
    void test2() {
        LocalTime localTime = LocalTime.of(7,59);
        Student student = new Student();
        assertThatThrownBy(() -> student.isStartTime(localTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
    }
}
