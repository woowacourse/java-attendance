package controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.InputView;

class ControllerTest {

    Controller controller = new Controller();

    @BeforeEach
    void setScanner() {
        System.setIn(System.in);
        InputView.setScanner(new Scanner(System.in));
    }

    @Test
    @DisplayName("존재하지 않는 학생을 입력했을 때의 테스트")
    void test1() {
        String input = "1\n말론\n이든\n09:59\nQ";
        InputStream in  = new ByteArrayInputStream(input.getBytes());

        System.setIn(in);

        InputView.setScanner(new Scanner(System.in));

        OutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);

        System.setOut(printStream);

        controller.start();
        Assertions.assertThat(out.toString().contains("[ERROR] 존재하지 않는 학생입니다.")).isTrue();
    }

    @Test
    @DisplayName("잘못된 형식의 시간을 입력했을 때의 테스트")
    void test2() {
        String input = "1\n이든\n09:99\n09:59\nQ\n";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        InputView.setScanner(new Scanner(System.in));

        OutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);

        System.setOut(printStream);

        controller.start();

        Assertions.assertThat(out.toString().contains("[ERROR] 시간 형식에 맞지 않습니다.")).isTrue();
    }

    @Test
    @DisplayName("캠퍼스 운영 시간이 아닌 시간을 입력했을 때의 테스트")
    void test3() {
        String input = "1\n이든\n04:33\n09:59\nQ";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        InputView.setScanner(new Scanner(System.in));


        OutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);

        System.setOut(printStream);

        controller.start();

        Assertions.assertThat(out.toString().contains("[ERROR] 캠퍼스 운영 시간이 아닙니다.")).isTrue();
        printStream.flush();
    }

    @Test
    @DisplayName("학생이 출석했을 때 테스트")
    void test4() {
        String input = "1\n이든\n09:59\nQ";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        InputView.setScanner(new Scanner(System.in));

        OutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);

        System.setOut(printStream);

        controller.start();

        Assertions.assertThat(out.toString().contains("12월 12일 목요일 09:59( 출석 )")).isTrue();
        printStream.flush();

    }

    @Test
    @DisplayName("수정할 때의 메서드 테스트")
    void test5() {
        String input = "2\n이든\n11\n09:59\nQ";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        InputView.setScanner(new Scanner(System.in));

        OutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);

        System.setOut(printStream);

        controller.start();

        Assertions.assertThat(out.toString().contains("12월 11일 수요일 --:-- -> 09:59 (출석) 수정 완료!")).isTrue();
        printStream.flush();

    }

    @Test
    @DisplayName("주말 및 공휴일을 수정할 때의 메서드 테스트")
    void test6() {
        String input = "2\n이든\n25\n09:59\nQ";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        InputView.setScanner(new Scanner(System.in));

        OutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);

        System.setOut(printStream);

        controller.start();

        Assertions.assertThat(out.toString().contains("[ERROR] 주말 및 공휴일에는 출석을 수정할 수 없습니다.")).isTrue();
        printStream.flush();

    }

    @Test
    @DisplayName("크루별 출석 기록 확인 기능 테스트")
    void test7() {
        String input = "3\n빙봉\nQ";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        InputView.setScanner(new Scanner(System.in));

        OutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);

        System.setOut(printStream);

        controller.start();

        Assertions.assertThat(out.toString().contains("12월 02일 월요일 13:06( 지각 )\n"
                + "12월 03일 화요일 10:03( 출석 )\n"
                + "12월 04일 수요일 --:--( 결석 )\n"
                + "12월 05일 목요일 --:--( 결석 )\n"
                + "12월 06일 금요일 --:--( 결석 )\n"
                + "12월 09일 월요일 --:--( 결석 )\n"
                + "12월 10일 화요일 --:--( 결석 )\n"
                + "12월 11일 수요일 --:--( 결석 )\n"
                + "결석: 6회\n"
                + "출석: 1회\n"
                + "지각: 1회\n"
                + "제적 대상자입니다.")).isTrue();
        printStream.flush();

    }

    @Test
    @DisplayName("제적 위험자 확인 기능 테스트")
    void test8() {
        String input = "4\nQ";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        InputView.setScanner(new Scanner(System.in));

        OutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);

        System.setOut(printStream);

        controller.start();

        Assertions.assertThat(out.toString().contains("제적 위험자 조회 결과\n"
                + "- 빙티: 결석 6회, 지각 1회 (제적)\n"
                + "- 이든: 결석 6회, 지각 1회 (제적)\n"
                + "- 빙봉: 결석 6회, 지각 1회 (제적)\n"
                + "- 쿠키: 결석 6회, 지각 1회 (제적)\n"
                + "- 짱수: 결석 6회, 지각 0회 (제적)")).isTrue();
        printStream.flush();
    }
}