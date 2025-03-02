package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Input {
    private final Scanner scanner;

    public Input(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getMenuInput(LocalDate today) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("오늘은 MM월 dd일 EEEE입니다", Locale.KOREAN);
        String formattedDate = today.format(formatter);
        System.out.println(getMenuMessage(formattedDate));
        return scanner.nextLine();
    }

    private String getMenuMessage(String formattedDate) {
        return String.format("""
                        %s. 기능을 선택해 주세요.
                        1. 출석 확인
                        2. 출석 수정
                        3. 크루별 출석 기록 확인
                        4. 제적 위험자 확인
                        Q. 종료
                        """,
                formattedDate);
    }

    public String getNameInput() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String getTimeInput() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String getEditDateInput() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return scanner.nextLine();
    }

    public String getEditTimeInput() {
        System.out.println("언제로 변경하겠습니까?");
        return scanner.nextLine();
    }
}
