import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentTest {
    private final Student student = new Student();

    @Test
    @DisplayName("출석 등록 시간 확인 테스트")
    void 출석_등록_시간_확인_테스트(){
        LocalDate todayDate = LocalDate.of(2024,12,13);
        String attendanceTime = "10:59";
        LocalTime expect = LocalTime.of(10,59);

        student.registerAttendanceRecord(todayDate, attendanceTime);
        LocalTime result = student.attendanceTimeRecords.get(todayDate);
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("출석 등록 후 출석 상태 확인 테스트")
    void 출석_등록_후_출석_상태_확인_테스트(){
        LocalDate todayDate = LocalDate.of(2024,12,13);
        String attendanceTime = "10:59";
        AttendanceStatus expect = AttendanceStatus.ABSENT;

        student.registerAttendanceRecord(todayDate, attendanceTime);
        AttendanceStatus result = student.attendanceStatusRecords.get(todayDate);
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("출석 정보 수정 후 수정 시간 확인 테스트")
    void 출석_정보_수정_후_수정_시간_확인_테스트(){
        //given
        LocalDate recordData = LocalDate.of(2024,12,13);
        String attendanceTime = "10:59";
        String modifyDate = "13";
        String modifyTime = "10:00";
        LocalTime expect = LocalTime.of(10,0);
        student.registerAttendanceRecord(recordData, attendanceTime);
        //when
        student.modifyAttendanceRecord(modifyDate, modifyTime);
        //then
        LocalTime result = student.attendanceTimeRecords.get(recordData);
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("출석 정보 수정 후 수정 출결 상태 확인 테스트")
    void 출석_정보_수정_후_수정_출결_상태_확인_테스트(){
        //given
        LocalDate recordData = LocalDate.of(2024,12,13);
        String attendanceTime = "10:59";
        String modifyDate = "13";
        String modifyTime = "10:00";
        AttendanceStatus expect = AttendanceStatus.ATTENDANCE;
        student.registerAttendanceRecord(recordData, attendanceTime);
        //when
        student.modifyAttendanceRecord(modifyDate, modifyTime);
        //then
        AttendanceStatus result = student.attendanceStatusRecords.get(recordData);
        Assertions.assertEquals(expect, result);
    }


}
