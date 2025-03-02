package attendance.view;

import java.io.Closeable;
import java.util.Scanner;

public class InputView implements Closeable {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void close() {
        scanner.close();
    }
}
