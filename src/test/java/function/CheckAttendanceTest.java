package function;

import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendanceBook;
import dto.CheckAttendanceResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CheckAttendanceTest {
    @Test
    @DisplayName("출석하기 위해서는 닉네임과 등교 시간을 입력 받아야 한다.")
    void Using_Name_And_Time_To_Check_Attendance() {
        // given
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.registerCrew("쿠키", LocalDate.of(2024, 12, 3), LocalTime.of(10, 8));
        attendanceBook.registerCrew("쿠키", LocalDate.of(2024, 12, 4), LocalTime.of(10, 6));

        // when
        CheckAttendanceResponse response = attendanceBook.checkAttendance("쿠키", LocalDate.of(2024, 12, 2),
                LocalTime.of(10, 1));

        // then
        assertThat(response.time()).isEqualTo(LocalTime.of(10, 1));
        assertThat(response.attendanceStatus()).isEqualTo("출석");
    }
}