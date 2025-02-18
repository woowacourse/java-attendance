import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentRepositoryTest {
    Student student;
    Student student1 = new Student("짱수");
    Student student2 = new Student("이든");
    Student student3 = new Student("쿠키");
    Student student4 = new Student("빙봉");
    Student student5 = new Student("빙티");
    StudentRepository studentRepository;

    @BeforeEach
    public void test() {
        studentRepository = new StudentRepository();
        studentRepository.addStudent(student1);
        studentRepository.addStudent(student2);
        studentRepository.addStudent(student3);
        studentRepository.addStudent(student4);
        studentRepository.addStudent(student5);

    }
    @Test
    @DisplayName("존재하지 않는 학생을 입력시 예외처리 한다.")
    void test1() {
        String input = "포비";

        /*InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        OutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));*/

        Assertions.assertTrue(studentRepository.notExistStudent("포비"));
    }

    @Test
    @DisplayName("등교시간 잘못 입력시 예외처리 한다.")
    void test2() {
        LocalTime localTime = LocalTime.of(7,59);

        assertThatThrownBy(() -> student.isStartTime(localTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
    }

    @Test
    @DisplayName("이름 기준으로 학생 객체 찾는 기능")
    void test3() {
        String name = "짱수";
        Student student6 = studentRepository.findStudentByName(name);
        assertThat(student6).isEqualTo(student1);
    }

    @Test
    @DisplayName("등교 시간을 바탕으로 출석 기록 업데이트하는 기능")
    void test4() {
        String name = "짱수";
        int month = LocalDateTime.now().getMonthValue();
        int day = LocalDateTime.now().getDayOfMonth();
        LocalDateTime localDateTime = LocalDateTime.of(2024, month, day,9,59);

        Student student6 = studentRepository.findStudentByName(name);
        student6.updateState(localDateTime);
        assertThat(student6.attendance).isEqualTo(1);

    }

    @Test
    @DisplayName("등교 시간을 바탕으로 출석 기록을 map에 업데이트하는 기능")
    void test5() {
        String name = "짱수";
        int month = LocalDateTime.now().getMonthValue();
        int day = LocalDateTime.now().getDayOfMonth();
        LocalDateTime localDateTime = LocalDateTime.of(2024, month, day,9,59);

        Student student6 = studentRepository.findStudentByName(name);
        student6.updateState(localDateTime);

        assertThat(student6.record.get(localDateTime)).isEqualTo(AttendanceStatus.ATTENDANCE);
    }


}
