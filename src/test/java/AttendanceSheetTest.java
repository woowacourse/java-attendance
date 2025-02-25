import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import policy.FileReaderPolicy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AttendanceSheetTest {
    private static final String FILE_PATH = "src/main/resources/attendances.csv";

    @Test
    @DisplayName("출석부를 파일에서 읽어들여 저장할 수 있다")
    public void createAttendanceSheetTest() {
        AttendanceSheet attendanceSheet = new AttendanceSheet(new FileReaderPolicy(FILE_PATH));

        assertDoesNotThrow(attendanceSheet::addAttendanceInfoFromFile);
    }


//    @Test
//    @DisplayName("출석 기록을 추가할 수 있다")
//    public void AttendanceTest() {
//        //given
//        String nickname = "링크";
//        LocalDate attendanceDate = LocalDate.of(2024, 12, 10);
//        LocalTime attendanceTime = LocalTime.of(10, 0);
//
//        AttendanceSheet attendanceSheet = new AttendanceSheet();
//
//        //when-then
//        assertThat(attendanceSheet.attend(nickname, attendanceDate, attendanceTime)).isEqualTo("출석");
//    }

}
