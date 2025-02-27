package function;

import static constants.TestDataMaker.ABSENT_EXCEPT_MONDAY;
import static constants.TestDataMaker.ATTEND_MONDAY;
import static constants.TestDataMaker.LATE_EXCEPT_MONDAY;
import static constants.TestDataMaker.MONDAY_DATE;
import static constants.TestDataMaker.THURSDAY_DATE;
import static constants.TestDataMaker.TUESDAY_DATE;
import static constants.TestDataMaker.WEDNESDAY_DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.AttendanceBook;
import domain.PenaltyStatus;
import dto.CheckAttendanceRecordResponse;
import dto.PenaltyResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.ErrorMessage;

public class CheckAttendanceRecordTest {
    private AttendanceBook attendanceBook;

    @BeforeEach
    void SetUp() {
        // given
        attendanceBook = new AttendanceBook();
        attendanceBook.registerCrew("쿠키", MONDAY_DATE, ATTEND_MONDAY);
        attendanceBook.registerCrew("쿠키", TUESDAY_DATE, LATE_EXCEPT_MONDAY);
        attendanceBook.registerCrew("쿠키", THURSDAY_DATE, ABSENT_EXCEPT_MONDAY);
    }

    @Test
    @DisplayName("닉네임을 입력하면 전날까지의 크루 출석 기록을 출력해야 한다.")
    void Using_Name_To_Check_Attendance_Record() {
        // when
        List<CheckAttendanceRecordResponse> responses = attendanceBook.checkAttendanceRecord("쿠키");

        // then
        assertThat(responses.getFirst().date()).isEqualTo(MONDAY_DATE);
        assertThat(responses.getFirst().time()).isEqualTo(ATTEND_MONDAY);
        assertThat(responses.getFirst().attendanceStatus()).isEqualTo("출석");

        assertThat(responses.get(1).date()).isEqualTo(TUESDAY_DATE);
        assertThat(responses.get(1).time()).isEqualTo(LATE_EXCEPT_MONDAY);
        assertThat(responses.get(1).attendanceStatus()).isEqualTo("지각");

        assertThat(responses.get(2).date()).isEqualTo(WEDNESDAY_DATE);
        assertThat(responses.get(2).time()).isNull(); // 수요일 기록 존재 X
        assertThat(responses.get(2).attendanceStatus()).isEqualTo("결석");

        assertThat(responses.get(3).date()).isEqualTo(THURSDAY_DATE);
        assertThat(responses.get(3).time()).isEqualTo(ABSENT_EXCEPT_MONDAY);
        assertThat(responses.get(3).attendanceStatus()).isEqualTo("결석");
    }

    @Test
    @DisplayName("등록되지 않는 닉네임의 경우 예외 메시지를 출력한다.")
    void Name_Is_Not_Registered() {
        assertThatThrownBy(() -> attendanceBook.checkAttendanceRecord("미등록"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOTICE_NICKNAME_IS_NOT_REGISTERED.getFormat());
    }

    @Test
    @DisplayName("크루 출석 기록을 출력한 후, 출결 상태별 횟수와 패널티 대상자 여부를 출력해야한다.")
    void Calculate_Attendance_Status_Count_And_Judge_Penalty() {
        List<CheckAttendanceRecordResponse> responses = attendanceBook.checkAttendanceRecord("쿠키");

        PenaltyResponse penaltyResponse = PenaltyStatus.judgeCrewAttendanceRecord(responses);

        assertThat(penaltyResponse.attendCount()).isEqualTo(1);
        assertThat(penaltyResponse.lateCount()).isEqualTo(1);
        assertThat(penaltyResponse.absentCount()).isEqualTo(19);
        assertThat(penaltyResponse.penalty()).isEqualTo("제적");
    }
}