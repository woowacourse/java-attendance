package model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.FileInformationProvider;

public class AttendanceBookTest {
    @Test
    @DisplayName("학생 이름으로 학생 찾기 구현 테스트")
    void 특정_학생의_출석_시간_가져오기_구현() throws IOException {
        String expect = "빙티";
        AttendanceBook attendanceBook = new AttendanceBook(FileInformationProvider.loadStudentAttendance());
        String result = attendanceBook.findStudentByNickName(expect).getName();
        assertThat(expect).isEqualTo(result);
    }

    @Test
    @DisplayName("제적 위험자 조건에 맞는 크루 찾기 테스트")
    void 재적_위험자_조건에_맞는_크루_찾기_테스트() {
        List<Student> students = List.of(
                new Student("빙티", List.of(
                        LocalDateTime.of(2024, 12, 13, 10, 31),
                        LocalDateTime.of(2024, 12, 12, 10, 31),
                        LocalDateTime.of(2024, 12, 11, 10, 31)
                )),
                new Student("이든", List.of(
                        LocalDateTime.of(2024, 12, 13, 10, 31),
                        LocalDateTime.of(2024, 12, 12, 10, 31),
                        LocalDateTime.of(2024, 12, 11, 10, 31)
                )),
                new Student("쿠키", List.of(
                        LocalDateTime.of(2024, 12, 13, 10, 0),
                        LocalDateTime.of(2024, 12, 12, 10, 0),
                        LocalDateTime.of(2024, 12, 11, 10, 0)
                ))
        );
        AttendanceBook attendanceBook = new AttendanceBook(students);

        List<Student> expulsionRiskStudents = attendanceBook.findExpulsionRiskStudents();
        Assertions.assertThat(expulsionRiskStudents)
                .extracting(Student::getName)
                .contains("빙티", "이든");
    }

    @Test
    @DisplayName("출결 기록이 없는 경우 null 처리 테스트")
    void 출결_기록이_없는_경우_null_처리_테스트() throws IOException {
        List<Student> students = List.of(
                new Student("빙티", List.of(
                        LocalDateTime.of(2024, 12, 13, 10, 31),
                        LocalDateTime.of(2024, 12, 12, 10, 31),
                        LocalDateTime.of(2024, 12, 11, 10, 31)
                )),
                new Student("이든", List.of(
                        LocalDateTime.of(2024, 12, 13, 10, 31),
                        LocalDateTime.of(2024, 12, 12, 10, 31),
                        LocalDateTime.of(2024, 12, 11, 10, 31)
                )),
                new Student("쿠키", List.of(
                        LocalDateTime.of(2024, 12, 13, 10, 0),
                        LocalDateTime.of(2024, 12, 12, 10, 0),
                        LocalDateTime.of(2024, 12, 11, 10, 0)
                ))
        );
        AttendanceBook attendanceBook = new AttendanceBook(students);
        attendanceBook.updateNonExistentAttendanceRecords(LocalDate.of(2024,12,15));
        Student student = attendanceBook.findStudentByNickName("이든");
        LocalTime result = student.findAttendanceLocalTimeByLocalDate(LocalDate.of(2024,12,14));
        assertNull(result);
    }
}
