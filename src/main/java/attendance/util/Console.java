package attendance.util;

import java.util.Scanner;

public final class Console {

    private static final Scanner console = new Scanner(System.in);

    private Console() {
    }

    public static int readInt() {

        int input = console.nextInt();
        console.nextLine();
        return input;
    }

    public static String read() {

        return console.nextLine();
    }
}
