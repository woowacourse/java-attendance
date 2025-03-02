package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentTest {
    AttendanceDate addAttendanceDate;
    AttendanceDate deleteAttendanceDate;
    AttendanceTime addAttendanceTime = new AttendanceTime(LocalTime.of(8, 1));
    StudentAttendanceHistory studentAttendanceHistory;
    Student student;

    @BeforeEach
    void set() {
        addAttendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 12));
        deleteAttendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 13));
        studentAttendanceHistory = new StudentAttendanceHistory(
                Map.of(
                        new AttendanceDate(LocalDate.of(2024, 12, 12)), new AttendanceTime(LocalTime.of(8, 0))
                ) );
        student = new Student("이든", studentAttendanceHistory);
    }

    @Test
    @DisplayName("새로운 출석 정보를 저장하는 메서드 테스트")
    void test1() {
        student.addAttendanceDateTime(addAttendanceDate, addAttendanceTime);
        Assertions.assertTrue(
                student.getStudentAttendanceHistory().isExistSameAttendanceDate(addAttendanceDate)
        );
    }

    @Test
    @DisplayName("수정하고 싶은 날짜의 일자를 비교하여 삭제하는 메서드 테스트")
    void test2() {
        student.modifyAttendanceDateTime(addAttendanceDate, addAttendanceTime);

        Assertions.assertEquals(student.getStudentAttendanceHistory().getAttendanceHistory()
                .get(new AttendanceDate(LocalDate.of(2024, 12, 12))), new AttendanceTime(LocalTime.of(8, 1)));
    }

    @Test
    @DisplayName("수정하고 싶은 날짜의 일자를 저장하는 메서드 테스트")
    void test3() {
        student.modifyAttendanceDateTime(addAttendanceDate, addAttendanceTime);
        Assertions.assertTrue(
                student.getStudentAttendanceHistory().isExistSameAttendanceDate(addAttendanceDate)
        );
    }

    @Test
    @DisplayName("수정하고 싶은 날짜의 일자를 저장하는 메서드 테스트")
    void test4() {
        student.modifyAttendanceDateTime(addAttendanceDate, addAttendanceTime);
        Assertions.assertEquals(student.getStudentAttendanceHistory().getAttendanceHistory().get(addAttendanceDate),
                addAttendanceTime);
    }

    @Test
    @DisplayName("이름이 같은지 확이하는 메서드 테스트")
    void test5() {
        Assertions.assertTrue(
                student.isSameName("이든")
        );
    }

    @Test
    @DisplayName("날짜를 통해 해달 날짜의 입실한 시간 찾는 메서드 테스트")
    void test6() {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 12));
        Assertions.assertEquals(
                student.findAttendanceTimeByAttendanceDate(attendanceDate),
                new AttendanceTime(LocalTime.of(8, 0))
        );
    }

    @Test
    @DisplayName("파일에 있지 않은 정보들을 업데이트 하는 메서드 구현")
    void test7() {
        AttendanceDate start = new AttendanceDate(LocalDate.of(2024, 12, 1));

        student.updateMissingAttendanceRecords(start, new AttendanceDate(LocalDate.of(2024, 12, 12)));

        Assertions.assertTrue(
                student.getStudentAttendanceHistory()
                        .isExistSameAttendanceDate(new AttendanceDate(LocalDate.of(2024, 12, 4)))
        );

        Assertions.assertEquals(
                student.findAttendanceTimeByAttendanceDate(new AttendanceDate(LocalDate.of(2024, 12, 4))),
                new AttendanceTime(LocalTime.of(0, 0))
        );
    }

    @Test
    @DisplayName("이미 출석한 요일이면 예외 발생 테스트")
    void test8() {
        org.assertj.core.api.Assertions.assertThatThrownBy(
                        () -> student.isAlreadyExistAttendanceDate(new AttendanceDate(LocalDate.of(2024, 12, 12)))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 출석한 요일입니다. 다시 출석하고 싶으면 수정 기능을 이용해 주세요.");
    }

    @Test
    @DisplayName("출석하지 않은 요일을 수정하고자 할때 예외 발생 테스트")
    void test9() {
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> student.validateAttendanceBeforeModification(new AttendanceDate(LocalDate.of(2024, 12, 31)))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석하지 않은 요일입니다. 수정하고 싶으면 출석을 먼저 진행해 주세요.");
    }
}
