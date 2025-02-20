import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 1), LocalTime.of(10, 6)));
        attendanceBook.addNewCrew(crew1);

        assertThat(attendanceBook.checkCrewAlreadyExists("쿠키")).isEqualTo(true);
        assertThat(attendanceBook.checkCrewAlreadyExists("없음")).isEqualTo(false);
    }

    @Test
    void 캠퍼스_운영_시간이_아닌_경우_예외를_출력한다() {
        AttendanceBook attendanceBook = new AttendanceBook();
        assertThatThrownBy(
                () -> attendanceBook.validateIsInOperationHour(LocalTime.of(7, 7)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간은 08:00~23:00 입니다.");
    }
}