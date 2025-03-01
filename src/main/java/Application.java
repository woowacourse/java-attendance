import controller.AttendanceController;
import domain.AttendanceBook;
import file.AttendanceBookFileReader;

public class Application {

    public static void main(String[] args) {
        String fileName = "src/main/resources/attendances.csv";
        AttendanceBook book = AttendanceBookFileReader.read(fileName);

        AttendanceController controller = new AttendanceController(book);
        controller.run(book);
    }
}
