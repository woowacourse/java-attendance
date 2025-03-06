package reader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RawAttendanceTest {

    @Test
    @DisplayName("인자로 제공되는 구분자로 문자열을 구분할 수 있다.")
    void canDivideByDelimiter() {
        // given
        String delimiter = ",";
        RawAttendance rawAttendance = RawAttendance.from("강산,재중");

        // when
        String[] splitByDelimiter = rawAttendance.splitByDelimiter(delimiter);

        // then
        assertThat(splitByDelimiter.length).isEqualTo(2);
    }
}
