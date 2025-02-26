package function;

import static constants.TestDataMaker.ATTEND_EXCEPT_MONDAY;
import static constants.TestDataMaker.ATTEND_MONDAY;
import static constants.TestDataMaker.LATE_MONDAY;
import static constants.TestDataMaker.MONDAY_DATE;
import static constants.TestDataMaker.TUESDAY_DATE;
import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendanceBook;
import dto.ModifyAttendanceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ModifyAttendanceTest {
    private AttendanceBook attendanceBook;

    @BeforeEach
    void SetUp() {
        // given
        attendanceBook = new AttendanceBook();
        attendanceBook.registerCrew("쿠키", MONDAY_DATE, ATTEND_MONDAY);
        attendanceBook.registerCrew("쿠키", TUESDAY_DATE, ATTEND_EXCEPT_MONDAY);
    }

    @Test
    @DisplayName("출석을 수정하기 위해서는 닉네임, 수정하려는 날짜, 등교시간을 입력해야 한다.")
    void Using_Name_Date_Time_To_Modify_Attendance() {
        // when
        ModifyAttendanceResponse response = attendanceBook.modifyAttendance("쿠키", MONDAY_DATE, LATE_MONDAY);

        // then
        assertThat(response.date()).isEqualTo(MONDAY_DATE);
        assertThat(response.time()).isEqualTo(LATE_MONDAY);
        assertThat(response.attendanceStatus()).isEqualTo("지각");
    }
}