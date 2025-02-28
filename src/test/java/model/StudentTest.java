package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudentTest {
    AttendanceDateTime attendanceDateTime1;
    AttendanceDateTime attendanceDateTime2;
    List<AttendanceDateTime> studentAttendanceHistoryList;
    StudentAttendanceHistory studentAttendanceHistory;
    Student student;

    @Test
    @BeforeEach
    void set(){
        attendanceDateTime1 = new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 13, 2));;
        attendanceDateTime2 = new AttendanceDateTime(LocalDateTime.of(2024, 12, 3, 10, 6));
        studentAttendanceHistoryList = new ArrayList<>(List.of(attendanceDateTime1, attendanceDateTime2));
        studentAttendanceHistory = new StudentAttendanceHistory(studentAttendanceHistoryList);
        student = new Student("이든", studentAttendanceHistory);;
    }

    @Test
    @DisplayName("이름이 같은지 확인하는 메서드 테스트")
    void test1() {
        Assertions.assertThat(student.isSameName("이든")).isTrue();
    }

    @Test
    @DisplayName("시간 추가하는 메서드 테스트")
    void test2() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 4, 11, 6));
        student.addTime(attendanceDateTime);
        org.junit.jupiter.api.Assertions.assertTrue(student.getStudentAttendanceHistory().getAttendanceHistory().contains(
                attendanceDateTime
        ));
    }

    @Test
    @DisplayName("같은 날짜 찾는 메서드 테스트")
    void test3() {
        Assertions.assertThat(student.findSameDay(
                new AttendanceDateTime(LocalDateTime.of(2024, 12, 3, 10, 6))
        )).isEqualTo(attendanceDateTime2);
    }

    @Test
    @DisplayName("날짜 수정 메서드 테스트")
    void test4() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 13, 3));
        student.modifyRecord(attendanceDateTime);
        Assertions.assertThat(student.getStudentAttendanceHistory().getAttendanceHistory().contains(attendanceDateTime))
                .isTrue();

        Assertions.assertThat(
                student.getStudentAttendanceHistory().getAttendanceHistory().contains(attendanceDateTime1)).isFalse();
    }

    @Test
    @DisplayName("재출석을 시도하면 예외 발생 테스트")
    void test5() {
        Assertions.assertThatThrownBy(() -> student.validateAlreadyAttendanceDate(new TodayDate(LocalDate.of(2024, 12, 2))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 출석한 요일입니다. 수정하고 싶으시면 수정 메뉴를 이용해 주세요.");
    }
}