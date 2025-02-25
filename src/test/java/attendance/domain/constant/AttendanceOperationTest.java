package attendance.domain.constant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceOperationTest {

    @ParameterizedTest
    @CsvSource(value = {"1,ONE", "2,TWO", "3,THREE", "4, FOUR", "Q,QUIT"})
    void of(String functionNumber, AttendanceOperation expectedResult) {
        //when
        AttendanceOperation attendanceOperation = AttendanceOperation.of(functionNumber);
        //then
        Assertions.assertThat(attendanceOperation).isEqualTo(expectedResult);
    }

}
