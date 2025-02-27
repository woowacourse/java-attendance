import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.Attendance;
import domain.AttendanceBook;
import domain.Day;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class AttendanceUpdateTest {

    //출석 확인을 수정하려면 닉네임, 수정하려는 날짜, 등교 시간을 입력하여 기록을 수정할 수 있다.
    //수정 후에는 변경 전과 변경 후의 출석 기록을 확인할 수 있다.
    //출석을 수정하려는 크루의 닉네임을 입력해 주세요.
    //빙티
    //수정하려는 날짜(일)를 입력해 주세요.
    //3
    //언제로 변경하겠습니까?
    //09:58
    //
    //12월 03일 화요일 10:07 (지각) -> 09:58 (출석) 수정 완료!

    @Test
    void 닉네임_수정날짜_등교시간을_입력하여_기록을_수정한다() {
        final var nickname = "에드";
        final var day = new Day(LocalDate.of(2025, 2, 27));
        final var originTime = LocalTime.of(10, 0);
        final var attendance = new Attendance(day, originTime);
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.recordAttendance(nickname, attendance);

        final var modifiedTime = LocalTime.of(10, 5);
        AttendanceUpdate attendanceUpdate = new AttendanceUpdate();
        attendanceUpdate.updateAttendanceTime(nickname, day, modifiedTime);

        assertEquals(modifiedTime, attendance.getTime());
    }

}