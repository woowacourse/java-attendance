package view;

import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String readOption() {
        System.out.println(
                "1. 출석 확인\n" +
                        "2. 출석 수정\n" +
                        "3. 크루별 출석 기록 확인\n" +
                        "4. 제적 위험자 확인\n" +
                        "Q. 종료");

        return readLine();
    }

    private String readLine() {
        return scanner.nextLine().trim();
    }
}
