package function;

import static constants.TestDataMaker.ATTEND_EXCEPT_MONDAY;
import static constants.TestDataMaker.ATTEND_MONDAY;
import static constants.TestDataMaker.MONDAY_DATE;
import static constants.TestDataMaker.TUESDAY_DATE;
import static constants.TestDataMaker.WEDNESDAY_DATE;
import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendanceBook;
import dto.CheckAttendanceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CheckAttendanceTest {
    private AttendanceBook attendanceBook;

    @BeforeEach
    void SetUp() {
        // given
        attendanceBook = new AttendanceBook();
        attendanceBook.registerCrew("쿠키", MONDAY_DATE, ATTEND_MONDAY);
        attendanceBook.registerCrew("쿠키", TUESDAY_DATE, ATTEND_EXCEPT_MONDAY);
    }

    @Test
    @DisplayName("출석하기 위해서는 닉네임과 등교 시간을 입력 받아야 한다.")
    void Using_Name_And_Time_To_Check_Attendance() {
        // when
        CheckAttendanceResponse response = attendanceBook.checkAttendance("쿠키", WEDNESDAY_DATE, ATTEND_EXCEPT_MONDAY);

        // then
        assertThat(response.time()).isEqualTo(ATTEND_EXCEPT_MONDAY);
        assertThat(response.attendanceStatus()).isEqualTo("출석");
    }
}