package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudentTest {
    StudentAttendanceHistory studentAttendanceHistory = new StudentAttendanceHistory(
            List.of(LocalDateTime.of(2024, 12, 2, 0, 0),
                    LocalDateTime.of(2024, 12, 3, 0, 0),
                    LocalDateTime.of(2024, 12, 12, 0, 0))
    );

    Student student = new Student("이든", studentAttendanceHistory);

    @Test
    @DisplayName("이미 출석한 요일을 재출석할 경우 예외 발생 테스트")
    void test1() {
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                student.isAlreadyAttendanceDate(new TodayDate())
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 출석한 요일입니다. 수정하고 싶으시면 수정 메뉴를 이용해 주세요.");
    }

}