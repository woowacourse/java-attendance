package view;

import static util.Constants.*;

import domain.AttendanceBook;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);
    private final AttendanceBook attendanceBook;

    public InputView(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public String readSelectedMenu() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일", Locale.KOREAN);
        String formattedDate = TODAY.format(dateFormatter);
        String formattedDayOfWeek = TODAY.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        String message = "오늘은 " + formattedDate + " " + formattedDayOfWeek + "입니다. 기능을 선택해 주세요.\n"
                + "1. 출석 확인\n"
                + "2. 출석 수정\n"
                + "3. 크루별 출석 기록 확인\n"
                + "4. 제적 위험자 확인\n"
                + "Q. 종료";
        return readInput(message, InputValidator::validateSelectedMenu);
    }

    public String readName() {
        return readInput("\n닉네임을 입력해 주세요.",
                InputValidator::validateName, attendanceBook);
    }

    public String readAttendanceTime() {
        return readInput("등교 시간을 입력해 주세요.",
                InputValidator::validateTime);
    }

    public String readModifyDay() {
        return readInput("수정하려는 날짜(일)를 입력해 주세요.",
                InputValidator::validateDay);
    }

    public String readModifyName() {
        return readInput("\n출석을 수정하려는 크루의 닉네임을 입력해 주세요.",
                InputValidator::validateName, attendanceBook);
    }

    public String readModifyTime() {
        return readInput("언제로 변경하겠습니까?",
                InputValidator::validateTime);
    }

    private String readInput(String message, Consumer<String> validator) {
        System.out.println(message);
        String input = scanner.nextLine();
        validator.accept(input);
        return input;
    }

    private String readInput(String message,
                             BiConsumer<String, AttendanceBook> validator,
                             AttendanceBook param) {
        System.out.println(message);
        String input = scanner.nextLine();
        validator.accept(input, param);
        return input;
    }
}
