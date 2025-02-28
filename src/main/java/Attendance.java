import controller.AttendanceController;
import view.FileInput;
import view.Input;
import view.Output;

import java.util.Scanner;

public class Attendance {
    public static void main(String[] args) {
        AttendanceController attendanceController = new AttendanceController(new FileInput(),
                new Input(new Scanner(System.in)),
                new Output());
        attendanceController.start();
    }
}
