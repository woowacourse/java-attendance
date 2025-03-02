package attendance.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
        Assertions.assertThat(attendanceBook.getAttendanceBook().get(crew).getFirst().getAttendanceRecord().size())
                .isEqualTo(expectedResult);

    }

    @Test
    void 출석_확인_크루_없으면_예외_발생() {
        //given
        String invalidCrewName = "부기";
        String crewName = "우가";
        Crew crew = new Crew(crewName);
        Crew crew1 = new Crew(invalidCrewName);

        Crews crews = new Crews(Set.of(crew));

        LocalDateTime inputTime = LocalDateTime.of(2025, 2, 27, 9, 59);
        LocalDateTime currentTime = LocalDateTime.of(2025, 2, 21, 9, 59);
        AttendanceBook attendanceBook = new AttendanceBook(crews, currentTime);

        Assertions.assertThatThrownBy(() -> attendanceBook.registerAttendance(crew1, inputTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 크루를 찾을 수 없습니다.");
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
        Assertions.assertThat(attendanceBook.registerAttendance(crew, inputTime).getAttendanceStatus())
                .isEqualTo(attendanceStatus);
    }

    @Test
    void 출석_수정_크루_없으면_예외_발생() {
        //given
        String invalidCrewName = "부기";
        String crewName = "우가";
        Crew crew = new Crew(crewName);
        Crew crew1 = new Crew(invalidCrewName);
        Crews crews = new Crews(Set.of(crew));

        LocalDateTime currentDateTime = LocalDateTime.of(2025, 2, 28, 9, 59);
        LocalDateTime modifyDateTime = currentDateTime.withDayOfMonth(24).withHour(12).withMinute(59);

        AttendanceBook attendanceBook = new AttendanceBook(crews, currentDateTime);
        Assertions.assertThatThrownBy(() -> attendanceBook.findBeforeAttendanceRecord(crew1, modifyDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 크루를 찾을 수 없습니다.");
    }

    //출석부가 출석 기록에게 (이 날)을 메시지로 보내서 기록 있냐고 요청하고 있으면 달라고 함
    //출석 기록이 있다고 반환함 -> 이전값 -> new AttendanceTime으로 해야할 듯(출석부에서) , 주소가 같으니까 바뀌어버리니까
    @Test
    void 출석_수정_이전_기록_가져오기() {
        //given
        String crewName = "우가";
        Crew crew = new Crew(crewName);
        Crews crews = new Crews(Set.of(crew));

        LocalDateTime currentDateTime = LocalDateTime.of(2025, 2, 28, 9, 59);
        LocalDateTime modifyDateTime = currentDateTime.withDayOfMonth(24).withHour(12).withMinute(59);

        AttendanceBook attendanceBook = new AttendanceBook(crews, currentDateTime);

        Assertions.assertThat(attendanceBook.findBeforeAttendanceRecord(crew, modifyDateTime)
                .getAttendanceTime().getDayOfMonth()).isEqualTo(24);
    }

    @DisplayName("수정된 출석 기록이 이전 값과 다른지 검사")
    @ParameterizedTest()
    @CsvSource(value = {
            "2025, 2, 21, 9, 59, ATTENDANCE", "2025, 2, 21, 10, 6, LATE", "2025, 2, 21, 10, 31, ABSENCE",
            "2025, 2, 24, 12, 59, ATTENDANCE", "2025, 2,24, 13, 6, LATE", "2025, 2, 24, 13, 31, ABSENCE",
            "2025, 2, 25, 9, 59, ATTENDANCE", "2025, 2, 25, 10, 6, LATE", "2025, 2, 25, 10, 31, ABSENCE",
            "2025, 2, 26, 9, 59, ATTENDANCE", "2025, 2, 26, 10, 6, LATE", "2025, 2, 26, 10, 31, ABSENCE",
            "2025, 2, 27, 9, 59, ATTENDANCE", "2025, 2, 27, 10, 6, LATE", "2025, 2, 27, 10, 31, ABSENCE"
    })
    void 출석_수정_정상_동작(int year, int month, int day, int hour, int minute, AttendanceStatus attendanceStatus) {
        //given
        String crewName = "우가";
        Crew crew = new Crew(crewName);
        Crews crews = new Crews(Set.of(crew));
        LocalDateTime currentDateTime = LocalDateTime.of(2025, 2, 28, 9, 59);
        AttendanceBook attendanceBook = new AttendanceBook(crews, currentDateTime);

        //when
        LocalDateTime modifyTime = LocalDateTime.of(year, month, day, hour, minute);
        AttendanceTime modifiedAttendanceTime = attendanceBook.modifyAttendance(crew, modifyTime);

        //then
        Assertions.assertThat(modifiedAttendanceTime.getAttendanceStatus()).isEqualTo(attendanceStatus);
        Assertions.assertThat(modifiedAttendanceTime.getAttendanceTime()).isEqualTo(modifyTime);
    }

    @Test
    void 출석_기록() {
        //given
        String crewName = "우가";
        Crew crew = new Crew(crewName);
        Crews crews = new Crews(Set.of(crew));

        LocalDateTime currentDateTime = LocalDateTime.of(2025, 2, 28, 9, 59);
        AttendanceBook attendanceBook = new AttendanceBook(crews, currentDateTime);

        //when & then
        Assertions.assertThat(attendanceBook.findAttendanceRecord(crew).getAttendanceRecord().size()).isEqualTo(19);
    }

    @Test
    void 제적_위험자_확인() {
        //given
        String crewName1 = "우가";
        String crewName2 = "부기";
        String crewName3 = "헤일러";

        Crew crew1 = new Crew(crewName1);
        Crew crew2 = new Crew(crewName2);
        Crew crew3 = new Crew(crewName3);

        Crews crews = new Crews(Set.of(crew1, crew2, crew3));

        LocalDateTime currentDateTime = LocalDateTime.of(2025, 2, 24, 9, 59);
        AttendanceBook attendanceBook = new AttendanceBook(crews, currentDateTime);

        attendanceBook.registerAttendance(crew1, LocalDateTime.of(2025, 2, 25, 10, 31));
        attendanceBook.registerAttendance(crew1, LocalDateTime.of(2025, 2, 24, 13, 6));

        attendanceBook.registerAttendance(crew2, LocalDateTime.of(2025, 2, 26, 10, 6));
        attendanceBook.registerAttendance(crew2, LocalDateTime.of(2025, 2, 28, 10, 31));

        attendanceBook.registerAttendance(crew3, LocalDateTime.of(2025, 2, 27, 10, 6));
        attendanceBook.registerAttendance(crew3, LocalDateTime.of(2025, 2, 28, 10, 31));

        //when & then
        Assertions.assertThat(attendanceBook.findPenaltyCrews().size()).isEqualTo(3);
    }

    @Test
    void 닉네임_순으로_정렬() {
        //given
        String crewName1 = "우가";
        String crewName2 = "부기";
        String crewName3 = "헤일러";

        Crew crew1 = new Crew(crewName1);
        Crew crew2 = new Crew(crewName2);
        Crew crew3 = new Crew(crewName3);

        Crews crews = new Crews(Set.of(crew1, crew2, crew3));

        LocalDateTime currentDateTime = LocalDateTime.of(2025, 2, 24, 9, 59);
        AttendanceBook attendanceBook = new AttendanceBook(crews, currentDateTime);

        attendanceBook.registerAttendance(crew1, LocalDateTime.of(2025, 2, 25, 10, 31));
        attendanceBook.registerAttendance(crew1, LocalDateTime.of(2025, 2, 24, 13, 6));

        attendanceBook.registerAttendance(crew2, LocalDateTime.of(2025, 2, 26, 10, 6));
        attendanceBook.registerAttendance(crew2, LocalDateTime.of(2025, 2, 28, 10, 31));

        attendanceBook.registerAttendance(crew3, LocalDateTime.of(2025, 2, 27, 10, 6));
        attendanceBook.registerAttendance(crew3, LocalDateTime.of(2025, 2, 28, 10, 31));

        //when
        List<RiskCrew> penaltyCrews = attendanceBook.findPenaltyCrews();

        //then
        assertAll(
                () -> assertEquals(3, penaltyCrews.size()),
                () -> assertEquals(crewName2, penaltyCrews.get(0).getName()),
                () -> assertEquals(crewName1, penaltyCrews.get(1).getName()),
                () -> assertEquals(crewName3, penaltyCrews.get(2).getName()));
    }

    @Test
    void 결석_순으로_정렬() {
        //given
        String crewName1 = "우가";
        String crewName2 = "부기";
        String crewName3 = "헤일러";

        Crew crew1 = new Crew(crewName1);
        Crew crew2 = new Crew(crewName2);
        Crew crew3 = new Crew(crewName3);

        Crews crews = new Crews(Set.of(crew1, crew2, crew3));

        LocalDateTime currentDateTime = LocalDateTime.of(2025, 2, 24, 9, 59);
        AttendanceBook attendanceBook = new AttendanceBook(crews, currentDateTime);

        attendanceBook.registerAttendance(crew1, LocalDateTime.of(2025, 2, 24, 13, 31));
        attendanceBook.registerAttendance(crew1, LocalDateTime.of(2025, 2, 25, 10, 31));
        attendanceBook.registerAttendance(crew1, LocalDateTime.of(2025, 2, 26, 10, 31));

        attendanceBook.registerAttendance(crew2, LocalDateTime.of(2025, 2, 24, 13, 31));
        attendanceBook.registerAttendance(crew2, LocalDateTime.of(2025, 2, 25, 10, 31));

        attendanceBook.registerAttendance(crew3, LocalDateTime.of(2025, 2, 24, 12, 59));
        attendanceBook.registerAttendance(crew3, LocalDateTime.of(2025, 2, 25, 10, 31));

        //when
        List<RiskCrew> penaltyCrews = attendanceBook.findPenaltyCrews();

        //then
        assertAll(
                () -> assertEquals(3, penaltyCrews.size()),
                () -> assertEquals(crewName1, penaltyCrews.get(0).getName()),
                () -> assertEquals(crewName2, penaltyCrews.get(1).getName()),
                () -> assertEquals(crewName3, penaltyCrews.get(2).getName()));
    }

    @Test
    void 결석_횟수가_같을때_지각_횟수_정렬() {
        //given
        String crewName1 = "우가";
        String crewName2 = "부기";
        String crewName3 = "헤일러";

        Crew crew1 = new Crew(crewName1);
        Crew crew2 = new Crew(crewName2);
        Crew crew3 = new Crew(crewName3);

        Crews crews = new Crews(Set.of(crew1, crew2, crew3));

        LocalDateTime currentDateTime = LocalDateTime.of(2025, 2, 24, 9, 59);
        AttendanceBook attendanceBook = new AttendanceBook(crews, currentDateTime);

        attendanceBook.registerAttendance(crew2, LocalDateTime.of(2025, 2, 24, 13, 31));
        attendanceBook.registerAttendance(crew2, LocalDateTime.of(2025, 2, 25, 10, 31));

        attendanceBook.registerAttendance(crew1, LocalDateTime.of(2025, 2, 24, 13, 31));
        attendanceBook.registerAttendance(crew1, LocalDateTime.of(2025, 2, 25, 10, 31));
        attendanceBook.registerAttendance(crew1, LocalDateTime.of(2025, 2, 26, 10, 30));

        attendanceBook.registerAttendance(crew3, LocalDateTime.of(2025, 2, 24, 12, 59));
        attendanceBook.registerAttendance(crew3, LocalDateTime.of(2025, 2, 25, 10, 31));

        //when
        List<RiskCrew> penaltyCrews = attendanceBook.findPenaltyCrews();

        //then
        assertAll(
                () -> assertEquals(3, penaltyCrews.size()),
                () -> assertEquals(crewName1, penaltyCrews.get(0).getName()),
                () -> assertEquals(crewName2, penaltyCrews.get(1).getName()),
                () -> assertEquals(crewName3, penaltyCrews.get(2).getName()));
    }

}
