import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import controller.Controller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import model.AttendanceStatus;
import model.Student;
import model.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.InputView;

public class StudentRepositoryTest {
    Controller controller = new Controller();
    StudentRepository studentRepository = controller.createStudentRepository();
    Student student = studentRepository.findStudentByName("빙티");

    @Test
    @DisplayName("존재하지 않는 학생을 입력시 예외처리 한다.")
    void test1() {
        String input = "포비";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        OutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        assertThatThrownBy(() -> studentRepository.notExistStudent(InputView.userInput()))
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
        Student student6 = studentRepository.findStudentByName(name);
        assertThat(student6).isEqualTo(student);
    }

    @Test
    @DisplayName("등교 시간을 바탕으로 출석 기록 업데이트하는 기능")
    void test4() {
        String name = "짱수";
        int month = LocalDateTime.now().getMonthValue();
        int day = LocalDateTime.now().getDayOfMonth();
        LocalDateTime localDateTime = LocalDateTime.of(2025, month, day,9,59);

        Student student6 = studentRepository.findStudentByName(name);
        student6.updateState(localDateTime);
        assertThat(student6.getAttendance()).isEqualTo(1);

    }

    @Test
    @DisplayName("등교 시간을 바탕으로 출석 기록을 map에 업데이트하는 기능")
    void test5() {
        String name = "짱수";
        int month = LocalDateTime.now().getMonthValue();
        int day = LocalDateTime.now().getDayOfMonth();
        LocalDateTime localDateTime = LocalDateTime.of(2025, month, day,9,59);

        Student student6 = studentRepository.findStudentByName(name);
        student6.updateState(localDateTime);

        assertThat(student6.getRecord().get(localDateTime)).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    @DisplayName("LocalDateTime 을 날짜까지만 비교하는 메서드 테스트")
    void test6() {
        boolean compareResult = student.compareDayIsSame(LocalDateTime.of(2024,12,12,9,59),LocalDateTime.of(2024,12,12,13,25));
        Assertions.assertTrue(compareResult);
    }

    @Test
    @DisplayName("출석 기록 업데이트 하는 메서드 테스트")
    void test7() {
        Student student1 = studentRepository.findStudentByName("빙티");
        student1.updateState(LocalDateTime.of(2024,12,3,10,0));
        Assertions.assertTrue(student1.getRecord().get(LocalDateTime.of(2024,12,3,10,0)).equals(AttendanceStatus.ATTENDANCE));
    }

}
