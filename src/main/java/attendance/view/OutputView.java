package attendance.view;

public class OutputView {

    public void print(String message) {
        System.out.println(message);
    }

    public void printError(String error) {
        print(error);
    }
}
