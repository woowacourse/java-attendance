package controller;

import static org.assertj.core.api.Assertions.*;

import domain.AttendanceBook;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.FileReader;

class AttendanceControllerTest {
    AttendanceController attendanceController;
    AttendanceBook attendanceBook;
    ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        attendanceBook = new AttendanceBook(new FileReader().readAttendanceData());
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void resetStream() {
        System.setOut(System.out);
    }

    @DisplayName("실행 흐름이 정상적으로 동작한다.")
    @Test
    void test1() {
        runTest("1\n미미\n08:30\nQ\n",
                "12월 13일 금요일 08:30 (출석)");
    }

    @DisplayName("잘못된 값을 입력한 경우 해당 값부터 다시 입력을 받는다.")
    @Test
    void test2() {
        runTest("6\n2\n미미미\n미미\n15\n3\n23:30\n15:00\nq\n",
                "12월 03일 화요일 10:00 (출석) -> --:-- (결석) 수정 완료!");
    }

    @DisplayName("사용자가 프로그램을 종료하지 않는 경우 계속해서 실행된다.")
    @Test
    void test3() {
        runTest("3\n미미\n4\nQ",
                "제적 위험자 조회 결과");
    }

    void runTest(String input, String expectedOutput) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        attendanceController = new AttendanceController(attendanceBook);
        attendanceController.run();

        String output = outputStream.toString();
        assertThat(output).contains(expectedOutput);
    }
}
