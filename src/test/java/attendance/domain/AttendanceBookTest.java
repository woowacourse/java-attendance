package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("출석 목록")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AttendanceBookTest {

    @Test
    void 크루원의_이름으로_객체를_생성한다() {
        String crewName = "빙티";
        AttendanceBook attendanceBook = new AttendanceBook(crewName);

        assertThat(attendanceBook)
                .isInstanceOf(AttendanceBook.class);
    }

    @Test
    void 크루원의_이름에_해당하면_true를_반환한다() {
        String crewName = "빙티";
        AttendanceBook attendanceBook = new AttendanceBook(crewName);
        boolean result = attendanceBook.isNameMatched(crewName);

        assertThat(result).isTrue();
    }

    @Test
    void 크루원의_이름예_해당하지_않으면_false를_반환한다() {
        String crewName = "빙티";
        AttendanceBook attendanceBook = new AttendanceBook(crewName);
        String findName = "루키";
        boolean result = attendanceBook.isNameMatched(findName);

        assertThat(result).isFalse();
    }
}
