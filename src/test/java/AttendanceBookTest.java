import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import attendance.model.AttendanceBook;
import attendance.model.AttendanceTime;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceBookTest {

    @Test
    void 입력_받은_이름과_날짜로_출석_기록을_추가한다() {

        // given
        final AttendanceTime attendanceTime = new AttendanceTime(LocalDate.of(2025, 2, 27), 10, 5);

        // when
        final AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.add("이름", attendanceTime);

        // then
        final AttendanceTime result = attendanceBook.getAttendance("이름", LocalDate.of(2025, 2, 27));
        assertThat(result).isEqualTo(attendanceTime);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "이름1, true",
            "이름2, false"
    })
    void 입력_받은_이름이_크루_목록에_존재하는지_판단한다(final String name, final boolean expectedResult) {

        // given
        final AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.add("이름1", new AttendanceTime(LocalDate.of(2025, 2, 27), 10, 10));

        // when
        final boolean result = attendanceBook.isCrewExists(name);

        // then
        Assertions.assertThat(result).isEqualTo(expectedResult);
    }
}
