package view;

import domain.MenuOption;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final static Scanner scanner = new Scanner(System.in);

    public String readOption(List<MenuOption> options) {
        StringBuilder sb = new StringBuilder();
        for (MenuOption option : options) {
            sb.append(System.lineSeparator())
                    .append(option.getCommand())
                    .append(". ")
                    .append(option.getOption());
        }
        return prompt(sb.toString());
    }

    public String readNickname() {
        return prompt("닉네임을 입력해 주세요.");
    }

    public LocalTime readArrivalTime() {
        String response = prompt("등교 시간을 입력해 주세요.");
        return parseTime(response);
    }

    public String readEditNickname() {
        return prompt("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
    }

    public String readEditArrivalDate() {
        return prompt("수정하려는 날짜(일)를 입력해 주세요.");
    }

    public String readEditArrivalTime() {
        return prompt("언제로 변경하겠습니까?");
    }

    private String prompt(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    private LocalTime parseTime(String response) {
        return LocalTime.parse(response, DateTimeFormatter.ofPattern("HH:mm"));
    }
}
