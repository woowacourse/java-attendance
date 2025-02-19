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
}
