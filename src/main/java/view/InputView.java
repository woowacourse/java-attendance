package view;

import constant.MenuOption;
import java.util.Scanner;
import model.AttendanceBook;

public class InputView {
    static Scanner scanner = new Scanner(System.in);

    public static MenuOption inputChooseFunctionOption(){
       return MenuOption.validateSelectMenuOption(scanner.nextLine());
    }

    public static String inputStudentNickName(AttendanceBook attendanceBook){
        return scanner.nextLine();
    }

}
