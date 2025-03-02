package model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.IOException;
import java.time.LocalDateTime;
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
        FileInformationProvider fileProvider = new FileInformationProvider();
        AttendanceBook attendanceBook = new AttendanceBook(fileProvider.loadStudentAttendance());
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
}
