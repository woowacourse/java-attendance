import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final InputView inputView = new InputView(scanner);
        final AttendacneController attendacneController = new AttendacneController(inputView);
        attendacneController.run();
        scanner.close();
    }
}
