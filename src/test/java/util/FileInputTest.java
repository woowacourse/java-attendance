package util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import model.AttendanceDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileInputTest {
    @Test
    @DisplayName("파일 입력 테스트")
    void test1() {
        Map<String, List<AttendanceDateTime>> studentInformation = FileInput.readFileAndCreateStudentRepository();
        Assertions.assertTrue(studentInformation.containsKey("이든"));
        Assertions.assertTrue(studentInformation.containsKey("짱수"));
        Assertions.assertTrue(studentInformation.containsKey("빙봉"));
        Assertions.assertTrue(studentInformation.containsKey("빙티"));
        Assertions.assertTrue(studentInformation.containsKey("쿠키"));
        Assertions.assertTrue(studentInformation.get("쿠키").contains(new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 13, 1))));
        Assertions.assertTrue(studentInformation.get("쿠키").contains(new AttendanceDateTime(LocalDateTime.of(2024, 12, 3, 10, 6))));

        Assertions.assertTrue(studentInformation.get("빙티").contains(new AttendanceDateTime(LocalDateTime.of(2024, 12, 3, 10, 7))));
        Assertions.assertTrue(studentInformation.get("빙티").contains(new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 13, 0))));

        Assertions.assertTrue(studentInformation.get("이든").contains(new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 13, 2))));
        Assertions.assertTrue(studentInformation.get("이든").contains(new AttendanceDateTime(LocalDateTime.of(2024, 12, 3, 10, 6))));

        Assertions.assertTrue(studentInformation.get("빙봉").contains(new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 13, 6))));
        Assertions.assertTrue(studentInformation.get("빙봉").contains(new AttendanceDateTime(LocalDateTime.of(2024, 12, 3, 10, 3))));

        Assertions.assertTrue(studentInformation.get("짱수").contains(new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 13, 0))));
        Assertions.assertTrue(studentInformation.get("짱수").contains(new AttendanceDateTime(LocalDateTime.of(2024, 12, 3, 10, 0))));

    }
  
}