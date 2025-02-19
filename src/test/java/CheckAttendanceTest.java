import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CheckAttendanceTest {

    @DisplayName("출석하기 위해서는 닉네임과 등교 시간을 입력 받아야 한다.")
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
}