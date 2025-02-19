import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ModifyAttendanceTest {

    @Test
    @DisplayName("출석을 수정하기 위해서는 닉네임, 수정하려는 날짜, 등교시간을 입력해야 한다.")
    void 출석을_수정하기_위해서는_닉네임_수정하려는_날짜_등교시간을_입력해야_한다() {
        AttendanceBook attendanceBook = new AttendanceBook();

        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6)));
        attendanceBook.addNewCrew(crew1);

        Crew crew2 = Crew.createByName("우유");
        crew2.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 7)));
        attendanceBook.addNewCrew(crew2);
        crew2.modifyDailyAttendance(Map.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 20)));
    }

    @Test
    @DisplayName("등록되지 않는 닉네임의 경우 예외를 출력한다.")
    void 등록되지_않는_닉네임의_경우_예외를_출력한다() {
        AttendanceBook attendanceBook = new AttendanceBook();

        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6)));
        attendanceBook.addNewCrew(crew1);

        assertThatThrownBy(
                () -> attendanceBook.modifyAttendance("없음", Map.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 20))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @Test
    @DisplayName("수정하려는 날짜의 기록이 존재하지 않을 경우, 에러를 출력한다.")
    void 수정하려는_날짜의_기록이_존재하지_않을_경우_에러를_출력한다() {
        AttendanceBook attendanceBook = new AttendanceBook();

        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6)));
        attendanceBook.addNewCrew(crew1);

        assertThatThrownBy(
                () -> attendanceBook.modifyAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 5), LocalTime.of(10, 20))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 05일 기록이 존재하지 않습니다.");
    }
}
