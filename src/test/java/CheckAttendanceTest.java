import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.AttendanceBook;
import domain.Crew;
import domain.ErrorCode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class CheckAttendanceTest {

    @Test
    void 출석하기_위해서는_닉네임과_등교_시간을_입력_받아야_한다() {
        AttendanceBook attendanceBook = new AttendanceBook();

        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 1), LocalTime.of(10, 6)));
        attendanceBook.addNewCrew(crew1);

        Crew crew2 = Crew.createByName("우유");
        crew2.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 1), LocalTime.of(10, 7)));
        attendanceBook.addNewCrew(crew2);

        attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 2), LocalTime.of(10, 7))); // 정상
    }

    @Test
    void 이미_출석한_경우_다시_출석할_수_없으며_수정_기능을_이용하도록_안내해야_한다() {
        AttendanceBook attendanceBook = new AttendanceBook();

        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 1), LocalTime.of(10, 6)));
        attendanceBook.addNewCrew(crew1);

        Crew crew2 = Crew.createByName("우유");
        crew2.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 1), LocalTime.of(10, 7)));
        attendanceBook.addNewCrew(crew2);

        assertThatThrownBy(
                () -> attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 1), LocalTime.of(10, 7))))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void 주말_및_공휴일에는_출석을_받지_않는다() {
        AttendanceBook attendanceBook = new AttendanceBook();
        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 1), LocalTime.of(10, 6)));
        attendanceBook.addNewCrew(crew1);

        assertThatThrownBy(
                () -> attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 25), LocalTime.of(10, 7))))
                .isInstanceOf(IllegalArgumentException.class) // 공휴일
                .hasMessage("[ERROR] 12월 25일 공휴일은 등교일이 아닙니다.");
        assertThatThrownBy(
                () -> attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 1), LocalTime.of(10, 7))))
                .isInstanceOf(IllegalArgumentException.class) // 일요일
                .hasMessage("[ERROR] 12월 01일 일요일은 등교일이 아닙니다.");
        assertThatThrownBy(
                () -> attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 7), LocalTime.of(10, 7))))
                .isInstanceOf(IllegalArgumentException.class) // 토요일
                .hasMessage("[ERROR] 12월 07일 토요일은 등교일이 아닙니다.");
    }

    @Test
    void 등록되지_않는_닉네임의_경우_예외를_출력한다() {
        AttendanceBook attendanceBook = new AttendanceBook();
        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6)));
        attendanceBook.addNewCrew(crew1);

        assertThatThrownBy(
                () -> attendanceBook.checkAttendance("우유", Map.of(LocalDate.of(2024, 12, 4), LocalTime.of(10, 7))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");

    }

    @Test
    void 출석_확인시_캠퍼스_운영_시간이_아닌_경우_예외를_출력한다() {
        AttendanceBook attendanceBook = new AttendanceBook();
        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6)));
        attendanceBook.addNewCrew(crew1);

        assertThatThrownBy(
                () -> attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 4), LocalTime.of(7, 7))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간은 08:00~23:00 입니다.");
    }
}