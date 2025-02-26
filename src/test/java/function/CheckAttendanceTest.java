package function;

import static constants.TestDataMaker.ATTEND_EXCEPT_MONDAY;
import static constants.TestDataMaker.ATTEND_MONDAY;
import static constants.TestDataMaker.LATE_MONDAY;
import static constants.TestDataMaker.MONDAY_DATE;
import static constants.TestDataMaker.SUNDAY_DATE;
import static constants.TestDataMaker.TUESDAY_DATE;
import static constants.TestDataMaker.WEDNESDAY_DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.AttendanceBook;
import dto.CheckAttendanceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.ErrorMessage;

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

    @Test
    @DisplayName("출석하는 날이 주말이나 공휴일인 경우 예외 메시지를 출력한다.")
    void Weekend_Working_Is_Not_Allowed() {
        assertThatThrownBy(() -> attendanceBook.checkAttendance("쿠키", SUNDAY_DATE, ATTEND_EXCEPT_MONDAY))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOTICE_NOT_TRAINING_DAY.format(SUNDAY_DATE.getMonthValue(),
                        SUNDAY_DATE.getDayOfMonth(), "일요일"));
    }

    @Test
    @DisplayName("이미 출석을 하였는데 다시 출석 확인을 하는 경우 예외 메시지를 출력한다.")
    void Attendance_Already_Existed() {
        assertThatThrownBy(() -> attendanceBook.checkAttendance("쿠키", MONDAY_DATE, LATE_MONDAY))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOTICE_ATTENDANCE_ALREADY_EXISTED.getFormat());
    }

    @Test
    @DisplayName("등록되지 않는 닉네임의 경우 에외 메시지를 출력한다.")
    void Name_Is_Not_Registered() {
        assertThatThrownBy(() -> attendanceBook.checkAttendance("미등록", MONDAY_DATE, ATTEND_MONDAY))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOTICE_NICKNAME_IS_NOT_REGISTERED.getFormat());
    }
}