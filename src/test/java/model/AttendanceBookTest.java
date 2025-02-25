package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import controller.Controller;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.InputView;

public class AttendanceBookTest {
    private final AttendanceBook studentRepository = new Controller().createStudentRepository();

    @Test
    @DisplayName("존재하지 않는 학생을 입력시 예외처리 한다.")
    void test1() {
        String input = "포비";
        assertThatThrownBy(() -> studentRepository.notExistStudent(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @Test
    @DisplayName("등교시간 잘못 입력시 예외처리 한다.")
    void test2() {
        assertThatThrownBy(() -> InputView.isNotOpeningHour(LocalDateTime.of(2024,12,13,7,59)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
    }

    @Test
    @DisplayName("이름 기준으로 학생 객체 찾는 기능")
    void test3() {
        String name = "짱수";
        Student student = studentRepository.findStudentByName(name);
        String expectStudentName = student.getName();
        assertThat(name).isEqualTo(expectStudentName);

    }

    @Test
    @DisplayName("등교 시간을 바탕으로 출석 기록 업데이트하는 기능")
    void test4() {
        String name = "짱수";
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 13,9,59);

        Student student = studentRepository.findStudentByName(name);
        student.attendanceRegister(localDateTime);
        student.updateAttendanceTotalCount();
        assertThat(student.getAttendanceCount().getAttendanceTotalCount()).isEqualTo(3);
    }

    @Test
    @DisplayName("등교 시간을 바탕으로 출석 기록을 map에 업데이트하는 기능")
    void test5() {
        String name = "짱수";
        int month = LocalDateTime.now().getMonthValue();
        int day = LocalDateTime.now().getDayOfMonth();
        LocalDateTime localDateTime = LocalDateTime.of(2025, month, day,9,59);

        Student student6 = studentRepository.findStudentByName(name);
        student6.attendanceRegister(localDateTime);
        Assertions.assertEquals(student6.getAttendanceRecords()
                .getRecord()
                .get(LocalDate.from(localDateTime))
                .getAttendanceStatus(), AttendanceStatus.ATTENDANCE);
    }

    @Test
    @DisplayName("출석 기록 업데이트 하는 메서드 테스트")
    void test7() {
        Student student1 = studentRepository.findStudentByName("빙티");
        student1.modifyAttendanceRecord(LocalDateTime.of(2024,12,3,10,0));
        Assertions.assertEquals(student1.getAttendanceRecords()
                .getRecord()
                .get(LocalDate.of(2024, 12, 3))
                .getAttendanceStatus(), AttendanceStatus.ATTENDANCE);
    }

}
