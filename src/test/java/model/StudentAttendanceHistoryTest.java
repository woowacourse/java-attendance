package model;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentAttendanceHistoryTest {
    AttendanceDateTime addAttendanceDateTime;
    AttendanceDateTime deleteAttendanceDateTime ;
    StudentAttendanceHistory studentAttendanceHistory;

    @BeforeEach
    void set() {
        addAttendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 12, 12));
        deleteAttendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 13, 12, 12));
        studentAttendanceHistory = new StudentAttendanceHistory(
                List.of(new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 12, 12)),
                        new AttendanceDateTime(LocalDateTime.of(2024, 12, 13, 12, 12)))
        );;
    }

    @Test
    @DisplayName("날짜를 추가하는 메서드 테스트")
    void test1() {
        studentAttendanceHistory.addAttendanceDateTime(addAttendanceDateTime);
        Assertions.assertTrue(
                studentAttendanceHistory.isExistSameAttendanceDateTime(addAttendanceDateTime)
        );
    }

    @Test
    @DisplayName("날짜를 제거하는 메서드 테스트")
    void test2() {
        studentAttendanceHistory.modifyAttendance(deleteAttendanceDateTime);
        Assertions.assertFalse(
                studentAttendanceHistory.isExistSameAttendanceDateTime(deleteAttendanceDateTime)
        );
    }

    @Test
    @DisplayName("같은 날짜가 있는지 확인하는 메서드 테스트")
    void test3() {
        AttendanceDateTime wantToFind = new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 12, 12));
        Assertions.assertTrue(
                studentAttendanceHistory.isExistSameAttendanceDateTime(wantToFind)
        );
    }

    @Test
    @DisplayName("같은 날짜를 찾고, 이를 반환하는 메서드 테스트")
    void test4() {
        AttendanceDateTime wantToFind = new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 13, 13));
        Assertions.assertTrue(
                studentAttendanceHistory.findSameAttendanceDate(wantToFind).isSameDate(wantToFind)
        );
    }

    @Test
    @DisplayName("같은 날짜가 없다면, 예외를 발생하는 메서드 테스트")
    void test5() {
        AttendanceDateTime wantToFind = new AttendanceDateTime(LocalDateTime.of(2024, 12, 1, 13, 13));
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> studentAttendanceHistory.findSameAttendanceDate(wantToFind)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석하지 않은 요일입니다. 먼저 출석을 진행 후, 수정해 주세요.");
    }
}
