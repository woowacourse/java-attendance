package attendance.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceBookTest {

    @Test
    void 출석부_생성() {
        //given
        Crew crew = new Crew("우가");
        Crews crews = new Crews(Set.of(crew));
        LocalDateTime now = LocalDateTime.of(2025, 2, 28, 9, 59);

        assertDoesNotThrow(() -> new AttendanceBook(crews, now));
    }

    @ParameterizedTest
    @CsvSource(value = "2025, 2, 28, 9, 59, 19")
    void 현재_날짜_이전날까지_출석부_없는_평일날_생성(int year, int month, int day, int hour, int minute, int expectedResult) {
        //given
        Crew crew = new Crew("우가");
        Crews crews = new Crews(Set.of(crew));
        LocalDateTime now = LocalDateTime.of(year, month, day, hour, minute);

        //when
        AttendanceBook attendanceBook = new AttendanceBook(crews, now);

        //then
        Assertions.assertThat(attendanceBook.getAttendanceBook().get(crew).getAttendanceRecord().size())
                .isEqualTo(expectedResult);

    }

    @Test
    void 출석_확인_크루_없으면_예외_발생() {
        //given
        String invalidCrewName = "부기";
        String crewName = "우가";
        Crew crew = new Crew(crewName);

        Crews crews = new Crews(Set.of(crew));

        LocalDateTime inputTime = LocalDateTime.of(2025, 2, 27, 9, 59);
        LocalDateTime currentTime = LocalDateTime.of(2025, 2, 21, 9, 59);
        AttendanceBook attendanceBook = new AttendanceBook(crews, currentTime);

        Assertions.assertThatThrownBy(() -> attendanceBook.registerAttendance(invalidCrewName, inputTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 크루를 찾을 수 없습니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2025, 2, 24, 12, 59, ATTENDANCE", "2025, 2, 24, 13, 6, LATE", "2025, 2, 24, 13, 31, ABSENCE",
            "2025, 2, 25, 9, 59, ATTENDANCE", "2025, 2, 25, 10, 6, LATE", "2025, 2, 25, 10, 31, ABSENCE",
            "2025, 2, 26, 9, 59, ATTENDANCE", "2025, 2, 26, 10, 6, LATE", "2025, 2, 26, 10, 31, ABSENCE",
            "2025, 2, 27, 9, 59, ATTENDANCE", "2025, 2, 27, 10, 6, LATE", "2025, 2, 27, 10, 31, ABSENCE",
            "2025, 2, 28, 9, 59, ATTENDANCE", "2025, 2, 27, 10, 6, LATE", "2025, 2, 27, 10, 31, ABSENCE"})
    void 출석_확인(int year, int month, int day, int hour, int minute, AttendanceStatus attendanceStatus) {
        //given
        String crewName = "우가";
        Crew crew = new Crew(crewName);

        Crews crews = new Crews(Set.of(crew));

        LocalDateTime currentTime = LocalDateTime.of(2025, 2, 21, 9, 59);
        AttendanceBook attendanceBook = new AttendanceBook(crews, currentTime);

        LocalDateTime inputTime = LocalDateTime.of(year, month, day, hour, minute);
        Assertions.assertThat(attendanceBook.registerAttendance(crewName, inputTime).getAttendanceStatus())
                .isEqualTo(attendanceStatus);
    }

    //출석부한테 출석 수정 하기 위해 펼쳐봄
    //출석부가 크루 확인을함

    @Test
    void 출석_수정_크루_없으면_예외_발생() {
        //given
        String invalidCrewName = "부기";
        String crewName = "우가";
        Crew crew = new Crew(crewName);
        Crews crews = new Crews(Set.of(crew));

        LocalDateTime currentDateTime = LocalDateTime.of(2025, 2, 28, 9, 59);
        LocalDateTime modifyDateTime = currentDateTime.withDayOfMonth(24).withHour(12).withMinute(59);

        AttendanceBook attendanceBook = new AttendanceBook(crews, currentDateTime);
        Assertions.assertThatThrownBy(() -> attendanceBook.findBeforeAttendanceRecord(invalidCrewName, modifyDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 크루를 찾을 수 없습니다.");
    }

    //출석부가 출석 기록에게 (이 날)을 메시지로 보내서 기록 있냐고 요청하고 있으면 달라고 함
    //출석 기록이 있다고 반환함 -> 이전값 -> new AttendanceTime으로 해야할 듯(출석부에서) , 주소가 같으니까 바뀌어버리니까
    //출석부가 출석 기록에게 수정해달라고 요청함 -> 현재 값
    //출석 기록이 출석 시간에게 이 시간으로 바꿔달라고 요청함
    //출석 시간이 반환 했다고 true 반환 ->
    //출석 기록이 true받으면 출석부에게 반환된 시간을 전달함
    //출석부는 이전 시간이랑 새로운 시간을 보내줘야함
    @Test
    void 출석_수정_이전_기록_가져오기() {
        //given
        String crewName = "우가";
        Crew crew = new Crew(crewName);
        Crews crews = new Crews(Set.of(crew));

        LocalDateTime currentDateTime = LocalDateTime.of(2025, 2, 28, 9, 59);
        LocalDateTime modifyDateTime = currentDateTime.withDayOfMonth(24).withHour(12).withMinute(59);

        AttendanceBook attendanceBook = new AttendanceBook(crews, currentDateTime);

        Assertions.assertThat(attendanceBook.findBeforeAttendanceRecord(crewName, modifyDateTime)
                .getAttendanceTime().getDayOfMonth()).isEqualTo(24);
    }

}
