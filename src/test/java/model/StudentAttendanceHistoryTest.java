package model;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudentAttendanceHistoryTest {
    StudentAttendanceHistory studentAttendanceHistory = new StudentAttendanceHistory(
            List.of(LocalDateTime.of(2024, 12, 2, 0, 0),
                    LocalDateTime.of(2024, 12, 3, 0, 0))
    );
    @Test
    @DisplayName("같은 로컬데이트타임 찾기 메서드 테스트")
    void test1() {
        org.assertj.core.api.Assertions.assertThat(
                studentAttendanceHistory.findSameDay(LocalDateTime.of(2024, 12, 2, 0, 0))
        ).isEqualTo(LocalDateTime.of(2024, 12, 2, 0, 0));
    }

    @Test
    @DisplayName("같은 날짜인지 확인하는 메서드 테스트")
    void test2() {
        Assertions.assertTrue(studentAttendanceHistory.isExistSameDay(
                LocalDateTime.of(2024, 12, 2, 0, 0),
                LocalDateTime.of(2024, 12, 2, 0, 0)
        ));
    }

}