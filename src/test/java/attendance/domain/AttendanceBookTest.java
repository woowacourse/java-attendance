package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.domain.fixture.LocalDateTestFixture;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
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

    @Test
    void 출석_저장_시_출석_시각을_반환한다(){
        String crewName = "빙티";
        AttendanceBook attendanceBook = new AttendanceBook(crewName);
        LocalDate attendDate = LocalDateTestFixture.createRegularDate();
        LocalTime attendTime = LocalTime.of(10, 0);

        LocalDateTime result = attendanceBook.attend(attendDate, attendTime);

        assertThat(result.getHour()).isEqualTo(10);
        assertThat(result.getMinute()).isEqualTo(0);
        assertThat(result.toLocalDate()).isEqualTo(attendDate);
    }

    @Test
    void 출석_기록이_있는_경우_예외가_발생한다(){
        String crewName = "빙티";
        AttendanceBook attendanceBook = new AttendanceBook(crewName);
        LocalDate attendDate = LocalDateTestFixture.createRegularDate();
        LocalTime attendTime = LocalTime.of(10, 0);
        attendanceBook.attend(attendDate, attendTime);

        LocalTime newAttendTime = LocalTime.of(10,4);
        assertThatThrownBy(() -> attendanceBook.attend(attendDate, newAttendTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜에 출석 기록이 있습니다. 출석 수정 기능을 이용해주세요.");
    }

    @Test
    void 캠퍼스_운영시간이_아니면_예외가_발생한다(){
        String crewName = "빙티";
        AttendanceBook attendanceBook = new AttendanceBook(crewName);
        LocalDate attendDate = LocalDateTestFixture.createRegularDate();
        LocalTime attendTime = LocalTime.of(23, 59);

        assertThatThrownBy(() -> attendanceBook.attend(attendDate, attendTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("현재 캠퍼스 운영시간이 아닙니다.");
    }

    @Test
    void 캠퍼스_등교일이_아니면_예외가_발생한다(){
        String crewName = "빙티";
        AttendanceBook attendanceBook = new AttendanceBook(crewName);
        LocalDate attendDate = LocalDateTestFixture.createWeekendDate();
        LocalTime attendTime = LocalTime.of(10, 0);

        assertThatThrownBy(() -> attendanceBook.attend(attendDate, attendTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("등교일이 아닙니다.");
    }
}
