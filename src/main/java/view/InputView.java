package view;

import java.time.LocalTime;
import java.util.Scanner;

public class InputView {

    private Scanner scanner = new Scanner(System.in);

    public String getNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public LocalTime getAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return convertStringToLocalTime(scanner.nextLine());
    }

    public String getEditNickname() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public Integer getEditDayOfMonth() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return convertStringToInteger(scanner.nextLine());
    }

    public LocalTime getNewTime() {
        System.out.println("언제로 변경하겠습니까?");
        return convertStringToLocalTime(scanner.nextLine());
    }

    public String getOption() {
        return scanner.nextLine();
    }

    private LocalTime convertStringToLocalTime(String timeInput) {
        try {
            return LocalTime.parse(timeInput, TimeFormat.DATE_TIME_FORMATTER);
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 입력된 시간 형식이 적절하지 않습니다.");
        }
    }


    private Integer convertStringToInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력 가능합니다.");
        }
    }
}
