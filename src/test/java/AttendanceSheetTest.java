import domain.Attendance;
import domain.AttendanceSheet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AttendanceSheetTest {
    @Test
    @DisplayName("닉네임과 등교 날짜, 등교 시간을 입력하면 출석 기록을 추가할 수 있다")
    public void attendTest() {
        //given
        AttendanceSheet attendanceSheet = new AttendanceSheet(
                List.of(
                        new Attendance("링크", LocalDate.of(2024, 12, 10), LocalTime.of(10,0)),
                        new Attendance("링크", LocalDate.of(2024, 12, 11), LocalTime.of(11,0))
                        
                )
        );

        String nickname = "링크";
        LocalDate date = LocalDate.of(2024, 12, 12);
        LocalTime time = LocalTime.of(10,0);

        //when-then
        assertDoesNotThrow(() -> attendanceSheet.add(nickname, date, time));
    }
}
