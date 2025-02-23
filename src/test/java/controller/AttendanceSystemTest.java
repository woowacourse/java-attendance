package controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.AttendanceBook;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.DateTimeManager;
import util.FileReader;

public class AttendanceSystemTest {
    AttendanceSystem attendanceSystem;
    AttendanceBook attendanceBook;
    DateTimeManager dateTimeManager;
    PrintStream printStream = System.out;
    ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        attendanceBook = FileReader.readExistedAttendanceData();
        dateTimeManager = new DateTimeManager(2024, 12, 13);

        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void resetStream() {
        System.setOut(printStream);
    }

    @DisplayName("출석 확인 흐름이 정상적으로 동작한다.")
    @Test
    void test1() {
        String input = "1\n미미\n08:30\nQ\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        attendanceSystem = new AttendanceSystem(attendanceBook, dateTimeManager, scanner);
        attendanceSystem.run();

        String output = outputStream.toString();
        assertTrue(output.contains("12월 13일 금요일 08:30 (출석)"));
    }

    @DisplayName("출석 수정 흐름이 정상적으로 동작한다.")
    @Test
    void test2() {
        attendanceBook.enter("미미");
        attendanceBook.add("미미",
                LocalDateTime.of(2024, 12, 13, 9, 59));

        String input = "2\n미미\n13\n10:06\nQ\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        attendanceSystem = new AttendanceSystem(attendanceBook, dateTimeManager, scanner);
        attendanceSystem.run();

        String output = outputStream.toString();
        assertTrue(output.contains("12월 13일 금요일 09:59 (출석) -> 10:06 (지각) 수정 완료!"));
    }

    @DisplayName("크루별 출석 기록 확인 흐름이 정상적으로 동작한다.")
    @Test
    void test3() {
        String input = "3\n미미\nQ\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        attendanceSystem = new AttendanceSystem(attendanceBook, dateTimeManager, scanner);
        attendanceSystem.run();

        String output = outputStream.toString();
        assertTrue(output.contains("12월 02일 월요일 10:00 (출석)\n"
                + "12월 03일 화요일 09:57 (출석)\n"
                + "12월 04일 수요일 --:-- (결석)\n"
                + "12월 05일 목요일 --:-- (결석)\n"
                + "12월 06일 금요일 --:-- (결석)\n"
                + "12월 09일 월요일 --:-- (결석)\n"
                + "12월 10일 화요일 --:-- (결석)\n"
                + "12월 11일 수요일 --:-- (결석)\n"
                + "12월 12일 목요일 --:-- (결석)\n"
                + "12월 13일 금요일 --:-- (결석)\n"
                + "\n"
                + "출석: 2회\n"
                + "지각: 0회\n"
                + "결석: 8회\n"
                + "\n"
                + "제적 대상자입니다.\n"));
    }
}
