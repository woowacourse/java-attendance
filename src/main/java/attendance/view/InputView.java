package attendance.view;

import java.util.Scanner;

public class InputView {

    private static final Scanner console = new Scanner(System.in);

    public Menu readMenuCommand() {
        return Menu.find(console.nextLine());
    }
}
