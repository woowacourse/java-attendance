package view;

import constant.Selection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.regex.Pattern;
import util.DateFormatter;

public class InputView {

    public Selection getSelection() {
        Scanner scan = new Scanner(System.in);
        LocalDate now = DateFormatter.getTodayDate();
        String dateFormat = DateFormatter.dateFormat(now);
        printMenu(dateFormat);
        return Selection.findSelection(scan.nextLine());
    }

    public String getUsername() {
        System.out.println("닉네임을 입력해 주세요.");
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    public LocalDateTime getAttendanceTime() {
        Scanner scan = new Scanner(System.in);
        System.out.println("등교 시간을 입력해 주세요.");
        LocalDate now = DateFormatter.getTodayDate();
        String input = scan.nextLine();
        InputValidator.validateTime(input);
        LocalTime time = DateFormatter.changeInputToTime(input);
        return LocalDateTime.of(now, time);
    }

    public String getUsernameForEditHistory() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    public LocalDate getEditDay() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        InputValidator.validateNumber(input);
        int day = Integer.parseInt(input);
        return LocalDate.of(DateFormatter.YEAR, DateFormatter.MONTH, day);
    }

    public LocalTime getEditTime() {
        System.out.println("언제로 변경하겠습니까?");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        InputValidator.validateTime(input);
        return DateFormatter.changeInputToTime(input);
    }

    private void printMenu(String dateFormat) {
        System.out.printf("오늘은 %s입니다. 기능을 선택해 주세요.\n", dateFormat);
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");
    }

    static class InputValidator {
        private static final String TIME_FORMAT = "^(?:[01]\\d|2[0-3]):[0-5]\\d$";

        private static void validateNumber(String input) {
            if (!Pattern.matches("\\d+", input)) {
                throw new IllegalArgumentException("[ERROR] 숫자만 입력 가능합니다.");
            }
        }

        private static void validateTime(String input) {
            if (!Pattern.matches(TIME_FORMAT, input)) {
                throw new IllegalArgumentException("[ERROR] 00:00 형태로 입력해주세요!");
            }
        }
    }
}
