package test.model.attendance;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import model.attendance.AttendanceStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceStatusTest {

    //TODO : Attendance와 결합하기
    @DisplayName("날짜와 시간을 입력하면 이에 맞는 출석 상태를 반환한다.")
    @Test
    void success_findStatusByDate() {
        //given
        LocalDate date = LocalDate.of(2024, 12, 13);
        LocalTime time = LocalTime.of(10, 5);

        //then, when
        assertThat(AttendanceStatus.findByAttendanceTime(date, time)).isEqualTo(AttendanceStatus.NORMAL);
    }
}
