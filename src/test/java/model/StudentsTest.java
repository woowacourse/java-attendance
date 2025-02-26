package model;

import controller.AttendanceManagementController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudentsTest {
    AttendanceManagementController attendanceManagementController = new AttendanceManagementController();
    Students students = attendanceManagementController.updateStudentAttendanceRecord();

    @Test
    @DisplayName("존재하지 않는 학생 찾으면 예외 발생 테스트")
    void test1() {
        Assertions.assertThatThrownBy(() -> students.findStudentByName("말론"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 존재하지 않는 학생의 이름입니다.");
    }

    @Test
    @DisplayName("존재하는 학생을 찾을 때 true 리턴해주는 메서드 테스트")
    void test2() {
        Assertions.assertThat(students.isExistStudent("이든"))
                .isTrue();
    }


}