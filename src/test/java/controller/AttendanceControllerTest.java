package controller;

import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendanceBook;
import domain.AttendanceTime;
import domain.AttendanceTimes;
import domain.Crew;
import domain.CrewAttendance;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.InputView;

class AttendanceControllerTest {
    private ByteArrayOutputStream outputStream;
    private PrintStream standardOut;

    private AttendanceController attendanceController;

    @BeforeEach
    void setUp() {
        standardOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        AttendanceBook attendanceBook = createAttendanceBook();
        InputView inputView = new InputView();
        attendanceController = new AttendanceController(attendanceBook, inputView);
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
        System.out.println(getOutput());
    }

    @Test
    @DisplayName("IO 입출력 메서드 정상 작동 확인")
    void inputOutputTest() {
        // given
        systemIn("hello", "world");

        // when
        getInputTwice();

        // then
        String output = getOutput();
        assertThat(output).contains("hello", "world");
    }

    private void getInputTwice() {
        Scanner scanner = new Scanner(System.in);

        String input1 = scanner.next();
        System.out.println(input1);

        String input2 = scanner.next();
        System.out.println(input2);
    }

    @Test
    @DisplayName("Q 입력 시 프로그램 종료")
    void exitProgramTest() {
        // given
        systemIn("Q");

        // when
        attendanceController.run();

        // then
        assertThat(getOutput()).contains("프로그램을 종료합니다.");
    }

    @Disabled // TODO: 주말에 상관없이 출석을 기록할 수 있는 방법 모색
    @Test
    @DisplayName("1 입력 시 출석")
    void attendTest() {
        // given
        systemIn("1", "이든", "09:59");

        // when
        attendanceController.run();

        // then
        assertThat(getOutput()).contains("09:59 (출석)");
    }

    @Test
    @DisplayName("2 입력 시 출석 수정")
    void modifyTest() {
        // given
        systemIn("2", "이든", "10", "09:58");

        // when
        attendanceController.run();

        // then
        assertThat(getOutput()).contains("->");
    }

    @Test
    @DisplayName("3 입력 시 출석 기록 확인")
    void readAttendanceLogTest() {
        // given
        systemIn("3", "이든");

        // when
        attendanceController.run();

        // then
        assertThat(getOutput()).contains(
                "이번 달 이든의 출석 기록입니다.",
                "출석", "지각", "출석", "결석"
        );
    }

    @Test
    @DisplayName("4 입력 시 제적, 면담, 경고 대상자 출력")
    void showDangerCrewTest() {
        // given
        systemIn("4");

        // when
        attendanceController.run();

        // then
        assertThat(getOutput()).contains("제적 위험자 조회 결과");
    }

    @Test
    @DisplayName("유효하지 않은 입력 시 예외 출력 후 프로그램 계속 작동")
    void givenInvalidInputThenPrintExceptionMessage() {
        // given
        systemIn("0");

        // when
        attendanceController.run();

        // then
        assertThat(getOutput()).contains("\"1, 2, 3, 4, Q\" 만 입력할 수 있습니다");
    }

    private AttendanceBook createAttendanceBook() {
        return AttendanceBook.of(createCrewAttendances());
    }

    private List<CrewAttendance> createCrewAttendances() {
        return List.of(
                CrewAttendance.of(
                        Crew.of("차니"), createAttendanceTimes()
                ),
                CrewAttendance.of(
                        Crew.of("이든"), createAttendanceTimes()
                )
        );
    }

    private AttendanceTimes createAttendanceTimes() {
        AttendanceTime attendanceTime1 = AttendanceTime.of(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 5)
        );
        AttendanceTime attendanceTime2 = AttendanceTime.of(
                LocalDate.of(2024, 12, 11),
                LocalTime.of(10, 6)
        );
        AttendanceTime attendanceTime3 = AttendanceTime.of(
                LocalDate.of(2024, 12, 12),
                LocalTime.of(10, 5)
        );
        AttendanceTime attendanceTime4 = AttendanceTime.of(
                LocalDate.of(2024, 12, 13),
                LocalTime.of(11, 6)
        );
        return AttendanceTimes.of(
                List.of(attendanceTime1,
                        attendanceTime2,
                        attendanceTime3,
                        attendanceTime4)
        );
    }

    private void systemIn(String... inputs) {
        String joined = String.join("\n", inputs);
        System.setIn(new ByteArrayInputStream(joined.getBytes()));
    }

    private String getOutput() {
        return outputStream.toString();
    }
}
