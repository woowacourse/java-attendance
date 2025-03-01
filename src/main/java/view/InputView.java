package view;

import constant.MenuOption;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputView {
    static Scanner SCANNER = new Scanner(System.in);

    public static MenuOption inputChooseFunctionOption(){
       return MenuOption.validateSelectMenuOption(SCANNER.nextLine());
    }

    public static String input(){
        return SCANNER.nextLine();
    }

    public static LocalTime inputAttendanceTime(){
        String input = SCANNER.nextLine();
        return validateTimeFormat(input);
    }

    public static LocalTime validateTimeFormat(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            return LocalTime.parse(time, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 시간 형식입니다. 'HH:mm' 형식으로 입력하세요.");
        }
    }

    public static int validateDateFormat(String input) {
        try {
            int a = Integer.parseInt(input);
            if (a < 1 || a > 31){
                throw new IllegalArgumentException("[ERROR] 날짜 입력은 1이상 31이하 숫자만 입력 가능합니다");
            }
            return a;
        }catch (NumberFormatException n){
            throw new IllegalArgumentException("[ERROR] 날짜 입력은 1이상 31이하 숫자만 입력 가능합니다");
        }
    }
}
