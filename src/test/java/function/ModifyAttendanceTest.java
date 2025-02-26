package function;

import static constants.TestDataMaker.ATTEND_EXCEPT_MONDAY;
import static constants.TestDataMaker.ATTEND_MONDAY;
import static constants.TestDataMaker.LATE_MONDAY;
import static constants.TestDataMaker.MONDAY_DATE;
import static constants.TestDataMaker.NON_OPERATING_TIME;
import static constants.TestDataMaker.SUNDAY_DATE;
import static constants.TestDataMaker.TUESDAY_DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.AttendanceBook;
import dto.ModifyAttendanceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.ErrorMessage;

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

    @Test
    @DisplayName("등록되지 않는 닉네임의 경우 예외 메시지를 출력한다.")
    void Name_Is_Not_Registered() {
        assertThatThrownBy(() -> attendanceBook.modifyAttendance("미등록", MONDAY_DATE, LATE_MONDAY))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOTICE_NICKNAME_IS_NOT_REGISTERED.getFormat());
    }

    @Test
    @DisplayName("수정하려는 날이 주말이나 공휴일인 경우 예외 메시지를 출력한다.")
    void Weekend_Working_Is_Not_Allowed() {
        assertThatThrownBy(() -> attendanceBook.modifyAttendance("쿠키", SUNDAY_DATE, LATE_MONDAY))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOTICE_NOT_TRAINING_DAY.format(SUNDAY_DATE.getMonthValue(),
                        SUNDAY_DATE.getDayOfMonth(), "일요일"));
    }

    @Test
    @DisplayName("등교 시간이 캠퍼스 운영 시간이 아닌 경우 예외메시지를 출력한다.")
    void Time_Is_Not_A_Campus_Operating_Time() {
        assertThatThrownBy(() -> attendanceBook.modifyAttendance("쿠키", MONDAY_DATE, NON_OPERATING_TIME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOTICE_TIME_IS_NOT_A_CAMPUS_OPERATING_TIME.getFormat());
    }
}