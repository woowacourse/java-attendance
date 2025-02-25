package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 24));
        AttendanceTime attendanceTime = new AttendanceTime(LocalTime.of(10, 1));

        attendanceBook.attend(name, attendanceDate, attendanceTime);
        Assertions.assertThatThrownBy(() -> attendanceBook.checkAlreadyAttended(name, attendanceDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이미 출석한 경우 예외 테스트")
    @Test
    void attendAlreadyAttendedTest() {
        String name = "빙봉";
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 13));

        Assertions.assertThatThrownBy(() -> attendanceBook.checkAlreadyAttended(name, attendanceDate))
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

        Assertions.assertThat(attendanceBook.findCrewByName(name).findAttendanceByDate(attendanceDate).isLate()).isEqualTo(false);
    }
}
