package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.AttendanceConvertor;
import util.AttendanceFileReader;

public class AttendanceBookTest {

    private AttendanceBook attendanceBook;

    @BeforeEach
    void createAttendanceBook() {
        List<String> contents = AttendanceFileReader.readFile();
        Map<String, List<LocalDateTime>> attendanceFileContents = AttendanceConvertor.convertToAttendances(contents);
        attendanceBook = new AttendanceBook(attendanceFileContents);
    }

    @DisplayName("닉네임으로 크루 탐색 기능 테스트")
    @Test
    void findCrewByNameTest() {
        assertThat(attendanceBook.findCrewByName("쿠키"))
                .isEqualTo(new Crew("쿠키", new Attendances(List.of())));
    }

    @DisplayName("없는 크루 탐색 테스트")
    @Test
    void findCrewByNameExceptionTest() {
        assertThatThrownBy(() -> attendanceBook.checkExistCrew("메삼"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("출석 기능 테스트")
    @Test
    void attendTest() {
        String name = "빙봉";
        Crew crew = attendanceBook.findCrewByName(name);
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 24));
        AttendanceTime attendanceTime = new AttendanceTime(LocalTime.of(10, 1));

        attendanceBook.attend(crew, attendanceDate, attendanceTime);
        Assertions.assertThatThrownBy(() -> attendanceBook.checkAlreadyAttended(crew, attendanceDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이미 출석한 경우 예외 테스트")
    @Test
    void attendAlreadyAttendedTest() {
        String name = "빙봉";
        Crew crew = attendanceBook.findCrewByName(name);
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 13));

        Assertions.assertThatThrownBy(() -> attendanceBook.checkAlreadyAttended(crew, attendanceDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("출석 수정을 위한 출석 기록 확인 테스트")
    @Test
    void checkAttendanceExistForEditTest() {
        String name = "빙봉";
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 27));

        Assertions.assertThatThrownBy(() -> attendanceBook.checkAttendanceExist(name, attendanceDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("지각 -> 출석 수정 테스트")
    @Test
    void attendEditTest() {
        String name = "빙봉";
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 2));
        AttendanceTime attendanceTime = new AttendanceTime(LocalTime.of(12, 58));

        attendanceBook.editAttendance(name, attendanceDate, attendanceTime);

        assertThat(attendanceBook.findCrewByName(name).findAttendanceByDate(attendanceDate).isLate()).isEqualTo(false);
    }

    @DisplayName("크루 이름 기반 출석 기록들 탐색 테스트")
    @Test
    void findAttendancesByCrewNameTest() {
        String name = "메이";
        List<Attendance> attendances = attendanceBook.getAttendancesByName(name);

        assertThat(attendances.size()).isEqualTo(5);
    }

    @DisplayName("제적 위험자 확인 테스트")
    @ParameterizedTest
    @MethodSource("provideExpelledCrew")
    void findRiskOfExpulsionCrewTest(Crew crew) {
        assertThat(crew.getCrewStatus()).isNotEqualTo(CrewStatus.NORMAL);
    }

    private static Stream<Arguments> provideExpelledCrew() {
        List<String> contents = AttendanceFileReader.readFile();
        Map<String, List<LocalDateTime>> attendanceFileContents = AttendanceConvertor.convertToAttendances(contents);
        AttendanceBook attendanceBook = new AttendanceBook(attendanceFileContents);
        List<Crew> crews = attendanceBook.findRiskOfExpulsionCrew();

        return crews.stream()
                .map(Arguments::arguments);
    }
}
