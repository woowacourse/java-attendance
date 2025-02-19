import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {

    @Test
    @DisplayName("출석부에_기존이름이_존재여부_확인")
    void 출석부에_기존이름이_존재여부_확인() {
        AttendanceBook attendanceBook = new AttendanceBook();
        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024,12,1),LocalTime.of(10, 6)));
        attendanceBook.addNewCrew(crew1);

        assertThat(attendanceBook.checkAlreadyExists("쿠키")).isEqualTo(true);
        assertThat(attendanceBook.checkAlreadyExists("없음")).isEqualTo(false);
    }
}