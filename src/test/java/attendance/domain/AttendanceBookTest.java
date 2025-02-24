package attendance.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import attendance.AttendanceFixture;
import attendance.dto.CrewNameAndAcademicStatusDTO;
import attendance.repository.AttendanceRepository;
import java.util.List;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceBookTest {

    @DisplayName("명단에 없는 크루원이면 예외를 발생한다.")
    @Test
    void 명단에_없는_크루원이면_예외를_발생한다() {

        //given
        Set<String> names = Set.of("a", "b", "c");
        AttendanceBook attendanceBook = new AttendanceBook(names);
        //when

        //then
        Assertions.assertThatThrownBy(() -> attendanceBook.checkName("d"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석부에 없는 크루원입니다.");
    }

    @DisplayName("제적 위험자 리스트를 가져온다.")
    @ParameterizedTest
    @CsvSource(value = {
            "체체,제적", "체글렛,면담", "피글렛,경고"
    })
    void 제적_위험자_리스트를_가져온다(String name, String academicStatus) {

        // given
        AttendanceBook attendanceBook = new AttendanceBook(Set.of("체체", "체글렛", "피글렛", "피글체"));
        List<Attendance> attendances = AttendanceFixture.makeAttendance();
        AttendanceRepository attendanceRepository = new AttendanceRepository(attendances);

        // when
        List<CrewNameAndAcademicStatusDTO> attendanceCountAndAcademicStatusDTOS = attendanceBook.getCrewAtRiskOfExpulsion(
                attendanceRepository, academicStatus, 2);

        // then
        assertAll(() -> {

            assertEquals(attendanceCountAndAcademicStatusDTOS.size(), 1);
            assertEquals(attendanceCountAndAcademicStatusDTOS.getFirst().crewName(), name);
        });
    }

}
