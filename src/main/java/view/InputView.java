package view;

import constant.MenuOption;
import java.util.Scanner;

public class InputView {
    static Scanner scanner = new Scanner(System.in);

    public static MenuOption selectFunction(){
       return MenuOption.validateSelectMenuOption(scanner.nextLine());
    }

}
