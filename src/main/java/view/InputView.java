package view;

import java.time.LocalTime;
import java.util.Scanner;
import util.Converter;

public class InputView {

    private Scanner scanner = new Scanner(System.in);

    public String getNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public LocalTime getAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return Converter.convertStringToLocalTime(scanner.nextLine());
    }

    public String getEditNickname() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public Integer getEditDayOfMonth() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return Converter.convertStringToInteger(scanner.nextLine());
    }

    public LocalTime getNewTime() {
        System.out.println("언제로 변경하겠습니까?");
        return Converter.convertStringToLocalTime(scanner.nextLine());
    }

    public String getOption() {
        return scanner.nextLine();
    }
}
