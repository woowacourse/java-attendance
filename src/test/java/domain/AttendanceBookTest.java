package domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {
    AttendanceBook attendanceBook;

    @BeforeEach
    void setUp() {
        Map<String, Attendances> initValue = new HashMap<>();
        initValue.put("test1", new Attendances());
        initValue.put("test2", new Attendances());
        attendanceBook = new AttendanceBook(initValue);
    }

    @DisplayName("특정 크루의 출석을 저장한다")
    @Test
    void saveCrewAttendanceTest() {
        String nickname = "test1";
        LocalDateTime testTime = LocalDateTime.of(2024, 12, 3, 10, 5);

        attendanceBook.addAttendanceForCrew(nickname, testTime);

        Attendances attendances = attendanceBook.getCrewAttendances().get(nickname);
        Assertions.assertEquals(1, attendances.getRecords().size());
    }

    @DisplayName("크루가 존재하지 않으면 예외를 발생시킨다")
    @Test
    void saveCrewAttendanceExceptionTest() {
        String nickname = "test3";
        LocalDateTime testTime = LocalDateTime.of(2024, 12, 3, 10, 5);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> attendanceBook.addAttendanceForCrew(nickname, testTime));
    }
}
