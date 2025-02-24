package attendance;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendancesTest {

    @Test
    @DisplayName("CSV파일을 성공적으로 읽어온다.")
    void readFileTest1() {
        Attendances attendances = new Attendances();
        assertThatCode(() -> attendances.readAttendancesFile("src/main/resources/attendances.csv")).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("CSV파일의 경로가 잘못되면 예외를 던진다.")
    void readFileTest2() {
        Attendances attendances = new Attendances();
        assertThatThrownBy(() -> attendances.readAttendancesFile("src/main/resources/attendances.text")).isInstanceOf(IllegalArgumentException.class);
    }
}
