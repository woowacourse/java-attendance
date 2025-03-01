import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {
    @Test
    @DisplayName("특정 학생의 출석 시간 가져오기 구현")
    void 특정_학생의_출석_시간_가져오기_구현() throws IOException {
        String name = "빙티";
        FileInformationProvider fileProvider = new FileInformationProvider();
        AttendanceBook attendanceBook = new AttendanceBook(fileProvider.loadStudentAttendance());
        Map<LocalDate, LocalTime> result = attendanceBook.findStudentAttendanceTimeRecord(name);

        Map<LocalDate, LocalTime> expect = Map.of(
                LocalDate.of(2024,12,3), LocalTime.of(10,7),
                LocalDate.of(2024,12,2), LocalTime.of(13,0)
        );
        assertThat(expect).isEqualTo(result);
    }

    @Test
    @DisplayName("제적 위험자 조건에 맞는 크루 찾기 테스트")
    void 재적_위험자_조건에_맞는_크루_찾기_테스트(){
        List<Student> students = List.of(
                new Student("빙티", List.of(
                        LocalDateTime.of(2024,12,13,10,31),
                        LocalDateTime.of(2024,12,12,10,31),
                        LocalDateTime.of(2024,12,11,10,31)
                )),
                new Student("이든", List.of(
                        LocalDateTime.of(2024,12,13,10,31),
                        LocalDateTime.of(2024,12,12,10,31),
                        LocalDateTime.of(2024,12,11,10,31)
                )),
                new Student("쿠키", List.of(
                        LocalDateTime.of(2024,12,13,10,0),
                        LocalDateTime.of(2024,12,12,10,0),
                        LocalDateTime.of(2024,12,11,10,0)
                ))
        );
        AttendanceBook attendanceBook = new AttendanceBook(students);

        List<Student> expulsionRiskStudents = attendanceBook.findExpulsionRiskStudents();
        Assertions.assertThat(expulsionRiskStudents)
                .extracting(Student::getName)
                .contains("빙티","이든");
    }
}
