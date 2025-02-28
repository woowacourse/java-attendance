import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendStatus;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendTest {

    @ParameterizedTest
    @CsvSource(value = {"2024-12-02,2,true", "2024-12-02,3,false"})
    @DisplayName("출석일이 주어진 값과 같은지 판정하는 기능")
    void checkAttendStatus(LocalDate date, int day, boolean expected) {
        //given
        Attend attend = new Attend(date);

        //when
        boolean actual = attend.equalsDay(day);

        //then
        assertThat(actual).isEqualTo(expected);
    }
}
