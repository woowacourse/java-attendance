package view;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import util.Console;

public class InputView {

    private static final int START_DATE = 1;
    private static final int END_DATE = 31;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public String inputFeature(int month, int day, String dayOfWeek) {
        System.out.printf("\n오늘은 %d월 %02d일 %s입니다. 기능을 선택해 주세요.\n" + "1. 출석 확인\n" + "2. 출석 수정\n" + "3. 크루별 출석 기록 확인\n"
                + "4. 제적 위험자 확인\n" + "Q. 종료\n", month, day, dayOfWeek);
        return Console.readLine();
    }

    public String inputNickName() {
        System.out.println("닉네임을 입력해 주세요.");
        String input = Console.readLine();
        validateNullOrEmpty(input);
        return input;
    }

    public LocalTime inputGoTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String input = Console.readLine();
        validateNullOrEmpty(input);
        return parseToLocalTime(input);
    }

    private void validateNullOrEmpty(final String nickName) {
        if (nickName.isBlank()) {
            throw new IllegalArgumentException("빈 값은 입력할 수 없습니다.");
        }
    }

    private LocalTime parseToLocalTime(final String input) {
        return LocalTime.parse(input, DATE_TIME_FORMATTER);
    }

    public String inputUpdateNickName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        String input = Console.readLine();
        validateNullOrEmpty(input);
        return input;
    }

    public int inputUpdateDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String input = Console.readLine();
        return validateParseDate(input);
    }

    public LocalTime inputUpdateTime() {
        System.out.println("언제로 변경하겠습니까?");
        String input = Console.readLine();
        validateNullOrEmpty(input);
        return parseToLocalTime(input);
    }

    private int validateParseDate(final String inputDate) {
        try {
            int date = Integer.parseInt(inputDate);
            return validateSize(date);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("날짜는 숫자만 입력할 수 있습니다.");
        }
    }

    private int validateSize(final int date) {
        if (date < START_DATE || date > END_DATE) {
            throw new IllegalArgumentException("날짜는 1부터 31일까지의 숫자만 입력할 수 있습니다.");
        }
        return date;
    }
}
