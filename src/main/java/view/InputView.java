package view;

import controller.Command;

import java.util.Arrays;
import java.util.Scanner;

import static controller.Command.*;

public class InputView {
    private static Scanner commandScanner = new Scanner(System.in);

    public static Command getCommand() {
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");

        String commandLine = commandScanner.nextLine();
        return convertToCommand(commandLine);
    }
}
