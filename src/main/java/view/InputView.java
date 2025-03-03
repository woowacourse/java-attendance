package view;

import domain.UserSelection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import util.InputParser;
import util.InputReader;

public class InputView {

    private final InputReader inputReader;

    public InputView(InputReader inputReader) {
        this.inputReader = inputReader;
    }

    public UserSelection readUserSelection(String date) {
        System.out.printf("오늘은 %s입니다. 기능을 선택해 주세요." + System.lineSeparator(), date);
        Arrays.stream(UserSelection.values())
                .forEach(userSelection ->
                        System.out.printf("%s. %s" + System.lineSeparator(),
                                userSelection.getInput(),
                                userSelection.getMessage()));
        return UserSelection.findByInput(inputReader.readline());
    }

    public String readName() {
        System.out.println("닉네임을 입력해 주세요.");
        return inputReader.readline();
    }

    public LocalTime readTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return InputParser.parseToLocalTime(inputReader.readline());
    }

    public String readNameToModify() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return inputReader.readline();
    }

    public LocalDate readDateToModify() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return InputParser.parseToDecemberLocalDate(inputReader.readline());
    }

    public LocalTime readTimeToModify() {
        System.out.println("언제로 변경하겠습니까?");
        return InputParser.parseToLocalTime(inputReader.readline());
    }
}
