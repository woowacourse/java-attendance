package function;

import static constants.TestTimeMaker.EXCEPT_MONDAY_ATTEND;
import static constants.TestTimeMaker.MONDAY_ATTEND;
import static constants.TestTimeMaker.MONDAY_LATE;
import static constants.TestTimeMaker.NON_OPERATING_TIME;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.AttendanceBook;
import domain.Crew;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CheckAttendanceTest {

    @DisplayName("출석하기 위해서는 닉네임과 등교 시간을 입력 받아야 한다.")
    @Test
    void 출석하기_위해서는_닉네임과_등교_시간을_입력_받아야_한다() {
        AttendanceBook attendanceBook = new AttendanceBook();

        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 2), MONDAY_ATTEND));
        attendanceBook.addNewCrew(crew1);

        Crew crew2 = Crew.createByName("우유");
        crew2.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 2), MONDAY_ATTEND));
        attendanceBook.addNewCrew(crew2);

        attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 3), EXCEPT_MONDAY_ATTEND));
    }

    @Test
    void 이미_출석한_경우_다시_출석할_수_없으며_수정_기능을_이용하도록_안내해야_한다() {
        AttendanceBook attendanceBook = new AttendanceBook();

        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 2), MONDAY_ATTEND));
        attendanceBook.addNewCrew(crew1);

        Crew crew2 = Crew.createByName("우유");
        crew2.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 2), MONDAY_ATTEND));
        attendanceBook.addNewCrew(crew2);

        assertThatThrownBy(
                () -> attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 2), MONDAY_LATE)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("주말 및 공휴일에는 출석을 받지 않는다.")
    void 주말_및_공휴일에는_출석을_받지_않는다() {
        AttendanceBook attendanceBook = new AttendanceBook();
        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 2), MONDAY_ATTEND));
        attendanceBook.addNewCrew(crew1);

        assertThatThrownBy(
                () -> attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 25), EXCEPT_MONDAY_ATTEND)))
                .isInstanceOf(IllegalArgumentException.class) // 공휴일
                .hasMessage("[ERROR] 12월 25일 공휴일은 등교일이 아닙니다.");
        assertThatThrownBy(
                () -> attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 1), EXCEPT_MONDAY_ATTEND)))
                .isInstanceOf(IllegalArgumentException.class) // 일요일
                .hasMessage("[ERROR] 12월 01일 일요일은 등교일이 아닙니다.");
        assertThatThrownBy(
                () -> attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 7), EXCEPT_MONDAY_ATTEND)))
                .isInstanceOf(IllegalArgumentException.class) // 토요일
                .hasMessage("[ERROR] 12월 07일 토요일은 등교일이 아닙니다.");
    }

    @Test
    @DisplayName("등록되지 않는 닉네임의 경우 예외를 출력한다.")
    void 등록되지_않는_닉네임의_경우_예외를_출력한다() {
        AttendanceBook attendanceBook = new AttendanceBook();
        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 2), MONDAY_ATTEND));
        attendanceBook.addNewCrew(crew1);

        assertThatThrownBy(
                () -> attendanceBook.checkAttendance("우유", Map.of(LocalDate.of(2024, 12, 2), MONDAY_ATTEND)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");

    }

    @Test
    void 출석_확인시_캠퍼스_운영_시간이_아닌_경우_예외를_출력한다() {
        AttendanceBook attendanceBook = new AttendanceBook();
        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 2), EXCEPT_MONDAY_ATTEND));
        attendanceBook.addNewCrew(crew1);

        assertThatThrownBy(
                () -> attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 3), NON_OPERATING_TIME)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간은 08:00~23:00 입니다.");
    }
}