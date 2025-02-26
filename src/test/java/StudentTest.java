import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentTest {
    @Test
    @DisplayName("출석 등록 시간 확인 테스트")
    void 출석_등록_시간_확인_테스트(){
        LocalDate todayDate = LocalDate.of(2024,12,13);
        String attendanceTime = "10:59";
        LocalTime expect = LocalTime.of(10,59);
        Student student = new Student();

        student.registerAttendanceRecord(todayDate, attendanceTime);
        LocalTime result = student.attendanceRecords.get(todayDate);
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("출석 등록 후 출석 상태 확인 테스트")
    void 출석_등록_후_출석_상태_확인_테스트(){
        LocalDate todayDate = LocalDate.of(2024,12,13);
        String attendanceTime = "10:59";
        AttendanceStatus expect = AttendanceStatus.ABSENT;
        Student student = new Student();

        student.registerAttendanceRecord(todayDate, attendanceTime);
        AttendanceStatus result = student.attendanceStatusRecords.get(todayDate);
        Assertions.assertEquals(expect, result);
    }

}
