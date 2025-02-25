package model;

import controller.Controller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceCountTest {
    private final AttendanceBook studentRepository = new Controller().createStudentRepository();

    @Test
    @DisplayName("총 출석 횟수 업데이트 기능")
    void test1(){
        Student student = studentRepository.findStudentByName("빙티");
        long expect = 1;
        student.updateAttendanceTotalCount();
        long result = student.getAttendanceCount().getAttendanceTotalCount();

        Assertions.assertEquals(expect, result);
    }

}