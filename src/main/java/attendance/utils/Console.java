package attendance.utils;

import java.util.Scanner;

public final class Console {
    private Console() {
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine() {
        return scanner.nextLine();
    }

    public static int readInt() {
        int input = scanner.nextInt();
        scanner.nextLine();

        return input;
    }
}
