package util;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import model.AttendanceDateTime;
import model.StudentAttendanceHistory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileInputTest {
    String regex = "[가-힣]+,\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";

    @Test
    @DisplayName("파일에 있는 자료의 형식이 맞지 않으면 예외 던지는 메서드 테스트")
    void test1() {
        String information = "쿠키,,2024-12-02 13:01";
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> information.matches(regex))
                        .isInstanceOf(IllegalArgumentException.class)
                                .hasMessage("[ERROR] 파일 자료 형식에 맞지 않습니다.");
    }

    @Test
    @DisplayName("파일에 있는 자료의 형식이 맞으면 통과하는 테스트")
    void test2() {
        String information = "쿠키,2024-12-02 13:01";
        Assertions.assertTrue(information.matches(regex));
    }

    @Test
    @DisplayName("파일 읽는 메서드 테스트")
    void test3() {
        Map<String, StudentAttendanceHistory> studentInfFormationInFile = FileInput.readFileAndMakeStudentInformation();

        Assertions.assertTrue(studentInfFormationInFile.containsKey("이든"));
        Assertions.assertTrue(studentInfFormationInFile.containsKey("짱수"));
        Assertions.assertTrue(studentInfFormationInFile.containsKey("빙봉"));
        Assertions.assertTrue(studentInfFormationInFile.containsKey("빙티"));
        Assertions.assertTrue(studentInfFormationInFile.containsKey("쿠키"));
        Assertions.assertTrue(studentInfFormationInFile.get("쿠키").isExistSameAttendanceDateTime(new AttendanceDateTime(
                LocalDateTime.of(2024, 12, 2, 13, 1))));
        Assertions.assertTrue(studentInfFormationInFile.get("쿠키").isExistSameAttendanceDateTime(new AttendanceDateTime(LocalDateTime.of(2024, 12, 3, 10, 6))));

        Assertions.assertTrue(studentInfFormationInFile.get("빙티").isExistSameAttendanceDateTime(new AttendanceDateTime(LocalDateTime.of(2024, 12, 3, 10, 7))));
        Assertions.assertTrue(studentInfFormationInFile.get("빙티").isExistSameAttendanceDateTime(new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 13, 0))));

        Assertions.assertTrue(studentInfFormationInFile.get("이든").isExistSameAttendanceDateTime(new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 13, 2))));
        Assertions.assertTrue(studentInfFormationInFile.get("이든").isExistSameAttendanceDateTime(new AttendanceDateTime(LocalDateTime.of(2024, 12, 3, 10, 6))));

        Assertions.assertTrue(studentInfFormationInFile.get("빙봉").isExistSameAttendanceDateTime(new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 13, 6))));
        Assertions.assertTrue(studentInfFormationInFile.get("빙봉").isExistSameAttendanceDateTime(new AttendanceDateTime(LocalDateTime.of(2024, 12, 3, 10, 3))));

        Assertions.assertTrue(studentInfFormationInFile.get("짱수").isExistSameAttendanceDateTime(new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 13, 0))));
        Assertions.assertTrue(studentInfFormationInFile.get("짱수").isExistSameAttendanceDateTime(new AttendanceDateTime(LocalDateTime.of(2024, 12, 3, 10, 0))));

    }
}