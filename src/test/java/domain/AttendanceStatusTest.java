package domain;

import static constants.TestDataMaker.ABSENT_EXCEPT_MONDAY;
import static constants.TestDataMaker.ABSENT_MONDAY;
import static constants.TestDataMaker.ATTEND_EXCEPT_MONDAY;
import static constants.TestDataMaker.ATTEND_MONDAY;
import static constants.TestDataMaker.LATE_EXCEPT_MONDAY;
import static constants.TestDataMaker.LATE_MONDAY;
import static constants.TestDataMaker.MONDAY_DATE;
import static constants.TestDataMaker.TUESDAY_DATE;
import static domain.AttendanceStatus.ABSENT_STATUS;
import static domain.AttendanceStatus.ATTEND_STATUS;
import static domain.AttendanceStatus.LATE_STATUS;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceStatusTest {
    @Test
    @DisplayName("날짜와 시간을 입력받아 출석 상태를 반환 한다.")
    void Judge_Attendance_Status_By_Date_And_Time() {
        assertThat(AttendanceStatus.judgeAttendanceStatusByDateAndTime(MONDAY_DATE, ATTEND_MONDAY))
                .isEqualTo(ATTEND_STATUS);
        assertThat(AttendanceStatus.judgeAttendanceStatusByDateAndTime(MONDAY_DATE, LATE_MONDAY))
                .isEqualTo(LATE_STATUS);
        assertThat(AttendanceStatus.judgeAttendanceStatusByDateAndTime(MONDAY_DATE, ABSENT_MONDAY))
                .isEqualTo(ABSENT_STATUS);

        assertThat(AttendanceStatus.judgeAttendanceStatusByDateAndTime(TUESDAY_DATE, ATTEND_EXCEPT_MONDAY))
                .isEqualTo(ATTEND_STATUS);
        assertThat(AttendanceStatus.judgeAttendanceStatusByDateAndTime(TUESDAY_DATE, LATE_EXCEPT_MONDAY))
                .isEqualTo(LATE_STATUS);
        assertThat(AttendanceStatus.judgeAttendanceStatusByDateAndTime(TUESDAY_DATE, ABSENT_EXCEPT_MONDAY))
                .isEqualTo(ABSENT_STATUS);
    }
}